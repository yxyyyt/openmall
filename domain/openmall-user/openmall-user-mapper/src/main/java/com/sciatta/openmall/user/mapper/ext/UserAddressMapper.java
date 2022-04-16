package com.sciatta.openmall.user.mapper.ext;

import com.sciatta.openmall.user.pojo.po.mbg.UserAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAddressMapper extends com.sciatta.openmall.user.mapper.mbg.UserAddressMapper {
    List<UserAddress> selectByUserId(@Param("userId") String userId);
    
    void deleteByUserIdAndAddressId(@Param("userId") String userId, @Param("addressId") String addressId);
    
    void updateIsDefaultByUserIdAndAddressId(@Param("userId") String userId, @Param("addressId") String addressId,
                                             @Param("isDefault") Integer isDefault);
    
    List<UserAddress> selectByUserIdAndIsDefault(@Param("userId") String userId, @Param("isDefault") Integer isDefault);
    
    UserAddress selectByUserIdAndAddressId(@Param("userId") String userId, @Param("addressId") String addressId);
}