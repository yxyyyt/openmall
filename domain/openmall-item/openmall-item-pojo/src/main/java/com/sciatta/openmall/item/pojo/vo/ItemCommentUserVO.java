package com.sciatta.openmall.item.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentUserVO
 */
@Data
public class ItemCommentUserVO implements Serializable {

    private static final long serialVersionUID = -5816529236865230792L;

    // ItemComment
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;

    // User
    private String userFace;
    private String nickname;
}
