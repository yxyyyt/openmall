package com.sciatta.openmall.service.support.paged;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sciatta.openmall.search.support.SearchResult;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static com.sciatta.openmall.common.constants.PagedConstants.PAGE_SIZE;
import static com.sciatta.openmall.common.constants.PagedConstants.PAGE_START;

/**
 * Created by yangxiaoyu on 2021/8/8<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 分页上下文，提供分页核心功能
 */
@Getter
public abstract class PagedContext<T> {
    protected final Integer pageNumber;
    protected final Integer pageSize;
    protected final PagedGridResult pagedGridResult;
    
    private PagedContext(Integer pageNumber, Integer pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagedGridResult = new PagedGridResult();
    }
    
    public static class Builder<T> {
        private Integer pageNumber;
        private Integer pageSize;
        private Env env;
        
        public Builder<T> setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }
        
        public Builder<T> setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        
        public Builder<T> setEnv(Env env) {
            this.env = env;
            return this;
        }
        
        public PagedContext<T> build() {
            PagedContext<T> context = null;
            
            if (this.pageNumber == null) {
                this.pageNumber = PAGE_START;
            }
            
            if (this.pageSize == null) {
                pageSize = PAGE_SIZE;
            }
            
            if (ObjectUtils.isEmpty(env) || env.equals(Env.DAO)) {
                context = new DaoPagedContext<>(pageNumber, pageSize);
            } else {
                context = new EsPagedContext<>(pageNumber, pageSize);
            }
            
            Assert.notNull(context, "PagedContext不能为空");
            return context;
        }
    }
    
    public List<T> startPage(MapperCallBack mapperCallBack, boolean useExecuteCallBackResult) {
        
        List<T> result = doStartPage(mapperCallBack);
        
        if (useExecuteCallBackResult) {
            pagedGridResult.setRows(result);
        }
        
        return result;
    }
    
    protected abstract List<T> doStartPage(MapperCallBack mapperCallBack);
    
    public <P> PagedGridResult getPagedGridResult(List<P> resultRows) {
        pagedGridResult.setRows(resultRows);
        return pagedGridResult;
    }
    
    public PagedGridResult getPagedGridResult() {
        return pagedGridResult;
    }
    
    public enum Env {
        DAO,
        ES
    }
    
    public interface MapperCallBack {
        Object execute();
    }
    
    static class DaoPagedContext<T> extends PagedContext<T> {
        private DaoPagedContext(Integer pageNumber, Integer pageSize) {
            super(pageNumber, pageSize);
        }
        
        @Override
        @SuppressWarnings("unchecked")
        protected List<T> doStartPage(MapperCallBack mapperCallBack) {
            // 分页
            PageHelper.startPage(pageNumber, pageSize);
            
            List<T> result = (List<T>) mapperCallBack.execute();
            
            // 初始化PagedGridResult分页元数据
            PageInfo<?> pageInfo = new PageInfo<>(result);
            
            pagedGridResult.setPageNumber(pageInfo.getPageNum());  // 当前页
            pagedGridResult.setPages(pageInfo.getPages());  // 总页数
            pagedGridResult.setTotal(pageInfo.getTotal());    // 总记录数
            
            return result;
        }
    }
    
    static class EsPagedContext<T> extends PagedContext<T> {
        
        private EsPagedContext(Integer pageNumber, Integer pageSize) {
            super(pageNumber, pageSize);
        }
        
        @Override
        @SuppressWarnings("unchecked")
        protected List<T> doStartPage(MapperCallBack mapperCallBack) {
            SearchResult<T> searchResult = (SearchResult<T>) mapperCallBack.execute();
            
            pagedGridResult.setPageNumber(searchResult.getPageNumber());
            pagedGridResult.setPages(searchResult.getTotalPages());
            pagedGridResult.setTotal(searchResult.getTotalRecords());
            
            return searchResult.getRows();
        }
    }
}