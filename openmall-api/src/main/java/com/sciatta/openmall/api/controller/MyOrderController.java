package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.MyOrderConverter;
import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.pojo.vo.OrderStatusCountsVO;
import com.sciatta.openmall.api.pojo.vo.OrderStatusItemVO;
import com.sciatta.openmall.api.pojo.vo.OrderStatusVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.MyOrderService;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusCountsDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusDTO;
import com.sciatta.openmall.service.pojo.dto.OrderStatusItemDTO;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/11<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 我的订单
 */
@RestController
@RequestMapping("myorders")
public class MyOrderController {
    private final MyOrderService myOrderService;
    private final OrderService orderService;
    
    public MyOrderController(MyOrderService myOrderService, OrderService orderService) {
        this.myOrderService = myOrderService;
        this.orderService = orderService;
    }
    
    @PostMapping("statusCounts")
    public JSONResult statusCounts(@RequestParam String userId) {
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        OrderStatusCountsDTO orderStatusCountsDTO = myOrderService.queryOrderStatusCounts(userId);
        OrderStatusCountsVO orderStatusCountsVO = MyOrderConverter.INSTANCE.orderStatusCountsDTOToOrderStatusCountsVO(orderStatusCountsDTO);
        
        return JSONResult.success(orderStatusCountsVO);
    }
    
    @PostMapping("trend")
    public JSONResult trend(@RequestParam String userId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<OrderStatusDTO> orderStatusDTOList = myOrderService.queryOrdersTrend(userId, pagedContext);
        
        List<OrderStatusVO> orderStatusVOList = OrderConverter.INSTANCE.orderStatusDTOListToOrderStatusVOList(orderStatusDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(orderStatusVOList));
    }
    
    @PostMapping("query")
    public JSONResult query(@RequestParam String userId, @RequestParam Integer orderStatus,
                            @RequestParam Integer page, @RequestParam Integer pageSize) {
        
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不能为空");
        }
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<OrderStatusItemDTO> orderStatusItemDTOList = myOrderService.queryOrders(userId, orderStatus, pagedContext);
        
        List<OrderStatusItemVO> orderStatusItemVOList
                = OrderConverter.INSTANCE.orderStatusItemDTOListToOrderStatusItemVOList(orderStatusItemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(orderStatusItemVOList));
    }
    
    @PostMapping("confirmReceive")
    public JSONResult confirmReceive(@RequestParam String orderId, @RequestParam String userId) {
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }
        
        boolean result = myOrderService.updateReceiveOrderStatus(orderId);
        if (!result) {
            return JSONResult.errorUsingMessage("订单确认收货失败！");
        }
        
        return JSONResult.success();
    }
    
    @PostMapping("/delete")
    public JSONResult delete(@RequestParam String orderId, @RequestParam String userId) {
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }
        
        boolean result = myOrderService.deleteOrderByOrderIdAndUserId(orderId, userId);
        if (!result) {
            return JSONResult.errorUsingMessage("订单删除失败！");
        }
        
        return JSONResult.success();
    }
}
