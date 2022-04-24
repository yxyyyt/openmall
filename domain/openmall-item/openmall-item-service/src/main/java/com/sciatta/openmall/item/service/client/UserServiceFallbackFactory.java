package com.sciatta.openmall.item.service.client;

import com.sciatta.openmall.common.utils.RandomUtils;
import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.pojo.po.mbg.User;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by Rain on 2022/4/23<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * UserServiceFallback
 */
@Slf4j
@Component
public class UserServiceFallbackFactory implements FallbackFactory<UserServiceFeignClient> {

    @Override
    public UserServiceFeignClient create(Throwable throwable) {

        return new UserServiceFeignClient() {
            @Override
            public UserDTO queryUserForLogin(String username, String password) {
                return null;
            }

            @Override
            public boolean queryUsernameIsExist(String username) {
                return false;
            }

            @Override
            public UserDTO createUser(UserQuery userQuery) {
                return null;
            }

            @Override
            public UserDTO queryUserByUserId(String userId) {
                log.error("query user {} fallback as {}", userId, throwable.getMessage());
                UserDTO userDTO = new UserDTO();
                userDTO.setFace("empty");   // TODO 替换为公共头像
                userDTO.setNickname(RandomUtils.randomString(8));   // 随机生成Nickname
                return userDTO;
            }

            @Override
            public boolean updateUserByUserId(String userId, UserQuery userQuery) {
                return false;
            }
        };
    }
}
