package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ImageItemCommentDTO
 */
@Data
public class ImageItemCommentDTO implements Serializable {
    private static final long serialVersionUID = -638661458974088330L;
    
    // ItemComment
    private String commentId;
    private String content;
    private Date createdTime;
    private String itemId;
    private String itemName;
    private String specName;
    
    // ItemImage
    private String itemImg;
}
