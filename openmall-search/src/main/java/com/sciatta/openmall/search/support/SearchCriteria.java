package com.sciatta.openmall.search.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangxiaoyu on 2021/10/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 搜索条件
 */
@Getter
public class SearchCriteria {
    private final Integer pageNumber;
    private final Integer pageSize;
    private final Map<String, String> where;
    private final List<String> highlight;
    private final Map<String, SortOrder> sort;
    
    private SearchCriteria(Integer pageNumber, Integer pageSize, Map<String, String> where,
                           List<String> highlight, Map<String, SortOrder> sort) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.where = where;
        this.highlight = highlight;
        this.sort = sort;
    }
    
    public enum SortOrder {
        ASC,
        DESC
    }
    
    @AllArgsConstructor
    @Getter
    public static class Sorter {
        private final String sortName;
        private final SearchCriteria.SortOrder sortOrder;
    }
    
    public static class Builder {
        private Integer pageNumber;
        private Integer pageSize;
        private final Map<String, String> where = new HashMap<>();
        private final List<String> highlight = new ArrayList<>();
        private final Map<String, SortOrder> sort = new HashMap<>();
        
        public Builder pageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }
        
        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        
        public Builder where(String name, String value) {
            this.where.put(name, value);
            return this;
        }
        
        public Builder highlight(String name) {
            this.highlight.add(name);
            return this;
        }
        
        public Builder sort(Sorter sorter) {
            this.sort.put(sorter.getSortName(), sorter.getSortOrder());
            return this;
        }
        
        public SearchCriteria build() {
            if (ObjectUtils.isEmpty(where)) {
                throw new IllegalArgumentException("搜索条件不能为空");
            }
            return new SearchCriteria(pageNumber, pageSize, where, highlight, sort);
        }
    }
}
