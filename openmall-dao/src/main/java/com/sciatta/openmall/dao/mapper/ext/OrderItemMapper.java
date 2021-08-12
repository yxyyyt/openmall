package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderItemMapper
 */
public interface OrderItemMapper extends com.sciatta.openmall.dao.mapper.mbg.OrderItemMapper {
    List<OrderItem> selectOrderItemByOrderId(@Param("orderId") String orderId);
    
}
