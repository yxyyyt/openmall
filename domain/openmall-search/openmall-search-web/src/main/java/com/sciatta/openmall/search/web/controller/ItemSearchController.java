package com.sciatta.openmall.search.web.controller;

import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.search.api.ItemSearchService;
import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import com.sciatta.openmall.search.web.converter.ItemSearchConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Created by Rain on 2022/4/14<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * 主搜
 */
@Validated
@RestController
@RequestMapping("items")
public class ItemSearchController {
    private final ItemSearchService itemSearchService;

    public ItemSearchController(ItemSearchService itemSearchService) {
        this.itemSearchService = itemSearchService;
    }

    @GetMapping("search")
    public JSONResult searchItemsByKeywords(
            @RequestParam @NotBlank(message = "商品关键字不能为空") String keywords,
            @RequestParam @NotBlank(message = "商品排序不能为空") String sort,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {

        PagedGridResult pagedGridResult = itemSearchService.search(keywords, sort, page, pageSize);

        pagedGridResult.setRows(
                ItemSearchConverter.INSTANCE.toItemSearchVO((List<ItemSearchDTO>) pagedGridResult.getRows())
        );

        return JSONResult.success(pagedGridResult);
    }
}
