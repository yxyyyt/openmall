package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * 轮播图DTO
 */
@Data
public class CarouselDTO implements Serializable {
    private static final long serialVersionUID = -523607214408772076L;
    
    private String imageUrl;
    private String backgroundColor;
    private String itemId;
    private String catId;
    private Integer type;
}
