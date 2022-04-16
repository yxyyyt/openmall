package com.sciatta.openmall.portlet.web.controller;

import com.sciatta.openmall.cache.Cache;
import com.sciatta.openmall.cache.CacheChildKey;
import com.sciatta.openmall.common.constants.CacheConstants;
import com.sciatta.openmall.common.enums.YesOrNo;
import com.sciatta.openmall.item.pojo.dto.ItemCategoryDTO;
import com.sciatta.openmall.item.pojo.vo.ItemCategorySubVO;
import com.sciatta.openmall.item.pojo.vo.ItemCategoryVO;
import com.sciatta.openmall.item.pojo.vo.ItemCategoryWithItemVO;
import com.sciatta.openmall.pojo.JSONResult;
import com.sciatta.openmall.portlet.api.CarouselService;
import com.sciatta.openmall.portlet.pojo.dto.CarouselDTO;
import com.sciatta.openmall.portlet.pojo.vo.CarouselVO;
import com.sciatta.openmall.portlet.service.client.ItemCategoryServiceFeignClient;
import com.sciatta.openmall.portlet.web.converter.CarouselConverter;
import com.sciatta.openmall.portlet.web.converter.ItemCategoryConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
    private final ItemCategoryServiceFeignClient itemCategoryService;

    public IndexController(CarouselService carouselService, ItemCategoryServiceFeignClient itemCategoryService) {
        this.carouselService = carouselService;
        this.itemCategoryService = itemCategoryService;
    }

    @GetMapping("carousels")
    @Cache(key = CacheConstants.CAROUSELS, toClass = CarouselVO.class, isList = true, processor = "redisCacheProcessor")
    public JSONResult carousels() {
        List<CarouselDTO> carouselDTOList = carouselService.queryAll(YesOrNo.YES.type);
        List<CarouselVO> carouselVOList = CarouselConverter.INSTANCE.toCarouselVO(carouselDTOList);

        return JSONResult.success(carouselVOList);
    }

    @GetMapping("categories")
    @Cache(key = CacheConstants.CATEGORIES, toClass = ItemCategoryVO.class, isList = true, processor = "redisCacheProcessor")
    public JSONResult categories() {
        List<ItemCategoryDTO> itemCategoryDTOList = itemCategoryService.queryAllRootLevel();
        List<ItemCategoryVO> itemCategoryVOList = ItemCategoryConverter.INSTANCE.toItemCategoryVO(itemCategoryDTOList);

        return JSONResult.success(itemCategoryVOList);
    }

    @GetMapping("subCategories/{parentId}")
    @Cache(key = CacheConstants.SUB_CATEGORIES, toClass = ItemCategorySubVO.class, isList = true, processor = "redisCacheProcessor")
    public JSONResult subCategories(
            @PathVariable @NotNull(message = "分类标识不能为空") @CacheChildKey(order = 0) Integer parentId) {
        List<ItemCategoryDTO> itemCategoryDTOList = itemCategoryService.querySubCategoriesByParentId(parentId);
        List<ItemCategorySubVO> itemCategorySubVOList = ItemCategoryConverter.INSTANCE.toItemCategorySubVO(itemCategoryDTOList);

        return JSONResult.success(itemCategorySubVOList);
    }

    @GetMapping("/sixItems/{parentId}")
    public JSONResult sixItems(@PathVariable @NotNull(message = "分类标识不能为空") Integer parentId) {
        List<ItemCategoryDTO> itemCategoryDTOList = itemCategoryService.querySixItemsByParentId(parentId);
        List<ItemCategoryWithItemVO> itemCategoryWithItemVOList = ItemCategoryConverter.INSTANCE.toItemCategoryWithItemVO(itemCategoryDTOList);

        return JSONResult.success(itemCategoryWithItemVOList);
    }
}
