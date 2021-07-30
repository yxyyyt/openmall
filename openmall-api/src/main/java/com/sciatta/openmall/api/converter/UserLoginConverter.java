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
public interface UserLoginConverter {
    UserLoginConverter INSTANCE = Mappers.getMapper(UserLoginConverter.class);
    
    UserLoginVO userLoginDTOtoUserLoginVO(UserLoginDTO userLoginDTO);
}
