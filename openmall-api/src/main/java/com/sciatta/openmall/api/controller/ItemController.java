package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.ItemConverter;
import com.sciatta.openmall.api.pojo.vo.ItemInfoVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import com.sciatta.openmall.service.pojo.dto.ItemImageDTO;
import com.sciatta.openmall.service.pojo.dto.ItemParamDTO;
import com.sciatta.openmall.service.pojo.dto.ItemSpecDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品详情
 */
@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    
    @GetMapping("info/{itemId}")
    public JSONResult info(@PathVariable String itemId) {
        
        if (!StringUtils.hasText(itemId)) {
            return JSONResult.errorUsingMessage("商品不存在");
        }
        
        ItemDTO itemDTO = itemService.queryItemById(itemId);
        List<ItemImageDTO> itemImageDTOS = itemService.queryItemImagesByItemId(itemId);
        List<ItemSpecDTO> itemSpecDTOS = itemService.queryItemSpecsByItemId(itemId);
        ItemParamDTO itemParamDTO = itemService.queryItemParamByItemId(itemId);
        
        ItemInfoVO itemInfoVO = ItemConverter.INSTANCE.toItemInfoVO(
                itemDTO,
                itemImageDTOS,
                itemSpecDTOS,
                itemParamDTO);
        
        
        return JSONResult.success(itemInfoVO);
    }
}
