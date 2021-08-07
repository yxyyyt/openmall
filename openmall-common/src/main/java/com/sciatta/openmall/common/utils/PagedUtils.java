package com.sciatta.openmall.common.utils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * PagedUtils
 */
public class PagedUtils {
    public static void startPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
    }
    
    public static PagedGridResult createPagedGridResult() {
        return new PagedGridResult();
    }
    
    public static void initPagedGridResult(List<?> list, Integer page, PagedGridResult pagedGridResult) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        
        pagedGridResult.setPage(page);  // 当前页
        pagedGridResult.setTotal(pageInfo.getPages());  // 总页数
        pagedGridResult.setRecords(pageInfo.getTotal());    // 总记录数
    }
    
    public static void setRows(PagedGridResult pagedGridResult, List<?> list) {
        pagedGridResult.setRows(list);  // 当前页的记录
    }
    
    @Data
    @AllArgsConstructor
    public static class Page {
        private Integer pageNum;
        private Integer pageSize;
    }
    
    @Data
    public static class PagedGridResult {
        private int page;               // 当前页数
        private int total;              // 总页数
        private long records;           // 总记录数
        private List<?> rows;           // 每行显示的内容
    }
}
