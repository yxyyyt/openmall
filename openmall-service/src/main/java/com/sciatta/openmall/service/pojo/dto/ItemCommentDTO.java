package com.sciatta.openmall.service.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentDTO
 */
@Data
public class ItemCommentDTO implements Serializable {
    
    private static final long serialVersionUID = 7947111750664230242L;
    
    // ItemComment
    private String id;
    private Integer commentLevel;
    private String content;
    private String itemSpecName;
    private Date createdTime;
    private String itemId;
    private String itemName;
    
    // User
    private String userFace;
    private String nickname;
    
    // ItemImage
    private String url;
}
