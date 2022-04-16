package com.sciatta.openmall.mapper.support;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sciatta.openmall.common.constants.PagedConstants;
import com.sciatta.openmall.pojo.PagedGridResult;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by Rain on 2022/4/7<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * 分页帮助类
 */
@Slf4j
public class MapperPagedHelper {
    public static void startPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            log.warn("page number put invalid value " + pageNum + ", take default value " + PagedConstants.PAGE_START);
            pageNum = PagedConstants.PAGE_START;
        }

        if (pageSize == null || pageSize <= 0) {
            log.warn("page size put invalid value " + pageSize + ", take default value " + PagedConstants.PAGE_SIZE);
            pageSize = PagedConstants.PAGE_SIZE;
        }

        PageHelper.startPage(pageNum, pageSize);
    }

    public static PagedGridResult endPage(List<?> mapperRows, List<?> converterRows) {
        PageInfo<?> pageInfo = new PageInfo<>(mapperRows);

        return PagedGridResult.builder()
                .pageNumber(pageInfo.getPageNum())
                .rows(converterRows)
                .pages(pageInfo.getPages())
                .total(pageInfo.getTotal())
                .build();
    }
}
