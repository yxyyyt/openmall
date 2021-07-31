package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserLoginConverter;
import com.sciatta.openmall.api.converter.UserRegisterConverter;
import com.sciatta.openmall.api.pojo.query.UserLoginApiQuery;
import com.sciatta.openmall.api.pojo.query.UserRegisterApiQuery;
import com.sciatta.openmall.api.pojo.vo.UserLoginVO;
import com.sciatta.openmall.api.pojo.vo.UserRegisterVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.CookieUtils;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import com.sciatta.openmall.service.pojo.dto.UserRegisterDTO;
import com.sciatta.openmall.service.pojo.query.UserRegisterServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sciatta.openmall.common.constants.CookieConstants.COOKIE_USERNAME;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 注册登录
 */
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;
    
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
        UserLoginDTO userLoginDTO = userService.queryUserForLogin(username, password);
        if (userLoginDTO == null) {
            return JSONResult.errorUsingMessage("用户名或密码不正确");
        }
        
        UserLoginVO userLoginVO = UserLoginConverter.INSTANCE.userLoginDTOtoUserLoginVO(userLoginDTO);
        
        // 设置客户端Cookie
        CookieUtils.setCookie(request, response, COOKIE_USERNAME,
                JsonUtils.objectToJson(userLoginVO), true);
        
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
        UserRegisterDTO userRegisterDTO = userService.createUser(userRegisterServiceQuery);
        
        UserRegisterVO userRegisterVO = UserRegisterConverter.INSTANCE.userRegisterDTOToUserRegisterVO(userRegisterDTO);
        
        // 设置客户端Cookie
        CookieUtils.setCookie(request, response, COOKIE_USERNAME,
                JsonUtils.objectToJson(userRegisterVO), true);
        
        return JSONResult.success();
    }
}
