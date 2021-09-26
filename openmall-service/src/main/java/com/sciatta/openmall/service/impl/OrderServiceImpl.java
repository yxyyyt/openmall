package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.ItemMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemSpecMapper;
import com.sciatta.openmall.dao.mapper.ext.UserAddressMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderItemMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderStatusMapper;
import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.*;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.converter.OrderConverter;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartQuery;
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
    public OrderDTO createOrder(List<ShopCartQuery> shopCartQueryList,
                                List<ShopCartQuery> shopCartPaidList,
                                OrderQuery orderQuery) {
        
        // 创建订单
        Order order = doCreateOrder(orderQuery);
        
        // 创建订单商品，关联更新Order总价格和实际总价格
        List<OrderItem> orderItemList = doCreateOrderItems(shopCartQueryList, shopCartPaidList, orderQuery, order);
        
        // 创建订单状态
        OrderStatus orderStatus = doCreateOrderStatus(orderQuery, order);
        
        // 持久化订单
        doSaveOrder(order, orderItemList, orderStatus);
        
        return OrderConverter.INSTANCE.toOrderDTO(order);
    }
    
    @Override
    public OrderStatusDTO queryOrderStatusByOrderId(String orderId) {
        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        
        return OrderConverter.INSTANCE.toOrderStatusDTO(orderStatus);
    }
    
    @Override
    public OrderDTO queryOrderByOrderIdAndUserId(String orderId, String userId) {
        Order order = orderMapper.selectByOrderIdAndUserId(orderId, userId);
        return OrderConverter.INSTANCE.toOrderDTO(order);
    }
    
    @Override
    public List<OrderItemDTO> queryOrderItemByOrderId(String orderId) {
        List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByOrderId(orderId);
        
        return OrderConverter.INSTANCE.toOrderItemDTO(orderItemList);
    }
    
    private void doSaveOrder(Order order, List<OrderItem> orderItemList, OrderStatus orderStatus) {
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
        // TOdo 增加spring断言
        
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
    
    private OrderStatus doCreateOrderStatus(OrderQuery orderQuery, Order order) {
        return OrderConverter.INSTANCE.toOrderStatus(order);
    }
}
