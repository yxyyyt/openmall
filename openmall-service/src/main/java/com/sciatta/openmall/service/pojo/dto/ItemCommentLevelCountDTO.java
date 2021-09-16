package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/5<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentLevelCountDTO
 */
@Data
public class ItemCommentLevelCountDTO implements Serializable {
    private static final long serialVersionUID = -5132680731068443418L;
    
    public Integer totalCounts;
    public Integer goodCounts;
    public Integer normalCounts;
    public Integer badCounts;
}
