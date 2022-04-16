package com.sciatta.openmall.order.mapper.ext;

import com.sciatta.openmall.order.pojo.po.mbg.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderItemMapper
 */
public interface OrderItemMapper extends com.sciatta.openmall.order.mapper.mbg.OrderItemMapper {
    List<OrderItem> selectOrderItemByOrderId(@Param("orderId") String orderId);
}
