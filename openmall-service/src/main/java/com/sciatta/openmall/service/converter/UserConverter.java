package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.dao.pojo.po.mbg.User;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangxiaoyu on 2021/7/29<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserConverter
 */
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);
    
    UserLoginDTO userToUserLoginDTO(User user);
}
