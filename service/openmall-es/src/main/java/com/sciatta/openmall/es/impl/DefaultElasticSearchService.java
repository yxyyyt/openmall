package com.sciatta.openmall.es.impl;

import com.sciatta.openmall.common.constants.PagedConstants;
import com.sciatta.openmall.es.ElasticSearchService;
import com.sciatta.openmall.es.ResultSetExtractor;
import com.sciatta.openmall.es.SearchCriteria;
import com.sciatta.openmall.pojo.PagedGridResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rain on 2022/4/16<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * DefaultElasticSearchService
 */
@Service
public class DefaultElasticSearchService implements ElasticSearchService {

    private final ElasticsearchRestTemplate esTemplate;

    public DefaultElasticSearchService(ElasticsearchRestTemplate esTemplate) {
        this.esTemplate = esTemplate;
    }

    @Override
    public <T> PagedGridResult<T> search(String queryName, String queryValue, Class<T> sourceClass, ResultSetExtractor<T> extractor) {
        return search(queryName, queryValue, PagedConstants.PAGE_START, PagedConstants.PAGE_SIZE, sourceClass, extractor);
    }

    @Override
    public <T> PagedGridResult<T> search(String queryName, String queryValue,
                                         Integer pageNumber, Integer pageSize,
                                         Class<T> sourceClass, ResultSetExtractor<T> extractor) {
        SearchCriteria criteria = new SearchCriteria.Builder()
                .where(queryName, queryValue)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        return search(criteria, sourceClass, extractor);
    }

    @Override
    public <T> PagedGridResult<T> search(String queryName, String queryValue,
                                         String highlight, SearchCriteria.Sorter sort,
                                         Integer pageNumber, Integer pageSize,
                                         Class<T> sourceClass, ResultSetExtractor<T> extractor) {

        SearchCriteria criteria = new SearchCriteria.Builder()
                .where(queryName, queryValue)
                .highlight(highlight)
                .sort(sort)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        return search(criteria, sourceClass, extractor);
    }

    @Override
    public <T> PagedGridResult<T> search(SearchCriteria criteria, Class<T> sourceClass, ResultSetExtractor<T> extractor) {
        NativeSearchQuery searchQuery = buildSearchQuery(criteria);

        return queryForPage(searchQuery, sourceClass, extractor);
    }

    // private

    private NativeSearchQuery buildSearchQuery(SearchCriteria criteria) {

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        withQuery(builder, criteria);
        withHighlightFields(builder, criteria);
        withSort(builder, criteria);
        withPageable(builder, criteria);

        return builder.build();
    }

    private <T> PagedGridResult<T> queryForPage(NativeSearchQuery query, Class<T> sourceClass, ResultSetExtractor<T> extractor) {

        SearchHits<T> searchHits = esTemplate.search(query, sourceClass);
        List<T> rows = searchHits.stream().map(extractor::extractor).collect(Collectors.toList());

        return new PagedGridResult<T>()
                .setPageNumber(query.getPageable().getPageNumber() + 1) // 首页从0开始
                .setRows(rows)
                .setPages((int) Math.ceil((double) searchHits.getTotalHits() / query.getPageable().getPageSize()))
                .setTotal(searchHits.getTotalHits());
    }

    private void withQuery(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        builder.withQuery(QueryBuilders.matchQuery(criteria.getWhere().getFieldName(), criteria.getWhere().getFieldValue()));
    }

    private void withHighlightFields(NativeSearchQueryBuilder builder, SearchCriteria criteria) {

        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[criteria.getHighlight().size()];

        for (int i = 0; i < criteria.getHighlight().size(); i++) {
            fields[i] = new HighlightBuilder.Field(criteria.getHighlight().get(i));
        }

        builder.withHighlightFields(fields);
    }

    private void withSort(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        criteria.getSort().forEach((k, v) -> builder.withSort(new FieldSortBuilder(k).order(SortOrder.fromString(v.name()))));
    }

    private void withPageable(NativeSearchQueryBuilder builder, SearchCriteria criteria) {
        // 首页从0开始
        builder.withPageable(PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize()));
    }
}
