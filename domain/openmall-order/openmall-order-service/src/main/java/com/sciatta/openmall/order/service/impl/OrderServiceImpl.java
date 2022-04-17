package com.sciatta.openmall.order.service.impl;

import com.sciatta.openmall.cache.CacheService;
import com.sciatta.openmall.cart.pojo.query.ShopCartQuery;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.common.utils.JsonUtils;
import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.mapper.support.MapperPagedHelper;
import com.sciatta.openmall.order.api.OrderService;
import com.sciatta.openmall.order.mapper.ext.OrderItemMapper;
import com.sciatta.openmall.order.mapper.ext.OrderMapper;
import com.sciatta.openmall.order.mapper.ext.OrderStatusMapper;
import com.sciatta.openmall.order.pojo.dto.OrderDTO;
import com.sciatta.openmall.order.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.order.pojo.po.ext.OrderStatus;
import com.sciatta.openmall.order.pojo.po.mbg.Order;
import com.sciatta.openmall.order.pojo.po.mbg.OrderItem;
import com.sciatta.openmall.order.pojo.query.OrderQuery;
import com.sciatta.openmall.order.pojo.query.OrderStatusQuery;
import com.sciatta.openmall.order.service.client.ItemServiceFeignClient;
import com.sciatta.openmall.order.service.client.UserAddressServiceFeignClient;
import com.sciatta.openmall.order.service.converter.OrderConverter;
import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.user.pojo.dto.UserAddressDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderServiceImpl
 */
