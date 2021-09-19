package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.pojo.query.UserItemCommentServiceQuery;
import com.sciatta.openmall.service.support.paged.PagedContext;

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
    
    List<ItemCommentDTO> queryUserItemComments(UserItemCommentServiceQuery userItemCommentServiceQuery, PagedContext pagedContext);
    
    ItemCommentLevelCountDTO queryCommentLevelCounts(String itemId);
    
    List<ItemDTO> querySearchItems(String keywords, String sort, PagedContext pagedContext);
    
    List<ItemDTO> querySearchCatItems(Integer catId, String sort, PagedContext pagedContext);
    
    List<ItemDTO> queryShopCartItemsBySpecIds(String specIds);
}
