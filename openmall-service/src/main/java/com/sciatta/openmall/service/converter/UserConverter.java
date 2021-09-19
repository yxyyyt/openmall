package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.common.enums.Sex;
import com.sciatta.openmall.common.utils.DateUtils;
import com.sciatta.openmall.dao.pojo.po.mbg.User;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserQuery;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.n3r.idworker.Sid;
import org.springframework.util.DigestUtils;

import java.util.Date;

import static com.sciatta.openmall.common.constants.PictureConstants.USER_FACE;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserConverter
 */
@Mapper
public abstract class UserConverter {
    public static UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    
    public User toUser(UserQuery userQuery) {
        User user = new User();
        user.setId(Sid.nextShort());    // Sid是分布式自增ID，基于snowflake算法
        user.setUsername(userQuery.getUsername());
        user.setPassword(DigestUtils.md5DigestAsHex(userQuery.getPassword().getBytes()));
        user.setNickname(userQuery.getUsername());
        user.setFace(USER_FACE);
        user.setSex(Sex.secret.type);
        user.setBirthday(DateUtils.stringToDate("1900-01-01"));
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        return user;
    }
    
    public abstract UserDTO toUserDTO(User user);
    
    public abstract User userServiceQueryToUser(String id, UserServiceQuery userServiceQuery);
}
