package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.CarouselConverter;
import com.sciatta.openmall.api.converter.CategoryConverter;
import com.sciatta.openmall.api.pojo.vo.CarouselVO;
import com.sciatta.openmall.api.pojo.vo.CategoryItemVO;
import com.sciatta.openmall.api.pojo.vo.CategorySubVO;
import com.sciatta.openmall.api.pojo.vo.CategoryVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.service.CarouselService;
import com.sciatta.openmall.service.CategoryService;
import com.sciatta.openmall.service.pojo.dto.CarouselDTO;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.support.cache.Cache;
import com.sciatta.openmall.service.support.cache.CacheChildKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 首页
 */
@Slf4j
@Validated
@RestController
@RequestMapping("index")
public class IndexController {
    private final CarouselService carouselService;
    private final CategoryService categoryService;
    
    public IndexController(CarouselService carouselService, CategoryService categoryService) {
        this.carouselService = carouselService;
        this.categoryService = categoryService;
    }
    
    @GetMapping("carousels")
    @Cache(key = CacheConstants.CAROUSELS, toClass = CarouselVO.class, isList = true)
    public JSONResult carousels() {
        List<CarouselDTO> carouselDTOList = carouselService.queryAll(YesOrNo.YES.type);
        List<CarouselVO> carouselVOList = CarouselConverter.INSTANCE.toCarouselVO(carouselDTOList);
        
        return JSONResult.success(carouselVOList);
    }
    
    @GetMapping("categories")
    @Cache(key = CacheConstants.CATEGORIES, toClass = CategoryVO.class, isList = true)
    public JSONResult categories() {
        List<CategoryDTO> categoryDTOList = categoryService.queryAllRootLevel();
        List<CategoryVO> categoryVOList = CategoryConverter.INSTANCE.toCategoryVO(categoryDTOList);
        
        return JSONResult.success(categoryVOList);
    }
    
    @GetMapping("subCategories/{parentId}")
    @Cache(key = CacheConstants.SUB_CATEGORIES, toClass = CategorySubVO.class, isList = true)
    public JSONResult subCategories(@PathVariable @CacheChildKey(order = 0) Integer parentId) {
        List<CategoryDTO> categoryDTOList = categoryService.querySubCategoriesByParentId(parentId);
        List<CategorySubVO> categorySubVOList = CategoryConverter.INSTANCE.toCategorySubVO(categoryDTOList);
        
        return JSONResult.success(categorySubVOList);
    }
    
    @GetMapping("/sixItems/{parentId}")
    public JSONResult sixItems(@PathVariable Integer parentId) {
        List<CategoryDTO> categoryDTOList = categoryService.querySixItemsByParentId(parentId);
        List<CategoryItemVO> categoryItemVOList = CategoryConverter.INSTANCE.toCategoryItemVO(categoryDTOList);
        
        return JSONResult.success(categoryItemVOList);
    }
}
