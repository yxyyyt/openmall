package com.sciatta.openmall.item.pojo.po.ext;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/9/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * Category
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ItemCategory extends com.sciatta.openmall.item.pojo.po.mbg.ItemCategory implements Serializable {

    private static final long serialVersionUID = -3188533113046221192L;

    // Category
    private List<ItemCategory> subCategories = new ArrayList<>();

    // Item
    private List<Item> items = new ArrayList<>();
}