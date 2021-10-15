package com.sciatta.openmall.search.support;


import org.springframework.data.elasticsearch.core.SearchHit;

/**
 * Created by yangxiaoyu on 2021/10/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ResultSetExtractor
 */
public interface ResultSetExtractor<T> {
    T extractor(SearchHit<T> searchHit);
}
