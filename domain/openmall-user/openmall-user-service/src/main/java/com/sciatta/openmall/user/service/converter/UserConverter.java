package com.sciatta.openmall.user.service.converter;

import com.sciatta.openmall.common.constants.PictureConstants;
import com.sciatta.openmall.common.enums.Sex;
import com.sciatta.openmall.common.utils.DateUtils;
import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import com.sciatta.openmall.user.pojo.po.mbg.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.n3r.idworker.Sid;
import org.springframework.util.DigestUtils;

import java.util.Date;

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
        user.setFace(PictureConstants.USER_FACE);
        user.setSex(Sex.secret.type);
        user.setBirthday(DateUtils.stringToDate("1900-01-01"));
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        return user;
    }

    public abstract UserDTO toUserDTO(User user);

    public abstract User toUser(String id, UserQuery userQuery);
}
