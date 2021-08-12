package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.ItemMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemSpecMapper;
import com.sciatta.openmall.dao.mapper.ext.UserAddressMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderItemMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderStatusMapper;
import com.sciatta.openmall.dao.pojo.po.ext.ShopCartItem;
import com.sciatta.openmall.dao.pojo.po.mbg.*;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.converter.OrderConverter;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderCreateServiceQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderDTO createOrder(OrderCreateServiceQuery orderCreateServiceQuery) {
        
        // 创建订单
        Order order = doCreateOrder(orderCreateServiceQuery);
        
        // 创建订单商品，关联更新Order总价格和实际总价格
        List<OrderItem> orderItemList = doCreateOrderItems(orderCreateServiceQuery, order);
        
        // 创建订单状态
        OrderStatus orderStatus = doCreateOrderStatus(orderCreateServiceQuery, order);
        
        // 持久化订单
        saveOrder(order, orderItemList, orderStatus);
        
        return OrderConverter.INSTANCE.orderToOrderDTO(order);
    }
    
    private void saveOrder(Order order, List<OrderItem> orderItemList, OrderStatus orderStatus) {
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
    
    private Order doCreateOrder(OrderCreateServiceQuery orderCreateServiceQuery) {
        UserAddress userAddress = userAddressMapper.selectByUserIdAndAddressId(orderCreateServiceQuery.getUserId(),
                orderCreateServiceQuery.getAddressId());
        
        return OrderConverter.INSTANCE.toOrder(orderCreateServiceQuery, userAddress);
    }
    
    private List<OrderItem> doCreateOrderItems(OrderCreateServiceQuery orderCreateServiceQuery, Order order) {
        
        List<String> idList = CollectionUtils.arrayToList(
                orderCreateServiceQuery.getItemSpecIds().split(","));
        
        List<ShopCartItem> shopCartItemList = itemMapper.searchShopCartItemsBySpecIds(idList);
        
        return OrderConverter.INSTANCE.toOrderItems(shopCartItemList, order);
    }
    
    private OrderStatus doCreateOrderStatus(OrderCreateServiceQuery orderCreateServiceQuery, Order order) {
        return OrderConverter.INSTANCE.toOrderStatus(order);
    }
    
    @Override
    public OrderStatusDTO queryOrderStatusByOrderId(String orderId) {
        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        
        return OrderConverter.INSTANCE.orderStatusToOrderStatusDTO(orderStatus);
    }
    
    @Override
    public OrderDTO queryOrderByOrderIdAndUserId(String orderId, String userId) {
        Order order = orderMapper.selectByOrderIdAndUserId(orderId, userId);
        return OrderConverter.INSTANCE.orderToOrderDTO(order);
    }
}
