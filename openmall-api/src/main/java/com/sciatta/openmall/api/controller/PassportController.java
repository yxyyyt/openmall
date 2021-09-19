package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserConverter;
import com.sciatta.openmall.api.pojo.query.UserQuery;
import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.api.support.cache.LoginShopCartCacheHelper;
import com.sciatta.openmall.api.support.cache.UserCacheHelper;
import com.sciatta.openmall.api.support.validate.IsEqual;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 注册登录
 */
@Validated
@RestController
@RequestMapping("passport")
public class PassportController {
    private final UserService userService;
    private final LoginShopCartCacheHelper loginShopCartCacheHelper;
    private final UserCacheHelper userCacheHelper;
    
    public PassportController(UserService userService,
                              LoginShopCartCacheHelper loginShopCartCacheHelper,
                              UserCacheHelper userCacheHelper) {
        this.userService = userService;
        this.loginShopCartCacheHelper = loginShopCartCacheHelper;
        this.userCacheHelper = userCacheHelper;
    }
    
    @PostMapping("login")
    public JSONResult login(
            @RequestBody @Validated(UserQuery.Login.class) UserQuery userQuery,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        // 查询用户是否存在
        UserDTO userDTO = userService.queryUserForLogin(userQuery.getUsername(), userQuery.getPassword());
        if (userDTO == null) {
            return JSONResult.errorUsingMessage("用户名或密码不正确");
        }
        
        UserCookieVO userCookieVO = UserConverter.INSTANCE.toUserCookieVO(userDTO);
        
        // 设置用户缓存
        userCacheHelper.setUserCache(userCookieVO, request, response);
        
        return loginShopCartCacheHelper.processCache(userCookieVO.getId(), request, response);
    }
    
    @PostMapping("/register")
    public JSONResult register(
            @RequestBody
            @IsEqual(field = "password", compareField = "confirmPassword", message = "两次密码输入不一致")
            @Validated(UserQuery.Register.class)
                    UserQuery userQuery,
            HttpServletRequest request,
            HttpServletResponse response) {
        
        // 查询用户名是否存在
        boolean test = userService.queryUsernameIsExist(userQuery.getUsername());
        if (test) {
            return JSONResult.errorUsingMessage("用户名已经存在");
        }
        
        // 注册
        UserDTO userDTO = userService.createUser(UserConverter.INSTANCE.toUserQuery(userQuery));
        
        UserCookieVO userCookieVO = UserConverter.INSTANCE.toUserCookieVO(userDTO);
        
        // 设置缓存
        userCacheHelper.setUserCache(userCookieVO, request, response);
        
        return loginShopCartCacheHelper.processCache(userCookieVO.getId(), request, response);
    }
    
    @PostMapping("logout")
    public JSONResult logout(@RequestParam @NotBlank(message = "用户标识不能为空") String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        
        // 清理缓存
        userCacheHelper.clearUserCache(userId, request, response);
        return JSONResult.success();
    }
    
    @GetMapping("usernameIsExist")
    public JSONResult usernameIsExist(
            @RequestParam @NotBlank(message = "用户名不能为空") String username) {
        
        // 查询用户名是否存在
        boolean test = userService.queryUsernameIsExist(username);
        if (test) {
            return JSONResult.errorUsingMessage("用户名已经存在");
        }
        
        // 用户名没有重复
        return JSONResult.success();
    }
}
