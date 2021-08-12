package com.sciatta.openmall.service;

import com.sciatta.openmall.service.pojo.query.OrderItemCommentServiceQuery;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * MyCommentService
 */
public interface MyCommentService {
    void saveComments(String orderId, String userId,
                      List<OrderItemCommentServiceQuery> commentList);
}
