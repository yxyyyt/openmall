package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.query.UserQuery;
import com.sciatta.openmall.api.pojo.vo.UserCookieVO;
import com.sciatta.openmall.api.pojo.vo.UserVO;
import com.sciatta.openmall.service.pojo.dto.UserDTO;
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
    
    public abstract com.sciatta.openmall.service.pojo.query.UserQuery toService(UserQuery userQuery);
    
    public abstract UserCookieVO toUserCookieVO(UserDTO userDTO);
    
    public abstract com.sciatta.openmall.service.pojo.query.UserQuery toService(String face);
    
    public abstract UserQuery toApi(com.sciatta.openmall.service.pojo.query.UserQuery userQuery);
}
