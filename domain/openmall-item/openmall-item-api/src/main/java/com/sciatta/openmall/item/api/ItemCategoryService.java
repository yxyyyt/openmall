package com.sciatta.openmall.item.api;

import com.sciatta.openmall.item.pojo.dto.ItemCategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 商品分类服务
 */
@FeignClient("openmall-item-service")
@RequestMapping("item-category-api")
public interface ItemCategoryService {
    /**
     * 获取第一级商品分类
     *
     * @return 商品分类
     */
    @GetMapping("allRootLevel")
    List<ItemCategoryDTO> queryAllRootLevel();

    /**
     * 查询指定商品分类的子商品分类，及子商品分类的次级商品分类
     *
     * @param parentId 指定商品分类
     * @return 子商品分类，及子商品分类的次级商品分类
     */
    @GetMapping("subCategoriesByParentId")
    List<ItemCategoryDTO> querySubCategoriesByParentId(@RequestParam("parentId") Integer parentId);

    /**
     * 查询指定商品分类信息，及其下所属商品
     *
     * @param parentId 指定商品分类
     * @return 指定商品分类信息，及其下所属商品
     */
    @GetMapping("sixItemsByParentId")
    List<ItemCategoryDTO> querySixItemsByParentId(@RequestParam("parentId") Integer parentId);
}
