package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.vo.ItemCommentLevelCountVO;
import com.sciatta.openmall.api.pojo.vo.ItemCommentUserVO;
import com.sciatta.openmall.api.pojo.vo.ItemSearchVO;
import com.sciatta.openmall.api.pojo.vo.ItemWrapVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.dao.pojo.po.ext.Item;
import com.sciatta.openmall.dao.pojo.po.ext.ItemComment;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.pojo.dto.*;
import com.sciatta.openmall.service.support.paged.PagedContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品详情
 */
@Validated
@RestController
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    
    @GetMapping("info/{itemId}")
    public JSONResult info(@PathVariable @NotBlank(message = "商品标识不能为空") String itemId) {
        
        ItemDTO itemDTO = itemService.queryItemById(itemId);
        List<ItemImageDTO> itemImageDTOList = itemService.queryItemImagesByItemId(itemId);
        List<ItemSpecDTO> itemSpecDTOList = itemService.queryItemSpecsByItemId(itemId);
        ItemParamDTO itemParamDTO = itemService.queryItemParamByItemId(itemId);
        
        ItemWrapVO itemWrapVO = ItemConverter.INSTANCE.toItemWrapVO(itemDTO, itemImageDTOList, itemSpecDTOList, itemParamDTO);
        
        return JSONResult.success(itemWrapVO);
    }
    
    @GetMapping("commentLevelCounts")
    public JSONResult commentLevelCounts(@RequestParam @NotBlank(message = "商品标识不能为空") String itemId) {
        
        ItemCommentLevelCountDTO itemCommentLevelCountDTO = itemService.queryCommentLevelCounts(itemId);
        
        ItemCommentLevelCountVO itemCommentLevelCountVO = ItemConverter.INSTANCE.toItemCommentLevelCountVO(itemCommentLevelCountDTO);
        
        return JSONResult.success(itemCommentLevelCountVO);
    }
    
    @GetMapping("comments")
    public JSONResult comments(
            @RequestParam @NotBlank(message = "商品标识不能为空") String itemId,
            @RequestParam Integer level,    // 评论级别可以为空，当为空时，则查询全部
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        
        PagedContext<ItemComment> pagedContext = new PagedContext.Builder<ItemComment>()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ItemCommentDTO> itemCommentDTOList = itemService.queryUserItemComments(
                ItemConverter.INSTANCE.toUserItemCommentServiceQuery(itemId, level),
                pagedContext
        );
        
        // 转换，nickname脱敏
        List<ItemCommentUserVO> itemCommentUserVOList = ItemConverter.INSTANCE.toItemCommentUserVO(itemCommentDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemCommentUserVOList));
    }
    
    @GetMapping("search")
    public JSONResult search(
            @RequestParam @NotBlank(message = "商品关键字不能为空") String keywords,
            @RequestParam @NotBlank(message = "商品排序不能为空") String sort,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        
        // 查询ES
        PagedContext<com.sciatta.openmall.search.pojo.po.Item> pagedContext =
                new PagedContext.Builder<com.sciatta.openmall.search.pojo.po.Item>()
                        .setPageNumber(page)
                        .setPageSize(pageSize)
                        .setEnv(PagedContext.Env.ES)
                        .build();
        
        List<ItemDTO> itemDTOList = itemService.querySearchItems(keywords, sort, pagedContext);
        
        List<ItemSearchVO> itemSearchVOList = ItemConverter.INSTANCE.toItemSearchVO(itemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemSearchVOList));
    }
    
    @GetMapping("catItems")
    public JSONResult catItems(
            @RequestParam @NotNull(message = "分类标识不能为空") Integer catId,
            @RequestParam @NotBlank(message = "商品排序不能为空") String sort,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        PagedContext<Item> pagedContext = new PagedContext.Builder<Item>()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ItemDTO> itemDTOList = itemService.querySearchCatItems(catId, sort, pagedContext);
        
        List<ItemSearchVO> itemSearchVOList = ItemConverter.INSTANCE.toItemSearchVO(itemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemSearchVOList));
    }
}
