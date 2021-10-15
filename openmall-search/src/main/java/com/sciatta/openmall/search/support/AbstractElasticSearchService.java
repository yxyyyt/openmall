package com.sciatta.openmall.search.support;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by yangxiaoyu on 2021/10/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * AbstractElasticSearchService
 */
public abstract class AbstractElasticSearchService<T> {
    
    protected final ElasticsearchRestTemplate esTemplate;
    
    public AbstractElasticSearchService(ElasticsearchRestTemplate esTemplate) {
        this.esTemplate = esTemplate;
    }
    
    // =================================================================================================================
    // protected
    protected NativeSearchQuery buildSearchQuery(SearchCriteria criteria) {
        
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        
        withQuery(builder, criteria);
        withHighlightFields(builder, criteria);
        withSort(builder, criteria);
        withPageable(builder, criteria);
        
        return builder.build();
    }
    
    protected SearchResult<T> queryForPage(NativeSearchQuery query, Class<T> sourceClass, ResultSetExtractor<T> extractor) {
        SearchResult<T> result = new SearchResult<>();
        
        SearchHits<T> searchHits = esTemplate.search(query, sourceClass);
        List<T> rows = searchHits.stream().map(extractor::extractor).collect(Collectors.toList());
        
        result.setPageNumber(query.getPageable().getPageNumber() + 1);// 首页从0开始
        result.setTotalPages((int) Math.ceil((double) searchHits.getTotalHits() / query.getPageable().getPageSize()));
        result.setTotalRecords(searchHits.getTotalHits());
        result.setRows(rows);
        
        return result;
    }
    
    // =================================================================================================================
    // private
    private void withQuery(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        criteria.getWhere().forEach((k, v) -> {
            builder.withQuery(QueryBuilders.matchQuery(k, v));
        });
    }
    
    private void withHighlightFields(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        criteria.getHighlight().forEach(name -> {
            builder.withHighlightFields(new HighlightBuilder.Field(name));
        });
    }
    
    private void withSort(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        criteria.getSort().forEach((k, v) -> {
            builder.withSort(new FieldSortBuilder(k).order(SortOrder.fromString(v.name())));
        });
    }
    
    private void withPageable(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        // 首页从0开始
        builder.withPageable(PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize()));
    }
}
