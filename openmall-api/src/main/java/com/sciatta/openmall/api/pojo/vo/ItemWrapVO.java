package com.sciatta.openmall.api.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemWrapVO
 */
@Data
public class ItemWrapVO implements Serializable {
    
    private static final long serialVersionUID = -4296280692116218417L;
    
    private ItemVO item;
    private List<ItemImageVO> itemImgList = new ArrayList<>();
    private List<ItemSpecVO> itemSpecList = new ArrayList<>();
    private ItemParamVO itemParams;
}
