package com.sciatta.openmall.item.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/5<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentLevelCountVO
 */
@Data
public class ItemCommentLevelCountVO implements Serializable {

    private static final long serialVersionUID = -727270174679232161L;

    public Integer totalCounts;
    public Integer goodCounts;
    public Integer normalCounts;
    public Integer badCounts;
}
