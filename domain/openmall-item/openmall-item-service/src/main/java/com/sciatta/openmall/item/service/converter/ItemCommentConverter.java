package com.sciatta.openmall.item.service.converter;

import com.sciatta.openmall.item.pojo.dto.ItemCommentDTO;
import com.sciatta.openmall.item.pojo.po.ext.ItemComment;
import com.sciatta.openmall.item.pojo.query.ItemCommentQuery;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.n3r.idworker.Sid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/12<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemCommentConverter
 */
@Mapper
public abstract class ItemCommentConverter {
    public static ItemCommentConverter INSTANCE = Mappers.getMapper(ItemCommentConverter.class);

    public abstract ItemComment toItemComment(
            ItemCommentQuery itemCommentQuery, String id, String userId, Date createdTime, Date updatedTime);

    public List<ItemComment> toItemComment(
            List<ItemCommentQuery> itemCommentQueryList, String userId) {

        List<ItemComment> itemCommentList = new ArrayList<>();

        for (ItemCommentQuery itemCommentQuery : itemCommentQueryList) {
            itemCommentList.add(toItemComment(
                    itemCommentQuery, Sid.nextShort(), userId, new Date(), new Date()));
        }

        return itemCommentList;
    }

    public abstract List<ItemCommentDTO> toItemCommentDTO(List<ItemComment> itemCommentList);
}
