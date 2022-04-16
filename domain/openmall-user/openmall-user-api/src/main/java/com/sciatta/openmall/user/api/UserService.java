package com.sciatta.openmall.user.api;

import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Rain on 2022/3/22<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * UserService
 */
@FeignClient("openmall-user-service")
@RequestMapping("user-api")
public interface UserService {
    @GetMapping("verify")
    UserDTO queryUserForLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password);

    @GetMapping("user/exists")
    boolean queryUsernameIsExist(@RequestParam("username") String username);

    @PostMapping("user")
    UserDTO createUser(@RequestBody UserQuery userQuery);

    @GetMapping("profile")
    UserDTO queryUserByUserId(@RequestParam("userId") String userId);

    @PutMapping("profile/{userId}")
    boolean updateUserByUserId(
            @PathVariable("userId") String userId,
            @RequestBody UserQuery userQuery);
}
