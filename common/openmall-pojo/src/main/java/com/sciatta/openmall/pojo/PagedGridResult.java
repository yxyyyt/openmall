package com.sciatta.openmall.pojo;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/8<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * PagedGridResult
 */
public class PagedGridResult<T> {
    private int pageNumber;     // 当前页
    private List<T> rows;       // 当前页所有记录
    private int pages;          // 总页数
    private long total;         // 总记录数

    public PagedGridResult() {
    }

    public PagedGridResult(int pageNumber, List<T> rows, int pages, long total) {
        this.pageNumber = pageNumber;
        this.rows = rows;
        this.pages = pages;
        this.total = total;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public List<T> getRows() {
        return rows;
    }

    public int getPages() {
        return pages;
    }

    public long getTotal() {
        return total;
    }

    public PagedGridResult<T> setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PagedGridResult<T> setRows(List<T> rows) {
        this.rows = rows;
        return this;
    }

    public PagedGridResult<T> setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public PagedGridResult<T> setTotal(long total) {
        this.total = total;
        return this;
    }
}
