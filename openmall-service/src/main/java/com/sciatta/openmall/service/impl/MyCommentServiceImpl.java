package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.ItemCommentMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderMapper;
import com.sciatta.openmall.dao.mapper.ext.OrderStatusMapper;
import com.sciatta.openmall.dao.pojo.po.ext.ImageItemComment;
import com.sciatta.openmall.dao.pojo.po.ext.ItemComment;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.dao.pojo.po.mbg.OrderStatus;
import com.sciatta.openmall.service.MyCommentService;
import com.sciatta.openmall.service.converter.ItemCommentConverter;
import com.sciatta.openmall.service.converter.MyOrderConverter;
import com.sciatta.openmall.service.pojo.dto.ImageItemCommentDTO;
import com.sciatta.openmall.service.pojo.query.OrderItemCommentServiceQuery;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyCommentServiceImpl
 */
@Service
public class MyCommentServiceImpl implements MyCommentService {
    private final ItemCommentMapper itemCommentMapper;
    private final OrderMapper orderMapper;
    private final OrderStatusMapper orderStatusMapper;
    
    public MyCommentServiceImpl(ItemCommentMapper itemCommentMapper, OrderMapper orderMapper, OrderStatusMapper orderStatusMapper) {
        this.itemCommentMapper = itemCommentMapper;
        this.orderMapper = orderMapper;
        this.orderStatusMapper = orderStatusMapper;
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String orderId, String userId, List<OrderItemCommentServiceQuery> commentList) {
        // 批量插入评论
        List<ItemComment> itemCommentList = ItemCommentConverter.INSTANCE.orderItemCommentServiceQueryListToItemComment(commentList, userId);
        itemCommentMapper.insertBatch(itemCommentList);
        
        // 更新为已评论
        Order order = MyOrderConverter.INSTANCE.toCommentOrder(orderId, YesOrNo.YES.type);
        orderMapper.updateByPrimaryKeySelective(order);
        
        // 更新评论时间
        OrderStatus orderStatus = MyOrderConverter.INSTANCE.toCommentOrderStatus(orderId, new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
    
    @Override
    public List<ImageItemCommentDTO> queryComments(String userId, PagedContext pagedContext) {
        List<ImageItemComment> imageItemCommentList =
                pagedContext.startPage(() -> itemCommentMapper.selectItemCommentsByUserId(userId), false);
        
        return MyOrderConverter.INSTANCE.imageItemCommentListToImageItemCommentDTOList(imageItemCommentList);
    }
}
