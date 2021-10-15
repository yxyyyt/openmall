package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.constants.SearchConstants;
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
import com.sciatta.openmall.search.ItemSearchService;
import com.sciatta.openmall.search.support.SearchCriteria;
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
    private final ItemSearchService itemSearchService;
    
    public ItemServiceImpl(ItemParamMapper itemParamMapper,
                           ItemMapper itemMapper,
                           ItemImageMapper itemImageMapper,
                           ItemSpecMapper itemSpecMapper,
                           ItemCommentMapper itemCommentMapper,
                           OrderMapper orderMapper,
                           OrderStatusMapper orderStatusMapper,
                           ItemSearchService itemSearchService) {
        this.itemParamMapper = itemParamMapper;
        this.itemMapper = itemMapper;
        this.itemImageMapper = itemImageMapper;
        this.itemSpecMapper = itemSpecMapper;
        this.itemCommentMapper = itemCommentMapper;
        this.orderMapper = orderMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.itemSearchService = itemSearchService;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ItemDTO queryItemById(String id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        
        return ItemConverter.INSTANCE.toItemDTO(item);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ItemImageDTO> queryItemImagesByItemId(String itemId) {
        List<ItemImage> itemImageList = itemImageMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.toItemImageDTO(itemImageList);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ItemSpecDTO> queryItemSpecsByItemId(String itemId) {
        List<ItemSpec> itemSpecList = itemSpecMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.toItemSpecDTO(itemSpecList);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ItemParamDTO queryItemParamByItemId(String itemId) {
        ItemParam itemParam = itemParamMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.toItemParamDTO(itemParam);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ItemCommentDTO> queryUserItemComments(ItemCommentQuery itemCommentQuery, PagedContext<ItemComment> pagedContext) {
        
        List<ItemComment> itemCommentList = pagedContext.startPage(
                () -> itemCommentMapper.selectItemComments(itemCommentQuery.getItemId(), itemCommentQuery.getCommentLevel()),
                false
        );
        
        return ItemConverter.INSTANCE.toItemCommentDTO(itemCommentList);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ItemCommentLevelCountDTO queryCommentLevelCounts(String itemId) {
        Integer goodCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.BAD.type);
        
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        
        return ItemConverter.INSTANCE.toItemCommentLevelCountDTO(goodCounts, normalCounts, badCounts, totalCounts);
    }
    
    @Override
    public List<ItemDTO> querySearchItems(String keywords, String sort,
                                          PagedContext<com.sciatta.openmall.search.pojo.po.Item> pagedContext) {
        
        SearchCriteria criteria = new SearchCriteria.Builder()
                .pageNumber(pagedContext.getPageNumber())
                .pageSize(pagedContext.getPageSize())
                .where(SearchConstants.ITEM_NAME, keywords)
                .highlight(SearchConstants.ITEM_NAME)
                .sort(getSorter(sort))
                .build();
        
        List<com.sciatta.openmall.search.pojo.po.Item> itemList =
                pagedContext.startPage(() -> itemSearchService.search(criteria), false);
        
        return ItemConverter.INSTANCE.searchToItemDTO(itemList);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ItemDTO> querySearchCatItems(Integer catId, String sort, PagedContext<Item> pagedContext) {
        List<Item> searchItemList = pagedContext.startPage(
                () -> itemMapper.searchItemsByCatId(catId, sort),
                false
        );
        
        return ItemConverter.INSTANCE.toItemDTO(searchItemList);
    }
    
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
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
    @Transactional(readOnly = true)
    public List<ItemCommentDTO> queryComments(String userId, PagedContext<ItemComment> pagedContext) {
        List<ItemComment> itemCommentList =
                pagedContext.startPage(() -> itemCommentMapper.selectItemCommentsByUserId(userId), false);
        
        return ItemCommentConverter.INSTANCE.toItemCommentDTO(itemCommentList);
    }
    
    // =================================================================================================================
    
    // private
    private SearchCriteria.Sorter getSorter(String sort) {
        if ("c".equals(sort)) {
            return new SearchCriteria.Sorter(SearchConstants.ITEM_SELL_COUNTS, SearchCriteria.SortOrder.DESC);
        } else if ("p".equals(sort)) {
            return new SearchCriteria.Sorter(SearchConstants.ITEM_PRICE_DISCOUNT, SearchCriteria.SortOrder.ASC);
        } else {
            return new SearchCriteria.Sorter(SearchConstants.ITEM_NAME, SearchCriteria.SortOrder.ASC);
        }
    }
}
