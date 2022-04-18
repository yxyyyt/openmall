package com.sciatta.openmall.search.service.impl;

import com.sciatta.openmall.common.constants.SearchConstants;
import com.sciatta.openmall.es.ElasticSearchService;
import com.sciatta.openmall.es.SearchCriteria;
import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.search.api.ItemSearchService;
import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import com.sciatta.openmall.search.service.converter.ItemSearchConverter;
import com.sciatta.openmall.search.service.pojo.po.ItemSearch;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Rain on 2022/4/16<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ItemSearchServiceImpl
 */
@RestController
public class ItemSearchServiceImpl implements ItemSearchService {
    private final ElasticSearchService elasticSearchService;

    public ItemSearchServiceImpl(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @Override
    public PagedGridResult<ItemSearchDTO> search(String keywords, String sort, Integer pageNumber, Integer pageSize) {
        PagedGridResult<ItemSearch> pagedGridResult = elasticSearchService.search(
                SearchConstants.ITEM_NAME, keywords,
                SearchConstants.ITEM_NAME,
                getSorter(sort),
                pageNumber, pageSize,
                ItemSearch.class,
                searchHit -> {
                    ItemSearch itemSearch = searchHit.getContent();
                    // 高亮替换
                    itemSearch.setItemName(searchHit.getHighlightFields().get(SearchConstants.ITEM_NAME).get(0));
                    return itemSearch;
                });


        return new PagedGridResult<ItemSearchDTO>()
                .setPageNumber(pagedGridResult.getPageNumber())
                .setRows(ItemSearchConverter.INSTANCE.toItemSearchDTO(pagedGridResult.getRows()))
                .setPages(pagedGridResult.getPages())
                .setTotal(pagedGridResult.getTotal());
    }

    // private
    private SearchCriteria.Sorter getSorter(String sort) {
        if ("c".equals(sort)) {
            return new SearchCriteria.Sorter(SearchConstants.ITEM_SELL_COUNTS, SearchCriteria.SortOrder.DESC);
        } else if ("p".equals(sort)) {
            return new SearchCriteria.Sorter(SearchConstants.ITEM_PRICE_DISCOUNT, SearchCriteria.SortOrder.ASC);
        } else {
            return new SearchCriteria.Sorter(SearchConstants.ITEM_NAME, SearchCriteria.SortOrder.ASC);
        }
    }
}
