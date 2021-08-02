package com.sciatta.openmall.dao.mapper.ext;

import com.sciatta.openmall.dao.pojo.po.mbg.Carousel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CarouselMapper
 */
public interface CarouselMapper extends com.sciatta.openmall.dao.mapper.mbg.CarouselMapper {
    List<Carousel> selectByIsShow(@Param("isShow") Integer isShow);
}
