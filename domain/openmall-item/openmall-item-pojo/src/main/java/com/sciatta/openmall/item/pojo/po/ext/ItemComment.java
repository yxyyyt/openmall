package com.sciatta.openmall.item.pojo.po.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/9/15<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemComment
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ItemComment extends com.sciatta.openmall.item.pojo.po.mbg.ItemComment implements Serializable {
    private static final long serialVersionUID = -2087145188220909275L;

    // ItemImage
    private String url;
}
