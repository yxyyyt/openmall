package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.CommentConverter;
import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.pojo.query.OrderItemCommentApiQuery;
import com.sciatta.openmall.api.pojo.vo.ImageItemCommentVO;
import com.sciatta.openmall.api.pojo.vo.OrderItemVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.service.MyCommentService;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.ImageItemCommentDTO;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    private final MyCommentService myCommentService;
    
    public MyCommentController(OrderService orderService, MyCommentService myCommentService) {
        this.orderService = orderService;
        this.myCommentService = myCommentService;
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
    
    @PostMapping("/saveList")
    public JSONResult saveList(@RequestParam String userId, @RequestParam String orderId,
                               @RequestBody List<OrderItemCommentApiQuery> orderItemCommentApiQueryList) {
        
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }
        
        if (ObjectUtils.isEmpty(orderItemCommentApiQueryList)) {
            return JSONResult.errorUsingMessage("评论内容不能为空");
        }
        
        myCommentService.saveComments(orderId, userId,
                CommentConverter.INSTANCE.orderItemCommentApiQueryListToOrderItemCommentServiceQueryList(orderItemCommentApiQueryList));
        
        return JSONResult.success();
    }
    
    @PostMapping("/query")
    public JSONResult query(@RequestParam String userId, @RequestParam Integer page, @RequestParam Integer pageSize) {
        
        if (!StringUtils.hasText(userId)) {
            return JSONResult.errorUsingMessage("用户不存在");
        }
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ImageItemCommentDTO> imageItemCommentDTOList = myCommentService.queryComments(userId, pagedContext);
        List<ImageItemCommentVO> imageItemCommentVOList =
                CommentConverter.INSTANCE.imageItemCommentDTOListToImageItemCommentVOList(imageItemCommentDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(imageItemCommentVOList));
    }
}
