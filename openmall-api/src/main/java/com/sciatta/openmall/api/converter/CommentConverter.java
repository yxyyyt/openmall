package com.sciatta.openmall.api.converter;

import com.sciatta.openmall.api.pojo.query.OrderItemCommentApiQuery;
import com.sciatta.openmall.service.pojo.query.OrderItemCommentServiceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * CommentConverter
 */
@Mapper
public abstract class CommentConverter {
    public static CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);
    
    public abstract List<OrderItemCommentServiceQuery> orderItemCommentApiQueryListToOrderItemCommentServiceQueryList(List<OrderItemCommentApiQuery> orderItemCommentApiQueryList);
}
