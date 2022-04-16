package com.sciatta.openmall.item.pojo.query;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentQuery
 */
@Data
public class ItemCommentQuery implements Serializable {
    private static final long serialVersionUID = 5946872621764874308L;
    
    @NotBlank(message = "商品标识不能为空")
    private String itemId;
    
    @NotBlank(message = "商品名不能为空")
    private String itemName;
    
    @NotBlank(message = "商品规格标识不能为空")
    private String itemSpecId;
    
    @NotBlank(message = "商品规格名不能为空")
    private String itemSpecName;
    
    @Min(value = 1, message = "评价级别仅支持好评，中评或差评")
    @Max(value = 3, message = "评价级别仅支持好评，中评或差评")
    @NotNull(message = "评价级别不能为空")
    private Integer commentLevel;
    
    @NotBlank(message = "评价内容不能为空")
    private String content;
}
