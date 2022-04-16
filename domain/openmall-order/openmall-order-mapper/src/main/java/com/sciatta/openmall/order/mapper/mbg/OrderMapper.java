package com.sciatta.openmall.order.mapper.mbg;

import com.sciatta.openmall.order.pojo.po.mbg.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}