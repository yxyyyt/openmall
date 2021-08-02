package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.dto.CarouselDTO;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 轮播图服务
 */
public interface CarouselService {
    List<CarouselDTO> queryAll(Integer isShow);
}
