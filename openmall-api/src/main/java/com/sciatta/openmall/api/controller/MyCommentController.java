package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemCommentConverter;
import com.sciatta.openmall.api.converter.OrderConverter;
import com.sciatta.openmall.api.pojo.query.ItemCommentQuery;
import com.sciatta.openmall.api.pojo.vo.ItemCommentUrlVO;
import com.sciatta.openmall.api.pojo.vo.OrderItemVO;
import com.sciatta.openmall.api.support.validate.ValidList;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.OrderService;
import com.sciatta.openmall.service.pojo.dto.ItemCommentDTO;
import com.sciatta.openmall.service.pojo.dto.OrderDTO;
import com.sciatta.openmall.service.pojo.dto.OrderItemDTO;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 我的评论
 */
@Validated
@RestController
@RequestMapping("mycomments")
public class MyCommentController {
    private final OrderService orderService;
    private final ItemService itemService;
    
    public MyCommentController(OrderService orderService, ItemService itemService) {
        this.orderService = orderService;
        this.itemService = itemService;
    }
    
    @PostMapping("/pending")
    public JSONResult pending(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @NotBlank(message = "订单标识不能为空") String orderId) {
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }
        
        if (ObjectUtils.nullSafeEquals(orderDTO.getIsComment(), YesOrNo.YES.type)) {
            return JSONResult.errorUsingMessage("该笔订单已经评价");
        }
        
        List<OrderItemDTO> orderItemDTOList = orderService.queryOrderItemByOrderId(orderId);
        List<OrderItemVO> orderItemVOList = OrderConverter.INSTANCE.toOrderItemVO(orderItemDTOList);
        
        return JSONResult.success(orderItemVOList);
    }
    
    @PostMapping("/saveList")
    public JSONResult saveList(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam @NotBlank(message = "订单标识不能为空") String orderId,
            @RequestBody @NotEmpty(message = "评价内容不能为空") @Validated ValidList<ItemCommentQuery> itemCommentQueryList) {
        
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);
        
        if (ObjectUtils.isEmpty(orderDTO)) {
            return JSONResult.errorUsingMessage("订单不存在");
        }
        
        itemService.saveComments(orderId, userId,
                ItemCommentConverter.INSTANCE.toItemCommentQuery(itemCommentQueryList));
        
        return JSONResult.success();
    }
    
    @PostMapping("/query")
    public JSONResult query(
            @RequestParam @NotBlank(message = "用户标识不能为空") String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ItemCommentDTO> itemCommentDTOList = itemService.queryComments(userId, pagedContext);
        List<ItemCommentUrlVO> itemCommentUrlVOList =
                ItemCommentConverter.INSTANCE.toItemCommentUrlVO(itemCommentDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemCommentUrlVOList));
    }
}
