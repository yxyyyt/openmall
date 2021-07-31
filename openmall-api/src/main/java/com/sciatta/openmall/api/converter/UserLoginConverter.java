package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.UserLoginVO;
import com.sciatta.openmall.service.pojo.dto.UserLoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by yangxiaoyu on 2021/7/30<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserLoginConverter
 */
@Mapper
public abstract class UserLoginConverter {
    public static UserLoginConverter INSTANCE = Mappers.getMapper(UserLoginConverter.class);
    
    public abstract UserLoginVO userLoginDTOtoUserLoginVO(UserLoginDTO userLoginDTO);
}
