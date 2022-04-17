package com.sciatta.openmall.order.api;

import com.sciatta.openmall.order.pojo.dto.OrderDTO;
import com.sciatta.openmall.order.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.order.pojo.query.OrderQuery;
import com.sciatta.openmall.pojo.PagedGridResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/10<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 订单服务
 */
@FeignClient("openmall-order-service")
@RequestMapping("order-api")
public interface OrderService {
    @PostMapping("placeOrder")
    OrderDTO createOrder(@RequestBody OrderQuery orderQuery);

    @GetMapping("orderStatus")
    OrderStatusDTO queryOrderStatusByOrderId(@RequestParam("orderId") String orderId);

    @DeleteMapping("order")
    boolean deleteOrderByOrderIdAndUserId(
            @RequestParam("orderId") String orderId, @RequestParam("userId") String userId);

    @GetMapping("order/details")
    OrderDTO queryOrderByOrderIdAndUserId(
            @RequestParam("orderId") String orderId,
            @RequestParam("userId") String userId);

    @GetMapping("order/items")
    List<OrderItemDTO> queryOrderItemByOrderId(@RequestParam("orderId") String orderId);

    @GetMapping("order/counts")
    OrderStatusCountsDTO queryOrderStatusCounts(@RequestParam("userId") String userId);

    @GetMapping("order/trend")
    PagedGridResult<OrderStatusDTO> queryOrdersTrend(
            @RequestParam("userId") String userId,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    /**
     * 查询订单关联的所有商品，注意此处由于使用分页的原因需要使用级联即时查询
     *
     * @param userId      用户外键
     * @param orderStatus 订单状态
     * @param pageNumber
     * @param pageSize
     * @return OrderStatusItemDTO集合
     */
    @GetMapping("order/query")
    PagedGridResult<OrderStatusDTO> queryOrders(
            @RequestParam("userId") String userId,
            @RequestParam("orderStatus") Integer orderStatus,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    );

    @PostMapping("order/delivered")
    boolean updateDeliverOrderStatus(@RequestParam("orderId") String orderId);

    @PostMapping("order/received")
    boolean updateReceiveOrderStatus(@RequestParam("orderId") String orderId);

    @PostMapping("order/commented")
    boolean updateCommented(@RequestParam("orderId") String orderId);
}
