package com.sciatta.openmall.search.impl;

import com.sciatta.openmall.search.ItemSearchService;
import com.sciatta.openmall.search.converter.ItemConverter;
import com.sciatta.openmall.search.pojo.po.Item;
import com.sciatta.openmall.search.support.AbstractElasticSearchService;
import com.sciatta.openmall.search.support.SearchCriteria;
import com.sciatta.openmall.search.support.SearchResult;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

/**
 * Created by yangxiaoyu on 2021/10/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemSearchServiceImpl
 */
@Service
public class ItemSearchServiceImpl extends AbstractElasticSearchService<Item> implements ItemSearchService {
    public ItemSearchServiceImpl(ElasticsearchRestTemplate esTemplate) {
        super(esTemplate);
    }
    
    @Override
    public SearchResult<Item> search(SearchCriteria criteria) {
        NativeSearchQuery searchQuery = buildSearchQuery(criteria);
        
        return queryForPage(searchQuery, Item.class, searchHit -> ItemConverter.INSTANCE.toItem(searchHit));
    }
}
