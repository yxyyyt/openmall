package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.CommentLevel;
import com.sciatta.openmall.dao.mapper.ext.*;
import com.sciatta.openmall.dao.pojo.po.ext.ItemComment;
import com.sciatta.openmall.dao.pojo.po.ext.ShopCartItem;
import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.converter.ItemConverter;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
    
    public ItemServiceImpl(ItemParamMapper itemParamMapper,
                           ItemMapper itemMapper,
                           ItemImageMapper itemImageMapper,
                           ItemSpecMapper itemSpecMapper,
                           ItemCommentMapper itemCommentMapper) {
        this.itemParamMapper = itemParamMapper;
        this.itemMapper = itemMapper;
        this.itemImageMapper = itemImageMapper;
        this.itemSpecMapper = itemSpecMapper;
        this.itemCommentMapper = itemCommentMapper;
    }
    
    @Override
    public ItemDTO queryItemById(String id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        
        return ItemConverter.INSTANCE.convert(item);
    }
    
    @Override
    public List<ItemImageDTO> queryItemImagesByItemId(String itemId) {
        List<ItemImage> itemImageList = itemImageMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.convertToItemImageDTO(itemImageList);
    }
    
    @Override
    public List<ItemSpecDTO> queryItemSpecsByItemId(String itemId) {
        List<ItemSpec> itemSpecList = itemSpecMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.convertToItemSpecDTO(itemSpecList);
    }
    
    @Override
    public ItemParamDTO queryItemParamByItemId(String itemId) {
        ItemParam itemParam = itemParamMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.convert(itemParam);
    }
    
    @Override
    public List<ItemCommentDTO> queryUserItemComments(UserItemCommentServiceQuery userItemCommentServiceQuery, PagedContext pagedContext) {
        
        List<ItemComment> itemCommentList = pagedContext.startPage(
                () -> itemCommentMapper.selectItemComments(userItemCommentServiceQuery.getItemId(), userItemCommentServiceQuery.getLevel()),
                false
        );
        
        return ItemConverter.INSTANCE.convertToItemCommentDTO(itemCommentList);
    }
    
    @Override
    public ItemCommentLevelCountDTO queryCommentLevelCounts(String itemId) {
        Integer goodCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.BAD.type);
        
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        
        return ItemConverter.INSTANCE.convert(goodCounts, normalCounts, badCounts, totalCounts);
    }
    
    @Override
    public List<ItemDTO> querySearchItems(String keywords, String sort, PagedContext pagedContext) {
        
        List<Item> itemList = pagedContext.startPage(
                () -> itemMapper.searchItemsByKeywords(keywords, sort),
                false
        );
        
        return ItemConverter.INSTANCE.convertToItemDTO(itemList);
    }
    
    @Override
    public List<ItemDTO> querySearchCatItems(Integer catId, String sort, PagedContext pagedContext) {
        List<Item> searchItemList = pagedContext.startPage(
                () -> itemMapper.searchItemsByCatId(catId, sort),
                false
        );
        
        return ItemConverter.INSTANCE.convertToItemDTO(searchItemList);
    }
    
    @Override
    public List<ShopCartItemDTO> queryShopCartItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> idList = CollectionUtils.arrayToList(ids);
        
        if (idList.size() <= 0) {
            return Collections.EMPTY_LIST;
        }
        
        List<ShopCartItem> shopCartItemList = itemMapper.searchShopCartItemsBySpecIds(idList);
        
        return ItemConverter.INSTANCE.shopCartItemListToShopCartItemDTOList(shopCartItemList);
    }
}
