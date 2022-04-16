package com.sciatta.openmall.portlet.service.impl;

import com.sciatta.openmall.portlet.api.CarouselService;
import com.sciatta.openmall.portlet.mapper.ext.CarouselMapper;
import com.sciatta.openmall.portlet.pojo.dto.CarouselDTO;
import com.sciatta.openmall.portlet.pojo.po.mbg.Carousel;
import com.sciatta.openmall.portlet.service.converter.CarouselConverter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CarouselServiceImpl
 */
@RestController
public class CarouselServiceImpl implements CarouselService {
    private final CarouselMapper carouselMapper;
    
    public CarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselMapper = carouselMapper;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CarouselDTO> queryAll(Integer isShow) {
        List<Carousel> carouselList = carouselMapper.selectByIsShow(isShow);
        
        return CarouselConverter.INSTANCE.toCarouselDTO(carouselList);
    }
}