@RestController
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderStatusMapper orderStatusMapper;

    private final UserAddressServiceFeignClient userAddressService;
    private final ItemServiceFeignClient itemService;

    private final CacheService cacheService;

    public OrderServiceImpl(OrderMapper orderMapper,
                            OrderItemMapper orderItemMapper,
                            OrderStatusMapper orderStatusMapper,
                            UserAddressServiceFeignClient userAddressService,
                            ItemServiceFeignClient itemService,
                            CacheService cacheService) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.userAddressService = userAddressService;
        this.itemService = itemService;
        this.cacheService = cacheService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderDTO createOrder(OrderQuery orderQuery) {
        // 创建订单
        Order order = prepareOrder(orderQuery);

        // 创建订单商品，关联更新Order总价格和实际总价格
        List<ShopCartQuery> shopCartList = getShopCartFromCache(orderQuery);
        List<ShopCartQuery> shopCartPaidList = new ArrayList<>();
        List<OrderItem> orderItemList = prepareOrderItems(shopCartList, shopCartPaidList, orderQuery, order);

        // 创建订单状态
        OrderStatus orderStatus = prepareOrderStatus(orderQuery, order);

        // 持久化订单
        doCreateOrder(order, orderItemList, orderStatus);

        // 剔除购物车中未支付的商品
        String unpaidShopCart = getUnPaidShopCart(shopCartList, shopCartPaidList);

        return OrderConverter.INSTANCE.toOrderDTO(order, unpaidShopCart);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderStatusDTO queryOrderStatusByOrderId(String orderId) {
        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);

        return OrderConverter.INSTANCE.toOrderStatusDTO(orderStatus);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteOrderByOrderIdAndUserId(String orderId, String userId) {
        Order order = OrderConverter.INSTANCE.toDeleteOrder(YesOrNo.YES.type, new Date());

        int result = orderMapper.updateByPrimaryKeyAndUserIdSelective(order, orderId, userId);

        return result == YesOrNo.YES.type;
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
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId,
                        com.sciatta.openmall.common.enums.OrderStatus.WAIT_PAY.type, null));

        Integer waitDeliverCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId,
                        com.sciatta.openmall.common.enums.OrderStatus.WAIT_DELIVER.type, null));

        Integer waitReceiveCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId,
                        com.sciatta.openmall.common.enums.OrderStatus.WAIT_RECEIVE.type, null));

        Integer waitCommentCounts = orderStatusMapper.selectOrderStatusCounts(
                OrderConverter.INSTANCE.toOrderStatusCountsQuery(userId,
                        com.sciatta.openmall.common.enums.OrderStatus.SUCCESS.type, YesOrNo.NO.type));

        return OrderConverter.INSTANCE.toOrderStatusCountsDTO(waitPayCounts, waitDeliverCounts, waitReceiveCounts,
                waitCommentCounts);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedGridResult<OrderStatusDTO> queryOrdersTrend(String userId, Integer pageNumber, Integer pageSize) {

        MapperPagedHelper.startPage(pageNumber, pageSize);

        OrderStatusQuery orderStatusQuery = OrderConverter.INSTANCE.toOrderStatusQuery(
                userId,
                YesOrNo.NO.type,
                getOrdersTrendStatus(
                        com.sciatta.openmall.common.enums.OrderStatus.WAIT_DELIVER.type,
                        com.sciatta.openmall.common.enums.OrderStatus.WAIT_RECEIVE.type,
                        com.sciatta.openmall.common.enums.OrderStatus.SUCCESS.type)
        );

        List<OrderStatus> orderStatusList = orderStatusMapper.selectOrderStatus(orderStatusQuery);
        List<OrderStatusDTO> orderStatusDTOList = OrderConverter.INSTANCE.toOrderStatusDTO(orderStatusList);


        return MapperPagedHelper.endPage(orderStatusList, orderStatusDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedGridResult<OrderStatusDTO> queryOrders(String userId, Integer orderStatus, Integer pageNumber, Integer pageSize) {

        MapperPagedHelper.startPage(pageNumber, pageSize);

        OrderStatusQuery orderStatusQuery = OrderConverter.INSTANCE.toOrderStatusQuery(
                userId,
                YesOrNo.NO.type,
                getOrdersTrendStatus(orderStatus));

        List<OrderStatus> orderStatusList = orderStatusMapper.selectOrderStatusWithOrderItem(orderStatusQuery);
        List<OrderStatusDTO> orderStatusDTOList = OrderConverter.INSTANCE.toOrderStatusDTO(orderStatusList);

        return MapperPagedHelper.endPage(orderStatusList, orderStatusDTOList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateDeliverOrderStatus(String orderId) {
        OrderStatus orderStatus = OrderConverter.INSTANCE.toReceiveOrderStatus(
                com.sciatta.openmall.common.enums.OrderStatus.WAIT_RECEIVE.type,
                new Date());

        int result = orderStatusMapper.updateByPrimaryKeyAndOriginalOrderStatusSelective(orderStatus,
                orderId, com.sciatta.openmall.common.enums.OrderStatus.WAIT_DELIVER.type);

        return result == YesOrNo.YES.type;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = OrderConverter.INSTANCE.toReceiveOrderStatus(
                com.sciatta.openmall.common.enums.OrderStatus.SUCCESS.type,
                new Date());

        int result = orderStatusMapper.updateByPrimaryKeyAndOriginalOrderStatusSelective(orderStatus,
                orderId, com.sciatta.openmall.common.enums.OrderStatus.WAIT_RECEIVE.type);

        return result == YesOrNo.YES.type;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateCommented(String orderId) {
        // 更新为已评论
        Order order = OrderConverter.INSTANCE.toOrder(orderId, YesOrNo.YES.type);
        int o = orderMapper.updateByPrimaryKeySelective(order);

        // 更新评论时间
        OrderStatus orderStatus = OrderConverter.INSTANCE.toOrderStatus(orderId, new Date());
        int os = orderStatusMapper.updateByPrimaryKeySelective(orderStatus);

        return o == YesOrNo.YES.type && os == YesOrNo.YES.type;
    }

    // private

    private void doCreateOrder(Order order, List<OrderItem> orderItemList, OrderStatus orderStatus) {
        // 保存订单
        orderMapper.insert(order);

        for (OrderItem orderItem : orderItemList) {
            // 保存订单商品
            orderItemMapper.insert(orderItem);
            // 扣减库存
            itemService.decreaseItemSpecStock(orderItem.getItemSpecId(), orderItem.getBuyCounts());
        }

        // 保存订单状态
        orderStatusMapper.insert(orderStatus);
    }

    private Order prepareOrder(OrderQuery orderQuery) {
        UserAddressDTO userAddressDTO = userAddressService.queryByUserIdAndAddressId(
                orderQuery.getUserId(), orderQuery.getAddressId());
        Assert.notNull(userAddressDTO, "用户地址不合法");

        return OrderConverter.INSTANCE.toOrder(orderQuery, userAddressDTO);
    }

    private List<OrderItem> prepareOrderItems(List<ShopCartQuery> shopCartList,
                                              List<ShopCartQuery> shopCartPaidList,
                                              OrderQuery orderQuery,
                                              Order order) {
        List<ItemDTO> itemDTOList = itemService.queryItemsBySpecIds(orderQuery.getItemSpecIds());

        return OrderConverter.INSTANCE.toOrderItems(shopCartList, shopCartPaidList, itemDTOList, order);
    }

    private OrderStatus prepareOrderStatus(OrderQuery orderQuery, Order order) {
        return OrderConverter.INSTANCE.toOrderStatus(order);
    }

    private List<Integer> getOrdersTrendStatus(Integer... orderStatuses) {
        List<Integer> result = new ArrayList<>();

        Collections.addAll(result, orderStatuses);

        return result;
    }

    private List<ShopCartQuery> getShopCartFromCache(OrderQuery orderQuery) {
        String tokenKey = cacheService.get(CacheConstants.getShopCartTokenKey(orderQuery.getUserId()));
        Assert.hasText(tokenKey, "购物车数据不合法");
        return JsonUtils.jsonToList(tokenKey, ShopCartQuery.class);
    }

    private String getUnPaidShopCart(List<ShopCartQuery> shopCartList, List<ShopCartQuery> shopCartPaidList) {
        shopCartList.removeAll(shopCartPaidList);    // 删除购物车中已支付商品

        return JsonUtils.objectToJson(shopCartList);
    }
}
