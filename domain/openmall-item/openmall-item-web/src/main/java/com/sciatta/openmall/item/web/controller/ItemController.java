package com.sciatta.openmall.item.web.controller;

import com.sciatta.openmall.item.api.ItemService;
import com.sciatta.openmall.item.pojo.dto.*;
import com.sciatta.openmall.item.pojo.vo.ItemCommentLevelCountVO;
import com.sciatta.openmall.item.pojo.vo.ItemWrapVO;
import com.sciatta.openmall.item.web.converter.ItemConverter;
import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.pojo.PagedGridResult;
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

        ItemWrapVO itemWrapVO = ItemConverter.INSTANCE.toItemWrapVO(
                itemDTO, itemImageDTOList, itemSpecDTOList, itemParamDTO);

        return JSONResult.success(itemWrapVO);
    }

    @GetMapping("commentLevelCounts")
    public JSONResult commentLevelCounts(@RequestParam @NotBlank(message = "商品标识不能为空") String itemId) {

        ItemCommentLevelCountDTO itemCommentLevelCountDTO = itemService.queryCommentLevelCounts(itemId);

        ItemCommentLevelCountVO itemCommentLevelCountVO =
                ItemConverter.INSTANCE.toItemCommentLevelCountVO(itemCommentLevelCountDTO);

        return JSONResult.success(itemCommentLevelCountVO);
    }

    @GetMapping("comments")
    public JSONResult comments(
            @RequestParam @NotBlank(message = "商品标识不能为空") String itemId,
            @RequestParam Integer level,    // 评论级别可以为空，当为空时，则查询全部
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {

        PagedGridResult pagedGridResult = itemService.queryItemComments(
                ItemConverter.INSTANCE.toItemCommentQuery(itemId, level),
                page,
                pageSize);

        // 转换，nickname脱敏
        pagedGridResult.setRows(
                ItemConverter.INSTANCE.toItemCommentUserVO((List<ItemCommentDTO>) pagedGridResult.getRows()));

        return JSONResult.success(pagedGridResult);
    }

    @GetMapping("catItems")
    public JSONResult searchItemsByCatId(
            @RequestParam @NotNull(message = "分类标识不能为空") Integer catId,
            @RequestParam @NotBlank(message = "商品排序不能为空") String sort,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {

        PagedGridResult pagedGridResult = itemService.searchItemsByCatId(catId, sort, page, pageSize);

        pagedGridResult.setRows(ItemConverter.INSTANCE.toItemSearchVO((List<ItemDTO>) pagedGridResult.getRows()));

        return JSONResult.success(pagedGridResult);
    }
}
