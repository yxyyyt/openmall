package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.ext.SubCategory;
import com.sciatta.openmall.dao.pojo.po.mbg.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends com.sciatta.openmall.dao.mapper.mbg.CategoryMapper {
    List<Category> selectByType(@Param("type") Integer type);
    
    List<SubCategory> selectSubCategoriesByParentId(@Param("parentId") Integer parentId);
}