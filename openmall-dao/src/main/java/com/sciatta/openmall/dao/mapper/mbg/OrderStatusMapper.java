package com.sciatta.openmall.dao.mapper.mbg;

import com.sciatta.openmall.dao.pojo.po.mbg.OrderStatus;

public interface OrderStatusMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderStatus record);

    int insertSelective(OrderStatus record);

    OrderStatus selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderStatus record);

    int updateByPrimaryKey(OrderStatus record);
}