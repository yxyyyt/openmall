package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.common.enums.OrderStatusCode;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.pojo.po.ext.ShopCartItem;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderItem;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderStatus;
import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderCreateServiceQuery;
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
    
    public List<OrderItem> toOrderItems(List<ShopCartItem> shopCartItemList, Order order) {
        int buyCounts = 1;  // TODO redis get data
        List<OrderItem> orderItemList = new ArrayList<>();
        
        for (ShopCartItem shopCartItem : shopCartItemList) {
            // 创建OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setId(Sid.nextShort());
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(shopCartItem.getItemId());
            orderItem.setItemName(shopCartItem.getItemName());
            orderItem.setItemImg(shopCartItem.getItemImgUrl());
            orderItem.setBuyCounts(buyCounts);
            orderItem.setItemSpecId(shopCartItem.getSpecId());
            orderItem.setItemSpecName(shopCartItem.getSpecName());
            orderItem.setPrice(shopCartItem.getPriceDiscount());
            orderItemList.add(orderItem);
            
            // 统计Order总价格和实际总价格
            order.setTotalAmount((order.getTotalAmount() == null ? 0 : order.getTotalAmount())
                    + shopCartItem.getPriceNormal() * buyCounts);
            order.setRealPayAmount((order.getRealPayAmount() == null ? 0 : order.getRealPayAmount())
                    + shopCartItem.getPriceDiscount() * buyCounts);
        }
        
        return orderItemList;
    }
    
    public OrderStatus toOrderStatus(Order order) {
        OrderStatus orderStatus = new OrderStatus();
        
        orderStatus.setOrderId(order.getId());
        orderStatus.setOrderStatus(OrderStatusCode.WAIT_PAY.type);
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
}
