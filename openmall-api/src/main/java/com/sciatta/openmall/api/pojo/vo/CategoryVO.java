package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/7/31<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CategoryVO
 */
@Data
public class CategoryVO implements Serializable {
    private static final long serialVersionUID = 7100329173789543868L;
    
    private Integer id;
    private String name;
    private String logo;
}
