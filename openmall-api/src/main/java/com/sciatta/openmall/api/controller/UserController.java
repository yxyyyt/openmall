package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.UserConverter;
import com.sciatta.openmall.api.pojo.query.UserApiQuery;
import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.api.pojo.vo.UserVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CookieConstants;
import com.sciatta.openmall.common.utils.CookieUtils;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.service.UserService;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 用户信息
 */
@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("query")
    public JSONResult query(@RequestParam String userId) {
        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserVO userVO = UserConverter.INSTANCE.userDTOToUserVO(userDTO);
        
        return JSONResult.success(userVO);
    }
    
    @PostMapping("update")
    public JSONResult update(@RequestParam String userId,
                             @RequestBody @Valid UserApiQuery userApiQuery,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        
        if (bindingResult.hasErrors()) {
            return JSONResult.errorUsingData(bindingResult);
        }
        
        UserServiceQuery userServiceQuery = UserConverter.INSTANCE.userApiQueryToUserServiceQuery(userApiQuery);
        boolean result = userService.updateUserByUserId(userId, userServiceQuery);
        if (!result) {
            return JSONResult.errorUsingMessage("用户更新失败，请尝试重试");
        }
        
        UserDTO userDTO = userService.queryUserByUserId(userId);
        UserCookieVO userCookieVO = UserConverter.INSTANCE.userDTOToUserCookieVO(userDTO);
        
        CookieUtils.setCookie(request, response, CookieConstants.COOKIE_USERNAME, JsonUtils.objectToJson(userCookieVO), true);
        
        return JSONResult.success();
    }
    
    
}