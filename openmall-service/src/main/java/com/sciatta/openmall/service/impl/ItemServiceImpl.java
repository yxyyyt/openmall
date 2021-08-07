package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.common.enums.CommentLevel;
import com.sciatta.openmall.common.utils.PagedUtils;
import com.sciatta.openmall.dao.mapper.ext.ItemCommentMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemImageMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemParamMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemSpecMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemMapper;
import com.sciatta.openmall.dao.pojo.po.ext.SearchItem;
import com.sciatta.openmall.dao.pojo.po.ext.UserItemComment;
import com.sciatta.openmall.dao.pojo.po.mbg.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.dao.pojo.query.UserItemCommentDaoQuery;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.converter.ItemConverter;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.SearchItemsServiceQuery;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;
import org.springframework.stereotype.Service;

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
        
        return ItemConverter.INSTANCE.itemToItemDTO(item);
    }
    
    @Override
    public List<ItemImageDTO> queryItemImagesByItemId(String itemId) {
        List<ItemImage> itemImageList = itemImageMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.itemImageListToItemImageDTOList(itemImageList);
    }
    
    @Override
    public List<ItemSpecDTO> queryItemSpecsByItemId(String itemId) {
        List<ItemSpec> itemSpecList = itemSpecMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.itemSpecListToItemSpecDTOList(itemSpecList);
    }
    
    @Override
    public ItemParamDTO queryItemParamByItemId(String itemId) {
        ItemParam itemParam = itemParamMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.itemParamToItemParamDTO(itemParam);
    }
    
    @Override
    public List<UserItemCommentDTO> queryUserItemComments(UserItemCommentServiceQuery userItemCommentServiceQuery,
                                                          PagedUtils.PagedGridResult pagedGridResult) {
        
        UserItemCommentDaoQuery userItemCommentDaoQuery = ItemConverter.
                INSTANCE.userItemCommentServiceQueryToUserItemCommentDaoQuery(userItemCommentServiceQuery);
        
        // 分页
        PagedUtils.startPage(userItemCommentServiceQuery.getPage(), userItemCommentServiceQuery.getPageSize());
        
        List<UserItemComment> userItemComments = itemCommentMapper.selectItemComments(userItemCommentDaoQuery);
        
        // 初始化PagedGridResult分页数据
        PagedUtils.initPagedGridResult(userItemComments, userItemCommentServiceQuery.getPage(), pagedGridResult);
        
        return ItemConverter.INSTANCE.userItemCommentListToUserItemCommentDTOList(userItemComments);
    }
    
    @Override
    public CommentLevelCountsDTO queryCommentLevelCounts(String itemId) {
        Integer goodCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.BAD.type);
        
        Integer totalCounts = goodCounts + normalCounts + badCounts;
        
        return ItemConverter.INSTANCE.toCommentLevelCountsDTO(goodCounts, normalCounts, badCounts, totalCounts);
    }
    
    @Override
    public List<SearchItemDTO> querySearchItems(SearchItemsServiceQuery searchItemsServiceQuery, PagedUtils.PagedGridResult pagedGridResult) {
        // 分页
        PagedUtils.startPage(searchItemsServiceQuery.getPage(), searchItemsServiceQuery.getPageSize());
        
        List<SearchItem> searchItems = itemMapper.searchItems(searchItemsServiceQuery.getKeywords(), searchItemsServiceQuery.getSort());
        
        // 初始化PagedGridResult分页数据
        PagedUtils.initPagedGridResult(searchItems, searchItemsServiceQuery.getPage(), pagedGridResult);
        
        return ItemConverter.INSTANCE.searchItemListToSearchItemDTOList(searchItems);
    }
    
}
