package com.sciatta.openmall.order.service.converter;

import com.sciatta.openmall.cart.pojo.query.ShopCartQuery;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.order.pojo.dto.OrderDTO;
import com.sciatta.openmall.order.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.order.pojo.po.ext.OrderStatus;
import com.sciatta.openmall.order.pojo.po.mbg.Order;
import com.sciatta.openmall.order.pojo.po.mbg.OrderItem;
import com.sciatta.openmall.order.pojo.query.OrderQuery;
import com.sciatta.openmall.order.pojo.query.OrderStatusCountsQuery;
import com.sciatta.openmall.order.pojo.query.OrderStatusQuery;
import com.sciatta.openmall.user.pojo.dto.UserAddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.n3r.idworker.Sid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * OrderConverter
 */
@Mapper
@Slf4j
public abstract class OrderConverter {
    public static OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    public Order toOrder(OrderQuery orderQuery,
                         UserAddressDTO userAddressDTO) {
        Order order = new Order();

        order.setId(Sid.nextShort());
        order.setUserId(userAddressDTO.getUserId());
        order.setReceiverName(userAddressDTO.getReceiver());
        order.setReceiverMobile(userAddressDTO.getMobile());
        order.setReceiverAddress(userAddressDTO.getProvince() + " "
                + userAddressDTO.getCity() + " "
                + userAddressDTO.getDistrict() + " "
                + userAddressDTO.getDetail());

        order.setPostAmount(0); // 默认包邮

        order.setPayMethod(orderQuery.getPayMethod());
        order.setLeftMsg(orderQuery.getLeftMsg());

        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());

        return order;
    }

    public abstract Order toOrder(String id, Integer isComment);

    public List<OrderItem> toOrderItems(List<ShopCartQuery> shopCartList,
                                        List<ShopCartQuery> shopCartPaidList,
                                        List<ItemDTO> itemDTOList,
                                        Order order) {
        List<OrderItem> orderItemList = new ArrayList<>();

        for (ItemDTO item : itemDTOList) {
            // 创建OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setId(Sid.nextShort());
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(item.getId());
            orderItem.setItemName(item.getItemName());
            orderItem.setItemImg(item.getUrl());
            // 获取购物车中的商品数量
            orderItem.setBuyCounts(getBuyCountsFromShopCart(shopCartList, shopCartPaidList, item.getSpecId()));
            orderItem.setItemSpecId(item.getSpecId());
            orderItem.setItemSpecName(item.getSpecName());
            orderItem.setPrice(item.getPriceDiscount());    // 订单商品中的价格是打折后的价格
            orderItemList.add(orderItem);

            // 统计Order总价格和实际总价格
            order.setTotalAmount((order.getTotalAmount() == null ? 0 : order.getTotalAmount())
                    + item.getPriceNormal() * orderItem.getBuyCounts());
            order.setRealPayAmount((order.getRealPayAmount() == null ? 0 : order.getRealPayAmount())
                    + item.getPriceDiscount() * orderItem.getBuyCounts());
        }

        return orderItemList;
    }

    public OrderStatus toOrderStatus(Order order) {
        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setOrderId(order.getId());
        orderStatus.setOrderStatus(com.sciatta.openmall.common.enums.OrderStatus.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());

        return orderStatus;
    }

    public abstract OrderStatus toOrderStatus(String orderId, Date commentTime);

    @Mappings({
            @Mapping(source = "order.id", target = "orderId"),
            @Mapping(source = "order.id", target = "merchantOrderDTO.merchantOrderId"),
            @Mapping(source = "order.userId", target = "merchantOrderDTO.merchantUserId"),
            @Mapping(expression = "java(java.lang.Math.addExact(order.getRealPayAmount(),order.getPostAmount()))",
                    target = "merchantOrderDTO.amount"),
            @Mapping(source = "order.payMethod", target = "merchantOrderDTO.payMethod"),
            @Mapping(source = "unpaidShopCart", target = "unPaidShopCart")
    })
    public abstract OrderDTO toOrderDTO(Order order, String unpaidShopCart);

    @Mappings({
            @Mapping(source = "id", target = "orderId")
    })
    public abstract OrderDTO toOrderDTO(Order order);

    public abstract OrderStatusDTO toOrderStatusDTO(OrderStatus orderStatus);

    public abstract List<OrderStatusDTO> toOrderStatusDTO(List<OrderStatus> orderStatusList);

    public abstract List<OrderItemDTO> toOrderItemDTO(List<OrderItem> orderItemList);

    public abstract OrderStatusCountsQuery toOrderStatusCountsQuery(String userId, Integer orderStatus, Integer isComment);

    public abstract OrderStatusCountsDTO toOrderStatusCountsDTO(Integer waitPayCounts,
                                                                Integer waitDeliverCounts,
                                                                Integer waitReceiveCounts,
                                                                Integer waitCommentCounts);


    public abstract OrderStatusQuery toOrderStatusQuery(String userId, Integer isDelete, List<Integer> orderStatuses);

    public abstract OrderStatus toReceiveOrderStatus(Integer orderStatus, Date successTime);

    public abstract Order toDeleteOrder(Integer isDelete, Date updatedTime);

    private Integer getBuyCountsFromShopCart(List<ShopCartQuery> shopCartList,
                                             List<ShopCartQuery> shopCartPaidList,
                                             String itemSpecId) {
        for (ShopCartQuery shopCartQuery : shopCartList) {
            if (shopCartQuery.getSpecId().equals(itemSpecId)) {
                shopCartPaidList.add(shopCartQuery);  // 缓存购物车中已支付的商品
                return shopCartQuery.getBuyCounts();
            }
        }
        log.error(itemSpecId + " not in shop cart");
        throw new IllegalStateException(itemSpecId + " not in shop cart");
    }
}
