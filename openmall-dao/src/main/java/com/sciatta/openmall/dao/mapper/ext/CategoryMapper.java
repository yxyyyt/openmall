package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.ext.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends com.sciatta.openmall.dao.mapper.mbg.CategoryMapper {
    List<Category> selectByType(@Param("type") Integer type);
    
    List<Category> selectSubCategoriesByParentId(@Param("parentId") Integer parentId);
    
    List<Category> selectSixItemsByParentId(@Param("parentId") Integer parentId);
}