package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.OrderStatusCode;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.OrderStatusMapper;
import com.sciatta.openmall.service.MyOrderService;
import com.sciatta.openmall.service.converter.MyOrderConverter;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import org.springframework.stereotype.Service;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyOrderServiceImpl
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {
    private final OrderStatusMapper orderStatusMapper;
    
    public MyOrderServiceImpl(OrderStatusMapper orderStatusMapper) {
        this.orderStatusMapper = orderStatusMapper;
    }
    
    @Override
    public OrderStatusCountsDTO getOrderStatusCounts(String userId) {
        
        Integer waitPayCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusCode.WAIT_PAY.type, null));
        
        Integer waitDeliverCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusCode.WAIT_DELIVER.type, null));
        
        Integer waitReceiveCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusCode.WAIT_RECEIVE.type, null));
        
        Integer waitCommentCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusCode.SUCCESS.type, YesOrNo.NO.type));
        
        return MyOrderConverter.INSTANCE.toOrderStatusCountsDTO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
    }
}
