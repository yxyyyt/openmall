package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.query.UserRegisterApiQuery;
import com.sciatta.openmall.service.pojo.query.UserRegisterServiceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserRegisterConverter
 */
@Mapper
public abstract class UserRegisterConverter {
    
    public static UserRegisterConverter INSTANCE = Mappers.getMapper(UserRegisterConverter.class);
    
    public abstract UserRegisterServiceQuery userRegisterApiQueryToUserRegisterServiceQuery(UserRegisterApiQuery userRegisterApiQuery);
}
