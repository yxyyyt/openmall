package com.sciatta.openmall.item.service.impl;

import com.sciatta.openmall.item.api.ItemCategoryService;
import com.sciatta.openmall.item.mapper.ext.ItemCategoryMapper;
import com.sciatta.openmall.item.pojo.dto.ItemCategoryDTO;
import com.sciatta.openmall.item.pojo.po.ext.ItemCategory;
import com.sciatta.openmall.item.service.converter.ItemCategoryConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCategoryServiceImpl
 */
@RestController
public class ItemCategoryServiceImpl implements ItemCategoryService {
    private final ItemCategoryMapper itemCategoryMapper;

    public ItemCategoryServiceImpl(ItemCategoryMapper itemCategoryMapper) {
        this.itemCategoryMapper = itemCategoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemCategoryDTO> queryAllRootLevel() {
        List<ItemCategory> itemCategoryList = itemCategoryMapper.selectByType(1); // 第一级商品种类

        return ItemCategoryConverter.INSTANCE.toItemCategoryDTO(itemCategoryList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemCategoryDTO> querySubCategoriesByParentId(Integer parentId) {
        List<ItemCategory> subItemCategoryList = itemCategoryMapper.selectSubCategoriesByParentId(parentId);

        return ItemCategoryConverter.INSTANCE.toItemCategoryDTO(subItemCategoryList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemCategoryDTO> querySixItemsByParentId(Integer parentId) {
        List<ItemCategory> itemCategoryList = itemCategoryMapper.selectSixItemsByParentId(parentId);

        return ItemCategoryConverter.INSTANCE.toItemCategoryDTO(itemCategoryList);
    }
}
