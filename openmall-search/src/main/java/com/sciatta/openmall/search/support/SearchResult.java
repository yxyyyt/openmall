package com.sciatta.openmall.search.support;

import lombok.Data;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/10/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * SearchResult
 */
@Data
public class SearchResult<T> {
    private int pageNumber;     // 当前页
    private int totalPages;          // 总页数
    private long totalRecords;         // 总记录数
    
    private List<T> rows;           // 当前页所有记录
}
