package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import org.apache.ibatis.annotations.Param;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderMapper
 */
public interface OrderMapper extends com.sciatta.openmall.dao.mapper.mbg.OrderMapper {
    Order selectByOrderIdAndUserId(@Param("orderId") String orderId, @Param("userId") String userId);
    
    int updateByPrimaryKeyAndUserIdSelective(@Param("order") Order order, @Param("orderId") String orderId, @Param("userId") String userId);
}
