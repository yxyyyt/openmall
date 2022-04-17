package com.sciatta.openmall.search.service.impl;

import com.sciatta.openmall.item.pojo.dto.ItemDTO;
import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.search.api.ItemSearchService;
import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import com.sciatta.openmall.search.service.client.ItemServiceFeignClient;
import com.sciatta.openmall.search.service.converter.ItemSearchConverter;
import org.springframework.web.bind.annotation.RestController;

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
    public PagedGridResult<ItemSearchDTO> search(String keywords, String sort, Integer pageNumber, Integer pageSize) {
        PagedGridResult<ItemDTO> pagedGridResult = itemService.searchItemsByKeywords(keywords, sort, pageNumber, pageSize);

        return new PagedGridResult<ItemSearchDTO>()
                .setPageNumber(pagedGridResult.getPageNumber())
                .setRows(ItemSearchConverter.INSTANCE.toItemSearchDTO(pagedGridResult.getRows()))
                .setPages(pagedGridResult.getPages())
                .setTotal(pagedGridResult.getTotal());
    }
}
