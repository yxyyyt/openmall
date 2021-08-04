package com.sciatta.openmall.api.controller;

import com.sciatta.openmall.api.converter.CarouselConverter;
import com.sciatta.openmall.api.converter.CategoryConverter;
import com.sciatta.openmall.api.pojo.vo.CarouselVO;
import com.sciatta.openmall.api.pojo.vo.CategoryItemVO;
import com.sciatta.openmall.api.pojo.vo.CategoryVO;
import com.sciatta.openmall.api.pojo.vo.SubCategoryVO;
import com.sciatta.openmall.common.JSONResult;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.service.CarouselService;
import com.sciatta.openmall.service.CategoryService;
import com.sciatta.openmall.service.pojo.dto.CarouselDTO;
import com.sciatta.openmall.service.pojo.dto.CategoryDTO;
import com.sciatta.openmall.service.pojo.dto.CategoryItemDTO;
import com.sciatta.openmall.service.pojo.dto.SubCategoryDTO;
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
    public JSONResult carousels() {
        List<CarouselDTO> carouselDTOList = carouselService.queryAll(YesOrNo.YES.type);
        
        List<CarouselVO> carouselsVO = CarouselConverter.INSTANCE.carouselDTOListToCarouselVOList(carouselDTOList);
        return JSONResult.success(carouselsVO);
    }
    
    @GetMapping("categories")
    public JSONResult categories() {
        List<CategoryDTO> categoryDTOList = categoryService.queryAllRootLevel();
        
        List<CategoryVO> categoryVOList = CategoryConverter.INSTANCE.categoryDTOListToCategoryVOList(categoryDTOList);
        return JSONResult.success(categoryVOList);
    }
    
    @GetMapping("subCategories/{parentId}")
    public JSONResult subCategories(@PathVariable Integer parentId) {
        
        if (parentId == null) {
            return JSONResult.errorUsingMessage("分类不存在");
        }
        
        List<SubCategoryDTO> subCategoryDTOList = categoryService.querySubCategoriesByParentId(parentId);
        List<SubCategoryVO> subCategoryVOList = CategoryConverter.INSTANCE.subCategoryDTOListToSubCategoryVOList(subCategoryDTOList);
        
        return JSONResult.success(subCategoryVOList);
    }
    
    @GetMapping("/sixItems/{parentId}")
    public JSONResult sixItems(@PathVariable Integer parentId) {
        
        if (parentId == null) {
            return JSONResult.errorUsingMessage("分类不存在");
        }
        
        List<CategoryItemDTO> categoryItemDTOList = categoryService.querySixItemsByParentId(parentId);
        List<CategoryItemVO> categoryItemVOList = CategoryConverter.INSTANCE.categoryItemDTOListToCategoryItemVOList(categoryItemDTOList);
        
        return JSONResult.success(categoryItemVOList);
    }
}
