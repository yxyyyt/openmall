package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.common.enums.OrderStatusEnum;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.pojo.po.ext.OrderStatusItem;
import com.sciatta.openmall.dao.pojo.po.ext.ShopCartItem;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderItem;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderStatus;
import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusItemDTO;
import com.sciatta.openmall.service.pojo.query.OrderCreateServiceQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartAddServiceQuery;
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
    
    public Order toOrder(OrderCreateServiceQuery orderCreateServiceQuery,
                         UserAddress userAddress) {
        Order order = new Order();
        
        order.setId(Sid.nextShort());
        order.setUserId(userAddress.getUserId());
        order.setReceiverName(userAddress.getReceiver());
        order.setReceiverMobile(userAddress.getMobile());
        order.setReceiverAddress(userAddress.getProvince() + " "
                + userAddress.getCity() + " "
                + userAddress.getDistrict() + " "
                + userAddress.getDetail());
        
        order.setPostAmount(0); // 默认包邮
        
        order.setPayMethod(orderCreateServiceQuery.getPayMethod());
        order.setLeftMsg(orderCreateServiceQuery.getLeftMsg());
        
        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());
        
        return order;
    }
    
    public List<OrderItem> toOrderItems(List<ShopCartAddServiceQuery> shopCartAddServiceQueryList,
                                        List<ShopCartAddServiceQuery> paidShopCartList,
                                        List<ShopCartItem> shopCartItemList,
                                        Order order) {
        List<OrderItem> orderItemList = new ArrayList<>();
        
        for (ShopCartItem shopCartItem : shopCartItemList) {
            // 创建OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setId(Sid.nextShort());
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(shopCartItem.getItemId());
            orderItem.setItemName(shopCartItem.getItemName());
            orderItem.setItemImg(shopCartItem.getItemImgUrl());
            orderItem.setBuyCounts(getBuyCountsFromShopCart(shopCartAddServiceQueryList, paidShopCartList, shopCartItem.getSpecId()));
            orderItem.setItemSpecId(shopCartItem.getSpecId());
            orderItem.setItemSpecName(shopCartItem.getSpecName());
            orderItem.setPrice(shopCartItem.getPriceDiscount());
            orderItemList.add(orderItem);
            
            // 统计Order总价格和实际总价格
            order.setTotalAmount((order.getTotalAmount() == null ? 0 : order.getTotalAmount())
                    + shopCartItem.getPriceNormal() * orderItem.getBuyCounts());
            order.setRealPayAmount((order.getRealPayAmount() == null ? 0 : order.getRealPayAmount())
                    + shopCartItem.getPriceDiscount() * orderItem.getBuyCounts());
        }
        
        return orderItemList;
    }
    
    private Integer getBuyCountsFromShopCart(List<ShopCartAddServiceQuery> shopCartAddServiceQueryList,
                                             List<ShopCartAddServiceQuery> paidShopCartList,
                                             String itemSpecId) {
        for (ShopCartAddServiceQuery shopCartAddServiceQuery : shopCartAddServiceQueryList) {
            if (shopCartAddServiceQuery.getSpecId().equals(itemSpecId)) {
                paidShopCartList.add(shopCartAddServiceQuery);  // 购物车中已支付的商品
                return shopCartAddServiceQuery.getBuyCounts();
            }
        }
        log.error(itemSpecId + " not in shop cart");
        throw new IllegalStateException(itemSpecId + " not in shop cart");
    }
    
    public OrderStatus toOrderStatus(Order order) {
        OrderStatus orderStatus = new OrderStatus();
        
        orderStatus.setOrderId(order.getId());
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        
        return orderStatus;
    }
    
    @Mappings({
            @Mapping(source = "id", target = "orderId"),
            @Mapping(source = "id", target = "merchantOrderDTO.merchantOrderId"),
            @Mapping(source = "userId", target = "merchantOrderDTO.merchantUserId"),
            @Mapping(expression = "java(java.lang.Math.addExact(order.getRealPayAmount(),order.getPostAmount()))",
                    target = "merchantOrderDTO.amount"),
            @Mapping(source = "payMethod", target = "merchantOrderDTO.payMethod")
    })
    public abstract OrderDTO orderToOrderDTO(Order order);
    
    public abstract OrderStatusDTO orderStatusToOrderStatusDTO(OrderStatus orderStatus);
    
    public abstract List<OrderStatusDTO> orderStatusListToOrderStatusDTOList(List<OrderStatus> orderStatusList);
    
    public abstract OrderStatusItemDTO orderStatusItemToOrderStatusItemDTO(OrderStatusItem orderStatusItem);
    
    public abstract List<OrderStatusItemDTO> orderStatusItemListToOrderStatusItemDTOList(List<OrderStatusItem> orderStatusItemList);
    
    public abstract List<OrderItemDTO> orderItemListToOrderItemDTOList(List<OrderItem> orderItemList);
}
