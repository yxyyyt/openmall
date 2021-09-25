package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.UserAddressVO;
import com.sciatta.openmall.service.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.service.pojo.query.UserAddressQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * UserAddressConverter
 */
@Mapper
public abstract class UserAddressConverter {
    public static UserAddressConverter INSTANCE = Mappers.getMapper(UserAddressConverter.class);
    
    public abstract List<UserAddressVO> toUserAddressVO(List<UserAddressDTO> userAddressDTOList);
    
    public abstract UserAddressQuery toUserAddressQuery(com.sciatta.openmall.api.pojo.query.UserAddressQuery userAddressQuery);
}
