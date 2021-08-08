package com.sciatta.openmall.service.support;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import static com.sciatta.openmall.common.constants.PagedConstants.PAGE_SIZE;
import static com.sciatta.openmall.common.constants.PagedConstants.PAGE_START;

/**
 * Created by yangxiaoyu on 2021/8/8<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 分页上下文，提供分页核心功能
 */
public class PagedContext {
    private final Integer pageNumber;
    private final Integer pageSize;
    private final PagedGridResult pagedGridResult;
    
    private PagedContext(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagedGridResult = new PagedGridResult();
    }
    
    public static class Builder {
        private Integer pageNumber;
        private Integer pageSize;
        
        public Builder setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }
        
        public Builder setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        
        public PagedContext build() {
            if (this.pageNumber == null) {
                this.pageNumber = PAGE_START;
            }
            
            if (this.pageSize == null) {
                pageSize = PAGE_SIZE;
            }
            
            return new PagedContext(this.pageNumber, this.pageSize);
        }
    }
    
    public <T> List<T> startPage(MapperCallBack<T> mapperCallBack, boolean useExecuteCallBackResult) {
        // 分页
        doStartPage();
        
        List<T> result = mapperCallBack.execute();
        
        // 初始化PagedGridResult分页元数据
        initPagedGridResult(result, useExecuteCallBackResult);
        
        return result;
    }
    
    private void doStartPage() {
        PageHelper.startPage(this.pageNumber, this.pageSize);
    }
    
    private void initPagedGridResult(List<?> list, boolean useExecuteCallBackResult) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        
        pagedGridResult.setPageNumber(pageInfo.getPageNum());  // 当前页
        pagedGridResult.setPages(pageInfo.getPages());  // 总页数
        pagedGridResult.setTotal(pageInfo.getTotal());    // 总记录数
        
        if (useExecuteCallBackResult) {
            pagedGridResult.setRows(list);
        }
    }
    
    public <T> PagedGridResult getPagedGridResult(List<T> resultRows) {
        pagedGridResult.setRows(resultRows);
        return pagedGridResult;
    }
    
    public PagedGridResult getPagedGridResult() {
        return pagedGridResult;
    }
    
    public interface MapperCallBack<T> {
        List<T> execute();
    }
}

