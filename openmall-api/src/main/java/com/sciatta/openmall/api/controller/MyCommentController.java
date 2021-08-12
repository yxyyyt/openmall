package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.pojo.vo.OrderItemVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 我的评论
 */
@RestController
@RequestMapping("mycomments")
public class MyCommentController {
    private final OrderService orderService;
    
    public MyCommentController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/pending")
    public JSONResult pending(@RequestParam String userId, @RequestParam String orderId) {
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }
        
        if (ObjectUtils.nullSafeEquals(orderDTO.getIsComment(), YesOrNo.YES.type)) {
            return JSONResult.errorUsingMessage("该笔订单已经评价");
        }
        
        List<OrderItemDTO> orderItemDTOList = orderService.queryOrderItemByOrderId(orderId);
        List<OrderItemVO> orderItemVOList = OrderConverter.INSTANCE.orderItemDTOListToOrderItemVOList(orderItemDTOList);
        
        return JSONResult.success(orderItemVOList);
    }
}
