package com.sciatta.openmall.search;

import com.sciatta.openmall.search.pojo.po.Item;
import com.sciatta.openmall.search.support.SearchCriteria;
import com.sciatta.openmall.search.support.SearchResult;

/**
 * Created by yangxiaoyu on 2021/10/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemSearchService
 */
public interface ItemSearchService {
    SearchResult<Item> search(SearchCriteria criteria);
}
