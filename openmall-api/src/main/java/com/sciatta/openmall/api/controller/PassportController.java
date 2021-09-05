package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserConverter;
import com.sciatta.openmall.api.converter.UserRegisterConverter;
import com.sciatta.openmall.api.pojo.query.UserLoginApiQuery;
import com.sciatta.openmall.api.pojo.query.UserRegisterApiQuery;
import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.api.support.cache.LoginShopCartCacheHelper;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.RedisCacheConstants;
import com.sciatta.openmall.common.utils.CookieUtils;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.common.utils.SidUtils;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserRegisterServiceQuery;
import com.sciatta.openmall.service.support.cache.CacheService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sciatta.openmall.common.constants.CookieConstants.*;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 注册登录
 */
@RestController
@RequestMapping("passport")
public class PassportController {
    private final UserService userService;
    private final LoginShopCartCacheHelper loginShopCartCacheHelper;
    private final CacheService cacheService;
    
    public PassportController(UserService userService, LoginShopCartCacheHelper loginShopCartCacheHelper, CacheService cacheService) {
        this.userService = userService;
        this.loginShopCartCacheHelper = loginShopCartCacheHelper;
        this.cacheService = cacheService;
    }
    
    @PostMapping("login")
    public JSONResult login(@RequestBody UserLoginApiQuery userLoginApiQuery,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        
        String username = userLoginApiQuery.getUsername();
        String password = userLoginApiQuery.getPassword();
        
        // 用户名和密码必须不为空
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return JSONResult.errorUsingMessage("用户名或密码不能为空");
        }
        
        // 查询用户是否存在
        UserDTO userDTO = userService.queryUserForLogin(username, password);
        if (userDTO == null) {
            return JSONResult.errorUsingMessage("用户名或密码不正确");
        }
        
        UserCookieVO userCookieVO = UserConverter.INSTANCE.userDTOToUserCookieVO(userDTO);
        
        // 设置缓存
        setUserCache(userCookieVO, request, response);
        
        return loginShopCartCacheHelper.processCache(userCookieVO.getId(), request, response);
    }
    
    @PostMapping("/register")
    public JSONResult register(@RequestBody UserRegisterApiQuery userRegisterApiQuery,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        
        String username = userRegisterApiQuery.getUsername();
        String password = userRegisterApiQuery.getPassword();
        String confirmPassword = userRegisterApiQuery.getConfirmPassword();
        
        // 判断用户名和密码必须不为空
        if (!StringUtils.hasText(username) ||
                !StringUtils.hasText(password) ||
                !StringUtils.hasText(confirmPassword)) {
            return JSONResult.errorUsingMessage("用户名或密码不能为空");
        }
        
        // 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorUsingMessage("用户名已经存在");
        }
        
        // 密码长度不能少于6位
        if (password.length() < 6) {
            return JSONResult.errorUsingMessage("密码长度不能少于6");
        }
        
        // 判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorUsingMessage("两次密码输入不一致");
        }
        
        UserRegisterServiceQuery userRegisterServiceQuery =
                UserRegisterConverter.INSTANCE.userRegisterApiQueryToUserRegisterServiceQuery(userRegisterApiQuery);
        
        // 注册
        UserDTO userDTO = userService.createUser(userRegisterServiceQuery);
        
        UserCookieVO userCookieVO = UserConverter.INSTANCE.userDTOToUserCookieVO(userDTO);
        
        // 设置缓存
        setUserCache(userCookieVO, request, response);
        
        return loginShopCartCacheHelper.processCache(userCookieVO.getId(), request, response);
    }
    
    @PostMapping("logout")
    public JSONResult logout(@RequestParam String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // 清除缓存
        clearUserCache(userId, request, response);
        
        return JSONResult.success();
    }
    
    @GetMapping("usernameIsExist")
    public JSONResult usernameIsExist(@RequestParam String username) {
        // 判断用户名不能为空
        if (!StringUtils.hasText(username)) {
            return JSONResult.errorUsingMessage("用户名不能为空");
        }
        
        // 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorUsingMessage("用户名已经存在");
        }
        
        // 用户名没有重复
        return JSONResult.success();
    }
    
    private void setUserCache(UserCookieVO userCookieVO, HttpServletRequest request, HttpServletResponse response) {
        String userTokenKey = getUserTokenKey(userCookieVO.getId());
        String userTokenValue = getUserTokenValue();
        
        userCookieVO.setUserUniqueToken(userTokenValue);
        
        // 设置缓存
        cacheService.set(userTokenKey, userTokenValue);
        CookieUtils.setCookie(request, response, COOKIE_USERNAME, JsonUtils.objectToJson(userCookieVO), true);
    }
    
    private void clearUserCache(String userId, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, COOKIE_USERNAME);
        CookieUtils.deleteCookie(request, response, COOKIE_SHOP_CART);
        
        cacheService.del(getUserTokenKey(userId));
    }
    
    private String getUserTokenKey(String userId) {
        return RedisCacheConstants.USER_TOKEN + ":" + userId;
    }
    
    private String getUserTokenValue() {
        return SidUtils.generateUUID();
    }
}
