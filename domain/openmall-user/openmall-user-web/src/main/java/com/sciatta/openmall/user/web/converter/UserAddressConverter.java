package com.sciatta.openmall.user.web.converter;

import com.sciatta.openmall.user.pojo.dto.UserAddressDTO;
import com.sciatta.openmall.user.pojo.vo.UserAddressVO;
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
}
