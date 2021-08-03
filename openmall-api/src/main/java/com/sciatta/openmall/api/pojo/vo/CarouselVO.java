package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CarouselVO
 */
@Data
public class CarouselVO implements Serializable {
    
    private static final long serialVersionUID = 7633863322869442666L;
    
    private String imageUrl;
    private String backgroundColor;
    private String itemId;
    private String catId;
    private Integer type;
}
