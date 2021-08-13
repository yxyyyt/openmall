package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.query.UserApiQuery;
import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.api.pojo.vo.UserVO;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
import com.sciatta.openmall.service.pojo.query.UserServiceQuery;
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
    
    public abstract UserVO userDTOToUserVO(UserDTO userDTO);
    
    public abstract UserServiceQuery userApiQueryToUserServiceQuery(UserApiQuery userApiQuery);
    
    public abstract UserCookieVO userDTOToUserCookieVO(UserDTO userDTO);
}
