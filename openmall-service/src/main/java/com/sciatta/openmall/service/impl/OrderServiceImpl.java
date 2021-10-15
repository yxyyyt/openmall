package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.OrderStatus;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.*;
import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderItem;
import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.dao.pojo.query.OrderStatusQuery;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.converter.OrderConverter;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartQuery;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderServiceImpl
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final ItemMapper itemMapper;
    private final UserAddressMapper userAddressMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final ItemSpecMapper itemSpecMapper;
    
    public OrderServiceImpl(ItemMapper itemMapper,
                            UserAddressMapper userAddressMapper,
                            OrderMapper orderMapper,
                            OrderItemMapper orderItemMapper,
                            OrderStatusMapper orderStatusMapper,
                            ItemSpecMapper itemSpecMapper) {
        this.itemMapper = itemMapper;
        this.userAddressMapper = userAddressMapper;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.itemSpecMapper = itemSpecMapper;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDTO createOrder(List<ShopCartQuery> shopCartQueryList,
                                List<ShopCartQuery> shopCartPaidList,
                                OrderQuery orderQuery) {
        // 创建订单
        Order order = doCreateOrder(orderQuery);
        
        // 创建订单商品，关联更新Order总价格和实际总价格
        List<OrderItem> orderItemList = doCreateOrderItems(shopCartQueryList, shopCartPaidList, orderQuery, order);
        
        // 创建订单状态
        com.sciatta.openmall.dao.pojo.po.ext.OrderStatus orderStatus = doCreateOrderStatus(orderQuery, order);
        
        // 持久化订单
        doSaveOrder(order, orderItemList, orderStatus);
        
        return OrderConverter.INSTANCE.toOrderDTO(order);
    }
    
    @Override
    @Transactional(readOnly = true)
    public OrderStatusDTO queryOrderStatusByOrderId(String orderId) {
        com.sciatta.openmall.dao.pojo.po.ext.OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        
        return OrderConverter.INSTANCE.toOrderStatusDTO(orderStatus);
    }
    
    @Override
    @Transactional(readOnly = true)
    public OrderDTO queryOrderByOrderIdAndUserId(String orderId, String userId) {
        Order order = orderMapper.selectByOrderIdAndUserId(orderId, userId);
        return OrderConverter.INSTANCE.toOrderDTO(order);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDTO> queryOrderItemByOrderId(String orderId) {
        List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByOrderId(orderId);
        
        return OrderConverter.INSTANCE.toOrderItemDTO(orderItemList);
    }
    
    @Override
    @Transactional(readOnly = true)
    public OrderStatusCountsDTO queryOrderStatusCounts(String userId) {
        Integer waitPayCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId, OrderStatus.WAIT_PAY.type, null));
        
        Integer waitDeliverCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId, OrderStatus.WAIT_DELIVER.type, null));
        
        Integer waitReceiveCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId, OrderStatus.WAIT_RECEIVE.type, null));
        
        Integer waitCommentCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId, OrderStatus.SUCCESS.type, YesOrNo.NO.type));
        
        return OrderConverter.INSTANCE.toOrderStatusCountsDTO(
                waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusDTO> queryOrdersTrend(String userId,
                                                 PagedContext<com.sciatta.openmall.dao.pojo.po.ext.OrderStatus> pagedContext) {
        OrderStatusQuery orderStatusQuery = OrderConverter.INSTANCE.toOrderStatusQuery(
                userId,
                YesOrNo.NO.type,
                getOrdersTrendStatus(
                        OrderStatus.WAIT_DELIVER.type,
                        OrderStatus.WAIT_RECEIVE.type,
                        OrderStatus.SUCCESS.type)
        );
        
        List<com.sciatta.openmall.dao.pojo.po.ext.OrderStatus> orderStatusList = pagedContext.startPage(
                () -> orderStatusMapper.selectOrderStatus(orderStatusQuery), false);
        
        return OrderConverter.INSTANCE.toOrderStatusDTO(orderStatusList);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusDTO> queryOrders(String userId, Integer orderStatus,
                                            PagedContext<com.sciatta.openmall.dao.pojo.po.ext.OrderStatus> pagedContext) {
        
        OrderStatusQuery orderStatusQuery = OrderConverter.INSTANCE.toOrderStatusQuery(
                userId,
                YesOrNo.NO.type,
                getOrdersTrendStatus(orderStatus));
        
        List<com.sciatta.openmall.dao.pojo.po.ext.OrderStatus> orderStatusList = pagedContext.startPage(
                () -> orderStatusMapper.selectOrderStatusWithOrderItem(orderStatusQuery), false);
        
        return OrderConverter.INSTANCE.toOrderStatusDTO(orderStatusList);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateReceiveOrderStatus(String orderId) {
        com.sciatta.openmall.dao.pojo.po.ext.OrderStatus orderStatus = OrderConverter.INSTANCE.toReceiveOrderStatus(OrderStatus.SUCCESS.type,
                new Date());
        
        int result = orderStatusMapper.updateByPrimaryKeyAndOriginalOrderStatusSelective(orderStatus,
                orderId, OrderStatus.WAIT_RECEIVE.type);
        
        return result == YesOrNo.YES.type;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteOrderByOrderIdAndUserId(String orderId, String userId) {
        Order order = OrderConverter.INSTANCE.toDeleteOrder(YesOrNo.YES.type, new Date());
        
        int result = orderMapper.updateByPrimaryKeyAndUserIdSelective(order, orderId, userId);
        
        return result == YesOrNo.YES.type;
    }
    
    // /////////////////////////////////////////////////////////////////////////////////////////
    // private
    
    private void doSaveOrder(Order order, List<OrderItem> orderItemList, com.sciatta.openmall.dao.pojo.po.ext.OrderStatus orderStatus) {
        // 保存订单
        orderMapper.insert(order);
        
        for (OrderItem orderItem : orderItemList) {
            // 保存订单商品
            orderItemMapper.insert(orderItem);
            // 减库存
            itemSpecMapper.decreaseItemSpecStock(orderItem.getItemSpecId(), orderItem.getBuyCounts());
        }
        
        // 保存订单状态
        orderStatusMapper.insert(orderStatus);
    }
    
    private Order doCreateOrder(OrderQuery orderQuery) {
        UserAddress userAddress = userAddressMapper.selectByUserIdAndAddressId(orderQuery.getUserId(),
                orderQuery.getAddressId());
        Assert.notNull(userAddress, "用户地址不合法");
        
        return OrderConverter.INSTANCE.toOrder(orderQuery, userAddress);
    }
    
    private List<OrderItem> doCreateOrderItems(List<ShopCartQuery> shopCartQueryList,
                                               List<ShopCartQuery> shopCartPaidList,
                                               OrderQuery orderQuery,
                                               Order order) {
        List<String> specIdList = CollectionUtils.arrayToList(orderQuery.getItemSpecIds().split(","));
        List<Item> itemList = itemMapper.searchShopCartItemsBySpecIds(specIdList);
        
        return OrderConverter.INSTANCE.toOrderItems(shopCartQueryList, shopCartPaidList, itemList, order);
    }
    
    private com.sciatta.openmall.dao.pojo.po.ext.OrderStatus doCreateOrderStatus(OrderQuery orderQuery, Order order) {
        return OrderConverter.INSTANCE.toOrderStatus(order);
    }
    
    private List<Integer> getOrdersTrendStatus(Integer... orderStatuses) {
        List<Integer> result = new ArrayList<>();
        
        Collections.addAll(result, orderStatuses);
        
        return result;
    }
}
