package com.sciatta.openmall.service.support;

import lombok.Data;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/8<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * PagedGridResult
 */
@Data
public class PagedGridResult {
    private int pageNumber;     // 当前页
    private int pages;          // 总页数
    private long total;         // 总记录数
    
    private List<?> rows;           // 当前页所有记录
}
