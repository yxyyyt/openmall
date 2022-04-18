package com.sciatta.openmall.es;

import com.sciatta.openmall.pojo.PagedGridResult;

/**
 * Created by Rain on 2022/4/16<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ElasticSearchService
 */
public interface ElasticSearchService {
    <T> PagedGridResult<T> search(String queryName, String queryValue, Class<T> sourceClass, ResultSetExtractor<T> extractor);

    <T> PagedGridResult<T> search(String queryName, String queryValue, Integer pageNumber, Integer pageSize, Class<T> sourceClass, ResultSetExtractor<T> extractor);

    <T> PagedGridResult<T> search(String queryName, String queryValue, String highlight, SearchCriteria.Sorter sort, Integer pageNumber, Integer pageSize, Class<T> sourceClass, ResultSetExtractor<T> extractor);

    <T> PagedGridResult<T> search(SearchCriteria criteria, Class<T> sourceClass, ResultSetExtractor<T> extractor);
}
