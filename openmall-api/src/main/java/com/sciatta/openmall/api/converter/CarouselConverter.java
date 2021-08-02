package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.vo.CarouselVO;
import com.sciatta.openmall.service.pojo.dto.CarouselDTO;
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
    
    public abstract CarouselVO carouselDTOToCarouselVO(CarouselDTO carouselDTO);
    
    public abstract List<CarouselVO> carouselsDTOToCarouselsVO(List<CarouselDTO> carouselsDTO);
}
