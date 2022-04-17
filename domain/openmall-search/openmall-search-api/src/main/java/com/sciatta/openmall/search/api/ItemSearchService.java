package com.sciatta.openmall.search.api;

import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.search.pojo.dto.ItemSearchDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 主搜
 */
@FeignClient("openmall-search-service")
@RequestMapping("item-search-api")
public interface ItemSearchService {
    @GetMapping("items")
    PagedGridResult<ItemSearchDTO> search(
            @RequestParam("keywords") String keywords,
            @RequestParam("sort") String sort,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);
}
