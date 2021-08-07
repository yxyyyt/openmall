package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.ext.SearchItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper extends com.sciatta.openmall.dao.mapper.mbg.ItemMapper {
    List<SearchItem> searchItems(@Param("keywords") String keywords, @Param("sort") String sort);
}