package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.OrderStatusEnum;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.OrderMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderStatusMapper;
import com.sciatta.openmall.dao.pojo.po.ext.OrderStatusItem;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderStatus;
import com.sciatta.openmall.dao.pojo.query.OrderStatusDaoQuery;
import com.sciatta.openmall.service.MyOrderService;
import com.sciatta.openmall.service.converter.MyOrderConverter;
import com.sciatta.openmall.service.converter.OrderConverter;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusItemDTO;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyOrderServiceImpl
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {
    private final OrderStatusMapper orderStatusMapper;
    private final OrderMapper orderMapper;
    
    public MyOrderServiceImpl(OrderStatusMapper orderStatusMapper, OrderMapper orderMapper) {
        this.orderStatusMapper = orderStatusMapper;
        this.orderMapper = orderMapper;
    }
    
    @Override
    public OrderStatusCountsDTO queryOrderStatusCounts(String userId) {
        
        Integer waitPayCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusEnum.WAIT_PAY.type, null));
        
        Integer waitDeliverCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusEnum.WAIT_DELIVER.type, null));
        
        Integer waitReceiveCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusEnum.WAIT_RECEIVE.type, null));
        
        Integer waitCommentCounts = orderStatusMapper.selectOrderStatusCounts(
                MyOrderConverter.INSTANCE.toOrderStatusCountsDaoQuery(userId, OrderStatusEnum.SUCCESS.type, YesOrNo.NO.type));
        
        return MyOrderConverter.INSTANCE.toOrderStatusCountsDTO(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
    }
    
    @Override
    public List<OrderStatusDTO> queryOrdersTrend(String userId, PagedContext pagedContext) {
        OrderStatusDaoQuery orderStatusDaoQuery = MyOrderConverter.INSTANCE.toOrderStatusDaoQuery(userId, YesOrNo.NO.type,
                CollectionUtils.arrayToList(
                        new Integer[]{OrderStatusEnum.WAIT_DELIVER.type, OrderStatusEnum.WAIT_RECEIVE.type, OrderStatusEnum.SUCCESS.type}));
        
        List<OrderStatus> orderStatusList = pagedContext.startPage(
                () -> orderStatusMapper.selectOrderStatus(orderStatusDaoQuery), false);
        
        return OrderConverter.INSTANCE.orderStatusListToOrderStatusDTOList(orderStatusList);
    }
    
    @Override
    public List<OrderStatusItemDTO> queryOrders(String userId, Integer orderStatus, PagedContext pagedContext) {
        OrderStatusDaoQuery orderStatusDaoQuery = MyOrderConverter.INSTANCE.toOrderStatusDaoQuery(userId, YesOrNo.NO.type,
                CollectionUtils.arrayToList(orderStatus == null ? new Integer[]{} : new Integer[]{orderStatus}));
        
        List<OrderStatusItem> orderStatusItemList = pagedContext.startPage(
                () -> orderStatusMapper.selectOrderStatusItem(orderStatusDaoQuery), false);
        
        return OrderConverter.INSTANCE.orderStatusItemListToOrderStatusItemDTOList(orderStatusItemList);
    }
    
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = MyOrderConverter.INSTANCE.toReceiveOrderStatus(OrderStatusEnum.SUCCESS.type,
                new Date());
        
        int result = orderStatusMapper.updateByPrimaryKeyAndOriginalOrderStatusSelective(orderStatus,
                orderId, OrderStatusEnum.WAIT_RECEIVE.type);
        
        return result == YesOrNo.YES.type;
    }
    
    @Override
    public boolean deleteOrderByOrderIdAndUserId(String orderId, String userId) {
        Order order = MyOrderConverter.INSTANCE.toDeleteOrder(YesOrNo.YES.type, new Date());
        
        int result = orderMapper.updateByPrimaryKeyAndUserIdSelective(order, orderId, userId);
        
        return result == YesOrNo.YES.type;
    }
}
