package com.sciatta.openmall.portlet.api;

import com.sciatta.openmall.portlet.pojo.dto.CarouselDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 轮播图服务
 */
@FeignClient("openmall-portlet-service")
@RequestMapping("carousel-api")
public interface CarouselService {
    @GetMapping("carousel")
    List<CarouselDTO> queryAll(@RequestParam("isShow") Integer isShow);
}
