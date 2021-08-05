package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.vo.ItemInfoVO;
import com.sciatta.openmall.api.pojo.vo.UserItemCommentVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.utils.PagedUtils;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.pojo.dto.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sciatta.openmall.common.constants.PagedConstants.PAGE_SIZE;
import static com.sciatta.openmall.common.constants.PagedConstants.PAGE_START;

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
    
    @GetMapping("/comments")
    public JSONResult comments(@RequestParam String itemId, @RequestParam Integer level,
                               @RequestParam Integer page, @RequestParam Integer pageSize) {
        
        if (!StringUtils.hasText(itemId)) {
            return JSONResult.errorUsingMessage("商品不存在");
        }
        
        if (page == null) {
            page = PAGE_START;
        }
        
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        
        // 创建pagedGridResult
        PagedUtils.PagedGridResult pagedGridResult = PagedUtils.createPagedGridResult();
        
        List<UserItemCommentDTO> userItemCommentDTOList = itemService.queryUserItemComment(
                ItemConverter.INSTANCE.toUserItemCommentServiceQuery(itemId, level, page, pageSize),
                pagedGridResult
        );
        
        // 转换，nickname脱敏
        List<UserItemCommentVO> userItemCommentVOList = ItemConverter.INSTANCE.userItemCommentDTOListToUserItemCommentVOList(userItemCommentDTOList);
        
        // 设置当前页记录
        PagedUtils.setRows(pagedGridResult, userItemCommentVOList);
        
        return JSONResult.success(pagedGridResult);
    }
}
