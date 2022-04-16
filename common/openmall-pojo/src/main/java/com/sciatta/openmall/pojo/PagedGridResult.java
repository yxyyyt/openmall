package com.sciatta.openmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/8<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * PagedGridResult
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedGridResult {
    private int pageNumber;     // 当前页
    private List<?> rows;       // 当前页所有记录
    private int pages;          // 总页数
    private long total;         // 总记录数
}
