package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAddressMapper extends com.sciatta.openmall.dao.mapper.mbg.UserAddressMapper {
    List<UserAddress> selectByUserId(@Param("userId") String userId);
    
    void deleteByUserIdAndAddressId(@Param("userId") String userId, @Param("addressId") String addressId);
    
    void updateIsDefaultByUserIdAndAddressId(@Param("userId") String userId, @Param("addressId") String addressId,
                                             @Param("isDefault") Integer isDefault);
    
    List<UserAddress> selectByUserIdAndIsDefault(@Param("userId") String userId, @Param("isDefault") Integer isDefault);
}