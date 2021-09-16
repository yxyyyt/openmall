package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.vo.ItemCommentLevelCountVO;
import com.sciatta.openmall.api.pojo.vo.ItemCommentUserVO;
import com.sciatta.openmall.api.pojo.vo.ItemSearchVO;
import com.sciatta.openmall.api.pojo.vo.ItemWrapVO;
import com.sciatta.openmall.common.JSONResult;
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
        
        ItemWrapVO itemWrapVO = ItemConverter.INSTANCE.convert(itemDTO, itemImageDTOList, itemSpecDTOList, itemParamDTO);
        
        return JSONResult.success(itemWrapVO);
    }
    
    @GetMapping("commentLevelCounts")
    public JSONResult commentLevelCounts(@RequestParam @NotBlank(message = "商品标识不能为空") String itemId) {
        
        ItemCommentLevelCountDTO itemCommentLevelCountDTO = itemService.queryCommentLevelCounts(itemId);
        
        ItemCommentLevelCountVO itemCommentLevelCountVO = ItemConverter.INSTANCE.convert(itemCommentLevelCountDTO);
        
        return JSONResult.success(itemCommentLevelCountVO);
    }
    
    @GetMapping("comments")
    public JSONResult comments(
            @RequestParam @NotBlank(message = "商品标识不能为空") String itemId,
            @RequestParam @NotNull(message = "评论级别不能为空") Integer level,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ItemCommentDTO> itemCommentDTOList = itemService.queryUserItemComments(
                ItemConverter.INSTANCE.toUserItemCommentServiceQuery(itemId, level),
                pagedContext
        );
        
        // 转换，nickname脱敏
        List<ItemCommentUserVO> itemCommentUserVOList = ItemConverter.INSTANCE.convertToItemCommentUserVO(itemCommentDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemCommentUserVOList));
    }
    
    @GetMapping("search")
    public JSONResult search(
            @RequestParam @NotBlank(message = "商品关键字不能为空") String keywords,
            @RequestParam @NotBlank(message = "商品排序不能为空") String sort,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ItemDTO> itemDTOList = itemService.querySearchItems(keywords, sort, pagedContext);
        
        List<ItemSearchVO> itemSearchVOList = ItemConverter.INSTANCE.convertToItemSearchVO(itemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemSearchVOList));
    }
    
    @GetMapping("catItems")
    public JSONResult catItems(
            @RequestParam @NotNull(message = "分类标识不能为空") Integer catId,
            @RequestParam @NotBlank(message = "商品排序不能为空") String sort,
            @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        PagedContext pagedContext = new PagedContext.Builder()
                .setPageNumber(page)
                .setPageSize(pageSize)
                .build();
        
        List<ItemDTO> itemDTOList = itemService.querySearchCatItems(catId, sort, pagedContext);
        
        List<ItemSearchVO> itemSearchVOList = ItemConverter.INSTANCE.convertToItemSearchVO(itemDTOList);
        
        return JSONResult.success(pagedContext.getPagedGridResult(itemSearchVOList));
    }
}
