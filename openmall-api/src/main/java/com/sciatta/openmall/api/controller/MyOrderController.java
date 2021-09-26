package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.pojo.vo.OrderStatusCountsVO;
import com.sciatta.openmall.api.pojo.vo.OrderStatusVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<OrderStatusDTO> orderStatusDTOList = orderService.queryOrdersTrend(userId, pagedContext);
        
        List<OrderStatusVO> orderStatusVOList = OrderConverter.INSTANCE.toOrderStatusVO(orderStatusDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(orderStatusVOList));
    }
    
    @PostMapping("query")
    public JSONResult query(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @NotNull(message = "订单状态不能为空") Integer orderStatus,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<OrderStatusDTO> orderStatusDTOList = orderService.queryOrders(userId, orderStatus, pagedContext);
        
        List<OrderStatusVO> orderStatusVOList = OrderConverter.INSTANCE.toOrderStatusVO(orderStatusDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(orderStatusVOList));
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
