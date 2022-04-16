package com.sciatta.openmall.user.web.converter;

import com.sciatta.openmall.user.pojo.dto.UserDTO;
import com.sciatta.openmall.user.pojo.query.UserQuery;
import com.sciatta.openmall.user.pojo.vo.UserCookieVO;
import com.sciatta.openmall.user.pojo.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangxiaoyu on 2021/8/13<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserConverter
 */
@Mapper
public abstract class UserConverter {
    public static UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    public abstract UserVO toUserVO(UserDTO userDTO);

    public abstract UserCookieVO toUserCookieVO(UserDTO userDTO);

    public abstract UserQuery toUserQuery(String face);
}
