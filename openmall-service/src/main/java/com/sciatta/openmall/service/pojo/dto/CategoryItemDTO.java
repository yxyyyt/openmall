package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/2<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CategoryItemDTO
 */
@Data
public class CategoryItemDTO implements Serializable {
    
    private static final long serialVersionUID = -5233172788748299506L;
    
    private Integer id;
    private String name;
    private String slogan;
    private String catImage;
    private String bgColor;
    
    private List<ItemFlatteningDTO> itemFlattenings = new ArrayList<>();
}
