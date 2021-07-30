package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserLoginConverter;
import com.sciatta.openmall.api.pojo.query.UserLoginQuery;
import com.sciatta.openmall.api.pojo.vo.UserLoginVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.CookieUtils;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sciatta.openmall.common.constant.CookieConstants.COOKIE_USERNAME;

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
    public JSONResult login(@RequestBody UserLoginQuery userLoginQuery,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        
        String username = userLoginQuery.getUsername();
        String password = userLoginQuery.getPassword();
        
        // 用户名和密码必须不为空
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            return JSONResult.errorUsingMessage("用户名或密码不能为空");
        }
        
        // 查询用户是否存在
        UserLoginDTO userLoginDTO = userService.queryUserForLogin(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (userLoginDTO == null) {
            return JSONResult.errorUsingMessage("用户名或密码不正确");
        }
        
        // 转换POJO
        UserLoginVO userLoginVO = UserLoginConverter.INSTANCE.userLoginDTOtoUserLoginVO(userLoginDTO);
        
        // 设置客户端Cookie
        CookieUtils.setCookie(request, response, COOKIE_USERNAME,
                JsonUtils.objectToJson(userLoginVO), true);
        
        return JSONResult.success();
    }
}
