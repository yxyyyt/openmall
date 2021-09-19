package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.CarouselMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.Carousel;
import com.sciatta.openmall.service.CarouselService;
import com.sciatta.openmall.service.converter.CarouselConverter;
import com.sciatta.openmall.service.pojo.dto.CarouselDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CarouselServiceImpl
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    private final CarouselMapper carouselMapper;
    
    public CarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselMapper = carouselMapper;
    }
    
    @Override
    public List<CarouselDTO> queryAll(Integer isShow) {
        List<Carousel> carouselList = carouselMapper.selectByIsShow(isShow);
        
        return CarouselConverter.INSTANCE.toCarouselDTO(carouselList);
    }
}
