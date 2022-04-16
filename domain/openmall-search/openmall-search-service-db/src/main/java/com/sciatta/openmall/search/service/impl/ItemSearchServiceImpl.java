package com.sciatta.openmall.search.service.impl;

import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.search.api.ItemSearchService;
import com.sciatta.openmall.search.service.client.ItemServiceFeignClient;
import com.sciatta.openmall.search.service.converter.ItemSearchConverter;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rain on 2022/4/15<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ItemSearchServiceImpl
 */
@RestController
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ItemServiceFeignClient itemService;

    public ItemSearchServiceImpl(ItemServiceFeignClient itemService) {
        this.itemService = itemService;
    }

    @Override
    public PagedGridResult search(String keywords, String sort, Integer pageNumber, Integer pageSize) {
        PagedGridResult pagedGridResult = itemService.searchItemsByKeywords(keywords, sort, pageNumber, pageSize);

        pagedGridResult.setRows(
                ItemSearchConverter.INSTANCE.toItemSearchDTO((
                        List<HashMap<String, ?>>) pagedGridResult.getRows())
        );

        return pagedGridResult;
    }
}
