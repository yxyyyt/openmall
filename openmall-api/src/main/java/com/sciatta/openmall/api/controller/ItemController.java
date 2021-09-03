package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.vo.CommentLevelCountsVO;
import com.sciatta.openmall.api.pojo.vo.ItemInfoVO;
import com.sciatta.openmall.api.pojo.vo.SearchItemVO;
import com.sciatta.openmall.api.pojo.vo.UserItemCommentVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品详情
 */
@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping("info/{itemId}")
    public JSONResult info(@PathVariable String itemId) {
        
        if (!StringUtils.hasText(itemId)) {
            return JSONResult.errorUsingMessage("商品不存在");
        }
        
        ItemDTO itemDTO = itemService.queryItemById(itemId);
        List<ItemImageDTO> itemImageDTOList = itemService.queryItemImagesByItemId(itemId);
        List<ItemSpecDTO> itemSpecDTOList = itemService.queryItemSpecsByItemId(itemId);
        ItemParamDTO itemParamDTO = itemService.queryItemParamByItemId(itemId);
        
        ItemInfoVO itemInfoVO = ItemConverter.INSTANCE.toItemInfoVO(
                itemDTO,
                itemImageDTOList,
                itemSpecDTOList,
                itemParamDTO);
        
        
        return JSONResult.success(itemInfoVO);
    }
    
    @GetMapping("commentLevelCounts")
    public JSONResult commentLevelCounts(@RequestParam String itemId) {
        if (!StringUtils.hasText(itemId)) {
            return JSONResult.errorUsingMessage("商品不存在");
        }
        
        CommentLevelCountsDTO commentLevelCountsDTO = itemService.queryCommentLevelCounts(itemId);
        
        CommentLevelCountsVO commentLevelCountsVO = ItemConverter.INSTANCE.commentLevelCountsDTOToCommentLevelCountsVO(commentLevelCountsDTO);
        
        return JSONResult.success(commentLevelCountsVO);
    }
    
    @GetMapping("comments")
    public JSONResult comments(@RequestParam String itemId, @RequestParam Integer level,
                               @RequestParam Integer page, @RequestParam Integer pageSize) {
        
        if (!StringUtils.hasText(itemId)) {
            return JSONResult.errorUsingMessage("商品不存在");
        }
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<UserItemCommentDTO> userItemCommentDTOList = itemService.queryUserItemComments(
                ItemConverter.INSTANCE.toUserItemCommentServiceQuery(itemId, level),
                pagedContext
        );
        
        // 转换，nickname脱敏
        List<UserItemCommentVO> userItemCommentVOList = ItemConverter.INSTANCE.userItemCommentDTOListToUserItemCommentVOList(userItemCommentDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(userItemCommentVOList));
    }
    
    @GetMapping("search")
    public JSONResult search(@RequestParam String keywords, @RequestParam String sort,
                             @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (!StringUtils.hasText(keywords)) {
            return JSONResult.errorUsingMessage("商品不存在");
        }
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<SearchItemDTO> searchItemDTOList = itemService.querySearchItems(
                ItemConverter.INSTANCE.toSearchItemsServiceQuery(keywords, sort),
                pagedContext
        );
        
        List<SearchItemVO> searchItemVOList = ItemConverter.INSTANCE.searchItemDTOListToSearchItemVOList(searchItemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(searchItemVOList));
    }
    
    @GetMapping("catItems")
    public JSONResult catItems(@RequestParam Integer catId, @RequestParam String sort,
                               @RequestParam Integer page, @RequestParam Integer pageSize) {
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<SearchItemDTO> searchItemDTOList = itemService.querySearchCatItems(
                ItemConverter.INSTANCE.toSearchCatItemsServiceQuery(catId, sort),
                pagedContext
        );
        
        List<SearchItemVO> searchItemVOList = ItemConverter.INSTANCE.searchItemDTOListToSearchItemVOList(searchItemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(searchItemVOList));
    }
}
