package com.sciatta.openmall.portlet.mapper.mbg;

import com.sciatta.openmall.portlet.pojo.po.mbg.Carousel;

public interface CarouselMapper {
    int deleteByPrimaryKey(String id);

    int insert(Carousel record);

    int insertSelective(Carousel record);

    Carousel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Carousel record);

    int updateByPrimaryKey(Carousel record);
}