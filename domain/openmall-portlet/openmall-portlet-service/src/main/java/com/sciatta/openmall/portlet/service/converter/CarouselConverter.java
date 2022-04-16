package com.sciatta.openmall.portlet.service.converter;

import com.sciatta.openmall.portlet.pojo.dto.CarouselDTO;
import com.sciatta.openmall.portlet.pojo.po.mbg.Carousel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CarouselConverter
 */
@Mapper
public abstract class CarouselConverter {
    public static CarouselConverter INSTANCE = Mappers.getMapper(CarouselConverter.class);

    public abstract List<CarouselDTO> toCarouselDTO(List<Carousel> carouselList);
}
