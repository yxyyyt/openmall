package com.sciatta.openmall.order.mapper.mbg;

import com.sciatta.openmall.order.pojo.po.mbg.OrderStatus;

public interface OrderStatusMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);
}