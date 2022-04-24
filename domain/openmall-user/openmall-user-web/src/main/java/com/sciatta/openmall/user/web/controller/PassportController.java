package com.sciatta.openmall.user.web.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.sciatta.openmall.user.api.UserService;
import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.web.converter.UserConverter;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import com.sciatta.openmall.user.pojo.vo.UserCookieVO;
import com.sciatta.openmall.user.web.support.UserCacheHelper;
import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.web.support.validate.IsEqual;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PassportController {
    private final UserService userService;
    private final UserCacheHelper userCacheHelper;

    public PassportController(UserService userService, UserCacheHelper userCacheHelper) {
        this.userService = userService;
        this.userCacheHelper = userCacheHelper;
    }

    @PostMapping("login")
    @HystrixCommand(fallbackMethod = "loginFail")
    public JSONResult login(
            @RequestBody @Validated(UserQuery.WebLogin.class) UserQuery userQuery,
            HttpServletRequest request,
            HttpServletResponse response) {

        // 查询用户是否存在
        UserDTO userDTO = userService.queryUserForLogin(userQuery.getUsername(), userQuery.getPassword());
        if (userDTO == null) {
            return JSONResult.errorUsingMessage("用户名或密码不正确");
        }

        UserCookieVO userCookieVO = UserConverter.INSTANCE.toUserCookieVO(userDTO);

        // 设置缓存
        userCacheHelper.sync(userCookieVO, request, response);
        return JSONResult.success();
    }

    @PostMapping("/register")
    public JSONResult register(
            @RequestBody
            @IsEqual(field = "password", compareField = "confirmPassword", message = "两次密码输入不一致")
            @Validated(UserQuery.WebRegister.class)
                    UserQuery userQuery,
            HttpServletRequest request,
            HttpServletResponse response) {

        // 查询用户名是否存在
        boolean test = userService.queryUsernameIsExist(userQuery.getUsername());
        if (test) {
            return JSONResult.errorUsingMessage("用户名已经存在");
        }

        // 注册
        UserDTO userDTO = userService.createUser(userQuery);

        UserCookieVO userCookieVO = UserConverter.INSTANCE.toUserCookieVO(userDTO);

        // 设置缓存
        userCacheHelper.sync(userCookieVO, request, response);
        return JSONResult.success();
    }

    @PostMapping("logout")
    public JSONResult logout(@RequestParam @NotBlank(message = "用户标识不能为空") String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        // 清理缓存
        userCacheHelper.clear(userId, request, response);
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

    // private

    public JSONResult loginFail(UserQuery userQuery, HttpServletRequest request, HttpServletResponse response,
                                Throwable throwable) {
        log.error("{} login Fail as {} ", userQuery.getUsername(), throwable.getMessage());
        return JSONResult.errorUsingMessage("登录失败，请重试！");
    }
}
