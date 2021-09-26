package com.sciatta.openmall.service.converter;

import com.sciatta.openmall.common.enums.OrderStatus;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderItem;
import com.sciatta.openmall.dao.pojo.po.mbg.UserAddress;
import com.sciatta.openmall.dao.pojo.query.OrderStatusCountsQuery;
import com.sciatta.openmall.dao.pojo.query.OrderStatusQuery;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.query.OrderQuery;
import com.sciatta.openmall.service.pojo.query.ShopCartQuery;
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
        
        order.setPayMethod(orderQuery.getPayMethod());
        order.setLeftMsg(orderQuery.getLeftMsg());
        
        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setCreatedTime(new Date());
        order.setUpdatedTime(new Date());
        
        return order;
    }
    
    public abstract Order toOrder(String id, Integer isComment);
    
    public List<OrderItem> toOrderItems(List<ShopCartQuery> shopCartQueryList,
                                        List<ShopCartQuery> shopCartPaidList,
                                        List<Item> itemList,
                                        Order order) {
        List<OrderItem> orderItemList = new ArrayList<>();
        
        for (Item item : itemList) {
            // 创建OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setId(Sid.nextShort());
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(item.getId());
            orderItem.setItemName(item.getItemName());
            orderItem.setItemImg(item.getUrl());
            orderItem.setBuyCounts(getBuyCountsFromShopCart(shopCartQueryList, shopCartPaidList, item.getSpecId()));    // 获取购物车中的商品数量
            orderItem.setItemSpecId(item.getSpecId());
            orderItem.setItemSpecName(item.getSpecName());
            orderItem.setPrice(item.getPriceDiscount());
            orderItemList.add(orderItem);
            
            // 统计Order总价格和实际总价格
            order.setTotalAmount((order.getTotalAmount() == null ? 0 : order.getTotalAmount())
                    + item.getPriceNormal() * orderItem.getBuyCounts());
            order.setRealPayAmount((order.getRealPayAmount() == null ? 0 : order.getRealPayAmount())
                    + item.getPriceDiscount() * orderItem.getBuyCounts());
        }
        
        return orderItemList;
    }
    
    public com.sciatta.openmall.dao.pojo.po.ext.OrderStatus toOrderStatus(Order order) {
        com.sciatta.openmall.dao.pojo.po.ext.OrderStatus orderStatus = new com.sciatta.openmall.dao.pojo.po.ext.OrderStatus();
        
        orderStatus.setOrderId(order.getId());
        orderStatus.setOrderStatus(OrderStatus.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        
        return orderStatus;
    }
    
    public abstract com.sciatta.openmall.dao.pojo.po.ext.OrderStatus toOrderStatus(String orderId, Date commentTime);
    
    @Mappings({
            @Mapping(source = "id", target = "orderId"),
            @Mapping(source = "id", target = "merchantOrderDTO.merchantOrderId"),
            @Mapping(source = "userId", target = "merchantOrderDTO.merchantUserId"),
            @Mapping(expression = "java(java.lang.Math.addExact(order.getRealPayAmount(),order.getPostAmount()))",
                    target = "merchantOrderDTO.amount"),
            @Mapping(source = "payMethod", target = "merchantOrderDTO.payMethod")
    })
    public abstract OrderDTO toOrderDTO(Order order);
    
    public abstract OrderStatusDTO toOrderStatusDTO(com.sciatta.openmall.dao.pojo.po.ext.OrderStatus orderStatus);
    
    public abstract List<OrderStatusDTO> toOrderStatusDTO(List<com.sciatta.openmall.dao.pojo.po.ext.OrderStatus> orderStatusList);
    
    public abstract List<OrderItemDTO> toOrderItemDTO(List<OrderItem> orderItemList);
    
    public abstract OrderStatusCountsQuery toOrderStatusCountsQuery(String userId, Integer orderStatus, Integer isComment);
    
    public abstract OrderStatusCountsDTO toOrderStatusCountsDTO(Integer waitPayCounts,
                                                                Integer waitDeliverCounts,
                                                                Integer waitReceiveCounts,
                                                                Integer waitCommentCounts);
    
    
    public abstract OrderStatusQuery toOrderStatusQuery(String userId, Integer isDelete, List<Integer> orderStatuses);
    
    public abstract com.sciatta.openmall.dao.pojo.po.ext.OrderStatus toReceiveOrderStatus(Integer orderStatus, Date successTime);
    
    public abstract Order toDeleteOrder(Integer isDelete, Date updatedTime);
    
    private Integer getBuyCountsFromShopCart(List<ShopCartQuery> shopCartQueryList,
                                             List<ShopCartQuery> shopCartPaidList,
                                             String itemSpecId) {
        for (ShopCartQuery shopCartQuery : shopCartQueryList) {
            if (shopCartQuery.getSpecId().equals(itemSpecId)) {
                shopCartPaidList.add(shopCartQuery);  // 从购物车中移除已支付的商品
                return shopCartQuery.getBuyCounts();
            }
        }
        log.error(itemSpecId + " not in shop cart");
        throw new IllegalStateException(itemSpecId + " not in shop cart");
    }
}
