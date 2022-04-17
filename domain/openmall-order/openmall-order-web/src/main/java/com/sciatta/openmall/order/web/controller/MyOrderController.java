package com.sciatta.openmall.order.web.controller;

import com.sciatta.openmall.order.api.OrderService;
import com.sciatta.openmall.order.pojo.dto.OrderDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.order.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.order.pojo.query.OrderStatusCountsVO;
import com.sciatta.openmall.order.pojo.query.OrderStatusVO;
import com.sciatta.openmall.order.web.converter.OrderConverter;
import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.pojo.PagedGridResult;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 我的订单
 */
@Validated
@RestController
@RequestMapping("myorders")
public class MyOrderController {
    private final OrderService orderService;

    public MyOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("statusCounts")
    public JSONResult statusCounts(@RequestParam @NotBlank(message = "用户标识不能为空") String userId) {

        OrderStatusCountsDTO orderStatusCountsDTO = orderService.queryOrderStatusCounts(userId);
        OrderStatusCountsVO orderStatusCountsVO = OrderConverter.INSTANCE.toOrderStatusCountsVO(orderStatusCountsDTO);

        return JSONResult.success(orderStatusCountsVO);
    }

    @PostMapping("trend")
    public JSONResult trend(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {

        PagedGridResult<OrderStatusDTO> pagedGridResult = orderService.queryOrdersTrend(userId, page, pageSize);

        return JSONResult.success(
                new PagedGridResult<OrderStatusVO>()
                        .setPageNumber(pagedGridResult.getPageNumber())
                        .setRows(OrderConverter.INSTANCE.toOrderStatusVO(pagedGridResult.getRows()))
                        .setPages(pagedGridResult.getPages())
                        .setTotal(pagedGridResult.getTotal())
        );
    }

    @PostMapping("query")
    public JSONResult query(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @NotNull(message = "订单状态不能为空") Integer orderStatus,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        PagedGridResult<OrderStatusDTO> pagedGridResult = orderService.queryOrders(userId, orderStatus, page, pageSize);

        return JSONResult.success(
                new PagedGridResult<OrderStatusVO>()
                        .setPageNumber(pagedGridResult.getPageNumber())
                        .setRows(OrderConverter.INSTANCE.toOrderStatusVO(pagedGridResult.getRows()))
                        .setPages(pagedGridResult.getPages())
                        .setTotal(pagedGridResult.getTotal())
        );
    }

    @PostMapping("confirmReceive")
    public JSONResult confirmReceive(
            @RequestParam @NotBlank(message = "订单标识不能为空") String orderId,
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId) {

        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }

        boolean result = orderService.updateReceiveOrderStatus(orderId);
        if (!result) {
            return JSONResult.errorUsingMessage("订单确认收货失败！");
        }

        return JSONResult.success();
    }

    @PostMapping("/delete")
    public JSONResult delete(
            @RequestParam @NotBlank(message = "订单标识不能为空") String orderId,
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId) {

        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }

        boolean result = orderService.deleteOrderByOrderIdAndUserId(orderId, userId);
        if (!result) {
            return JSONResult.errorUsingMessage("订单删除失败！");
        }

        return JSONResult.success();
    }
}
