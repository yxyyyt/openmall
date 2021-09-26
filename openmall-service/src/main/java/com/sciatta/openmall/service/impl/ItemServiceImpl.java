package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.CommentLevel;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.dao.mapper.ext.*;
import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.ext.ItemComment;
import com.sciatta.openmall.dao.pojo.po.ext.OrderStatus;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.dao.pojo.po.mbg.Order;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.converter.ItemCommentConverter;
import com.sciatta.openmall.service.converter.ItemConverter;
import com.sciatta.openmall.service.converter.OrderConverter;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.ItemCommentQuery;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemServiceImpl
 */
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;
    private final ItemImageMapper itemImageMapper;
    private final ItemSpecMapper itemSpecMapper;
    private final ItemParamMapper itemParamMapper;
    private final ItemCommentMapper itemCommentMapper;
    private final OrderMapper orderMapper;
    private final OrderStatusMapper orderStatusMapper;
    
    public ItemServiceImpl(ItemParamMapper itemParamMapper,
                           ItemMapper itemMapper,
                           ItemImageMapper itemImageMapper,
                           ItemSpecMapper itemSpecMapper,
                           ItemCommentMapper itemCommentMapper, OrderMapper orderMapper, OrderStatusMapper orderStatusMapper) {
        this.itemParamMapper = itemParamMapper;
        this.itemMapper = itemMapper;
        this.itemImageMapper = itemImageMapper;
        this.itemSpecMapper = itemSpecMapper;
        this.itemCommentMapper = itemCommentMapper;
        this.orderMapper = orderMapper;
        this.orderStatusMapper = orderStatusMapper;
    }
    
    @Override
    public ItemDTO queryItemById(String id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        
        return ItemConverter.INSTANCE.toItemDTO(item);
    }
    
    @Override
    public List<ItemImageDTO> queryItemImagesByItemId(String itemId) {
        List<ItemImage> itemImageList = itemImageMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.toItemImageDTO(itemImageList);
    }
    
    @Override
    public List<ItemSpecDTO> queryItemSpecsByItemId(String itemId) {
        List<ItemSpec> itemSpecList = itemSpecMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.toItemSpecDTO(itemSpecList);
    }
    
    @Override
    public ItemParamDTO queryItemParamByItemId(String itemId) {
        ItemParam itemParam = itemParamMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.toItemParamDTO(itemParam);
    }
    
    @Override
    public List<ItemCommentDTO> queryUserItemComments(ItemCommentQuery itemCommentQuery, PagedContext pagedContext) {
        
        List<ItemComment> itemCommentList = pagedContext.startPage(
                () -> itemCommentMapper.selectItemComments(itemCommentQuery.getItemId(), itemCommentQuery.getCommentLevel()),
                false
        );
        
        return ItemConverter.INSTANCE.toItemCommentDTO(itemCommentList);
    }
    
    @Override
    public ItemCommentLevelCountDTO queryCommentLevelCounts(String itemId) {
        Integer goodCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.BAD.type);
        
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        
        return ItemConverter.INSTANCE.toItemCommentLevelCountDTO(goodCounts, normalCounts, badCounts, totalCounts);
    }
    
    @Override
    public List<ItemDTO> querySearchItems(String keywords, String sort, PagedContext pagedContext) {
        
        List<Item> itemList = pagedContext.startPage(
                () -> itemMapper.searchItemsByKeywords(keywords, sort),
                false
        );
        
        return ItemConverter.INSTANCE.toItemDTO(itemList);
    }
    
    @Override
    public List<ItemDTO> querySearchCatItems(Integer catId, String sort, PagedContext pagedContext) {
        List<Item> searchItemList = pagedContext.startPage(
                () -> itemMapper.searchItemsByCatId(catId, sort),
                false
        );
        
        return ItemConverter.INSTANCE.toItemDTO(searchItemList);
    }
    
    @Override
    public List<ItemDTO> queryShopCartItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> idList = CollectionUtils.arrayToList(ids);
        
        if (idList.size() <= 0) {
            return Collections.EMPTY_LIST;
        }
        
        List<Item> itemList = itemMapper.searchShopCartItemsBySpecIds(idList);
        
        return ItemConverter.INSTANCE.toItemDTO(itemList);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String orderId, String userId, List<ItemCommentQuery> commentList) {
        // 批量插入评论
        List<ItemComment> itemCommentList = ItemCommentConverter.INSTANCE.toItemComment(commentList, userId);
        itemCommentMapper.insertBatch(itemCommentList);
        
        // 更新为已评论
        Order order = OrderConverter.INSTANCE.toOrder(orderId, YesOrNo.YES.type);
        orderMapper.updateByPrimaryKeySelective(order);
        
        // 更新评论时间
        OrderStatus orderStatus = OrderConverter.INSTANCE.toOrderStatus(orderId, new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
    
    @Override
    public List<ItemCommentDTO> queryComments(String userId, PagedContext pagedContext) {
        List<ItemComment> itemCommentList =
                pagedContext.startPage(() -> itemCommentMapper.selectItemCommentsByUserId(userId), false);
        
        return ItemCommentConverter.INSTANCE.toItemCommentDTO(itemCommentList);
    }
}
