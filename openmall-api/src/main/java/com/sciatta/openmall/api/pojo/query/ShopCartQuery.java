package com.sciatta.openmall.api.pojo.query;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/9<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ShopCartQuery
 */
@Data
public class ShopCartQuery implements Serializable {
    private static final long serialVersionUID = -2310205691860958186L;
    
    @NotBlank(message = "商品标识不能为空")
    private String itemId;
    
    @NotBlank(message = "商品图片不能为空")
    private String itemImgUrl;
    
    @NotBlank(message = "商品名不能为空")
    private String itemName;
    
    @NotBlank(message = "商品规格标识不能为空")
    private String specId;
    
    @NotBlank(message = "商品规格名不能为空")
    private String specName;
    
    @NotNull(message = "购买数量不能为空")
    private Integer buyCounts;
    
    @NotBlank(message = "商品优惠价不能为空")
    private String priceDiscount;
    
    @NotBlank(message = "商品原价不能为空")
    private String priceNormal;
}
