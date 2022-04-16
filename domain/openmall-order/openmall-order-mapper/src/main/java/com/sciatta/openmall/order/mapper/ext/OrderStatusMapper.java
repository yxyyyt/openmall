package com.sciatta.openmall.order.mapper.ext;

import com.sciatta.openmall.order.pojo.po.ext.OrderStatus;
import com.sciatta.openmall.order.pojo.query.OrderStatusCountsQuery;
import com.sciatta.openmall.order.pojo.query.OrderStatusQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderStatusMapper
 */
public interface OrderStatusMapper extends com.sciatta.openmall.order.mapper.mbg.OrderStatusMapper {
    Integer selectOrderStatusCounts(OrderStatusCountsQuery orderStatusCountsQuery);

    List<OrderStatus> selectOrderStatus(OrderStatusQuery orderStatusQuery);

    List<OrderStatus> selectOrderStatusWithOrderItem(OrderStatusQuery orderStatusQuery);

    int updateByPrimaryKeyAndOriginalOrderStatusSelective(@Param("orderStatus") OrderStatus orderStatus,
                                                          @Param("orderId") String orderId,
                                                          @Param("originalOrderStatus") Integer originalOrderStatus);

    OrderStatus selectByPrimaryKey(String orderId);
}
