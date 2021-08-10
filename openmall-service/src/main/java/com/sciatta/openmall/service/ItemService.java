package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.SearchCatItemsServiceQuery;
import com.sciatta.openmall.service.pojo.query.SearchItemsServiceQuery;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;
import com.sciatta.openmall.service.support.PagedContext;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品服务
 */
public interface ItemService {
    ItemDTO queryItemById(String id);
    
    List<ItemImageDTO> queryItemImagesByItemId(String itemId);
    
    List<ItemSpecDTO> queryItemSpecsByItemId(String itemId);
    
    ItemParamDTO queryItemParamByItemId(String itemId);
    
    List<UserItemCommentDTO> queryUserItemComments(UserItemCommentServiceQuery userItemCommentServiceQuery, PagedContext pagedContext);
    
    CommentLevelCountsDTO queryCommentLevelCounts(String itemId);
    
    List<SearchItemDTO> querySearchItems(SearchItemsServiceQuery searchItemsServiceQuery, PagedContext pagedContext);
    
    List<SearchItemDTO> querySearchCatItems(SearchCatItemsServiceQuery searchCatItemsServiceQuery, PagedContext pagedContext);
    
    List<ShopCartItemDTO> queryShopCartItemsBySpecIds(String specIds);
}
