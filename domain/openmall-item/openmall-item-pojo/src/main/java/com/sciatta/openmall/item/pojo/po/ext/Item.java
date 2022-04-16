package com.sciatta.openmall.item.pojo.po.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by yangxiaoyu on 2021/9/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * Item
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class Item extends com.sciatta.openmall.item.pojo.po.mbg.Item {
    private static final long serialVersionUID = 4992662433983228404L;

    // ItemImage
    private String url;

    // ItemSpec
    private String specId;
    private String specName;
    private Integer priceDiscount;
    private Integer priceNormal;
}
