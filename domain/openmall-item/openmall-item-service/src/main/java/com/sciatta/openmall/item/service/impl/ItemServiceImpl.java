package com.sciatta.openmall.item.service.impl;

import com.sciatta.openmall.common.enums.CommentLevel;
import com.sciatta.openmall.item.api.ItemService;
import com.sciatta.openmall.item.mapper.ext.*;
import com.sciatta.openmall.item.pojo.dto.*;
import com.sciatta.openmall.item.pojo.po.ext.Item;
import com.sciatta.openmall.item.pojo.po.ext.ItemComment;
import com.sciatta.openmall.item.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.item.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.item.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.item.pojo.query.ItemCommentQuery;
import com.sciatta.openmall.item.service.client.OrderServiceFeignClient;
import com.sciatta.openmall.item.service.client.UserServiceFeignClient;
import com.sciatta.openmall.item.service.converter.ItemCommentConverter;
import com.sciatta.openmall.item.service.converter.ItemConverter;
import com.sciatta.openmall.mapper.support.MapperPagedHelper;
import com.sciatta.openmall.order.pojo.dto.OrderDTO;
import com.sciatta.openmall.pojo.PagedGridResult;
import com.sciatta.openmall.user.pojo.dto.UserDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemServiceImpl
 */
@RestController
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;
    private final ItemImageMapper itemImageMapper;
    private final ItemSpecMapper itemSpecMapper;
    private final ItemParamMapper itemParamMapper;
    private final ItemCommentMapper itemCommentMapper;

    private final UserServiceFeignClient userService;
    private final OrderServiceFeignClient orderService;

    public ItemServiceImpl(ItemParamMapper itemParamMapper,
                           ItemMapper itemMapper,
                           ItemImageMapper itemImageMapper,
                           ItemSpecMapper itemSpecMapper,
                           ItemCommentMapper itemCommentMapper,
                           UserServiceFeignClient userService,
                           OrderServiceFeignClient orderService) {
        this.itemParamMapper = itemParamMapper;
        this.itemMapper = itemMapper;
        this.itemImageMapper = itemImageMapper;
        this.itemSpecMapper = itemSpecMapper;
        this.itemCommentMapper = itemCommentMapper;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDTO queryItemById(String id) {
        Item item = itemMapper.selectByPrimaryKey(id);

        return ItemConverter.INSTANCE.toItemDTO(item);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemImageDTO> queryItemImagesByItemId(String itemId) {
        List<ItemImage> itemImageList = itemImageMapper.selectByItemId(itemId);

        return ItemConverter.INSTANCE.toItemImageDTO(itemImageList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemSpecDTO> queryItemSpecsByItemId(String itemId) {
        List<ItemSpec> itemSpecList = itemSpecMapper.selectByItemId(itemId);

        return ItemConverter.INSTANCE.toItemSpecDTO(itemSpecList);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemParamDTO queryItemParamByItemId(String itemId) {
        ItemParam itemParam = itemParamMapper.selectByItemId(itemId);

        return ItemConverter.INSTANCE.toItemParamDTO(itemParam);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedGridResult<ItemCommentDTO> queryItemComments(ItemCommentQuery itemCommentQuery, Integer pageNumber, Integer pageSize) {

        MapperPagedHelper.startPage(pageNumber, pageSize);

        // 不同用户的商品评价
        List<ItemComment> itemCommentList = itemCommentMapper.selectItemComments(
                itemCommentQuery.getItemId(),
                itemCommentQuery.getCommentLevel());

        List<ItemCommentDTO> itemCommentDTOList = ItemCommentConverter.INSTANCE.toItemCommentDTO(itemCommentList);
        setUserInfo(itemCommentDTOList);    // 获取用户信息

        return MapperPagedHelper.endPage(itemCommentList, itemCommentDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedGridResult<ItemCommentDTO> queryUserComments(String userId, Integer pageNumber, Integer pageSize) {

        MapperPagedHelper.startPage(pageNumber, pageSize);

        // 同一个用户的商品评价
        List<ItemComment> itemCommentList = itemCommentMapper.selectItemCommentsByUserId(userId);
        List<ItemCommentDTO> itemCommentDTOList = ItemCommentConverter.INSTANCE.toItemCommentDTO(itemCommentList);

        return MapperPagedHelper.endPage(itemCommentList, itemCommentDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemCommentLevelCountDTO queryCommentLevelCounts(String itemId) {
        Integer goodCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = itemCommentMapper.selectCommentCountsByItemIdAndLevel(itemId, CommentLevel.BAD.type);

        Integer totalCounts = goodCounts + normalCounts + badCounts;

        return ItemConverter.INSTANCE.toItemCommentLevelCountDTO(goodCounts, normalCounts, badCounts, totalCounts);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        itemSpecMapper.decreaseItemSpecStock(specId, buyCounts);
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<ItemDTO> queryItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> idList = CollectionUtils.arrayToList(ids);

        if (idList.size() <= 0) {
            return Collections.EMPTY_LIST;
        }

        List<Item> itemList = itemMapper.searchItemsBySpecIds(idList);

        return ItemConverter.INSTANCE.toItemDTO(itemList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveComments(String orderId, String userId, List<ItemCommentQuery> commentList) {
        OrderDTO orderDTO = orderService.queryOrderByOrderIdAndUserId(orderId, userId);

        if (ObjectUtils.isEmpty(orderDTO)) {
            throw new IllegalArgumentException("订单不存在");
        }

        // 批量插入评论
        List<ItemComment> itemCommentList = ItemCommentConverter.INSTANCE.toItemComment(commentList, userId);
        itemCommentMapper.insertBatch(itemCommentList);

        // 更新为已评论
        orderService.updateCommented(orderId);
    }

    @Override
    public PagedGridResult<ItemDTO> searchItemsByKeywords(String keywords, String sort, Integer pageNumber, Integer pageSize) {
        MapperPagedHelper.startPage(pageNumber, pageSize);

        List<Item> itemList = itemMapper.searchItemsByKeywords(keywords, sort);

        List<ItemDTO> itemDTOList = ItemConverter.INSTANCE.toItemDTO(itemList);

        return MapperPagedHelper.endPage(itemList, itemDTOList);
    }

    @Override
    public PagedGridResult<ItemDTO> searchItemsByCatId(Integer catId, String sort, Integer pageNumber, Integer pageSize) {
        MapperPagedHelper.startPage(pageNumber, pageSize);

        List<Item> itemList = itemMapper.searchItemsByCatId(catId, sort);

        List<ItemDTO> itemDTOList = ItemConverter.INSTANCE.toItemDTO(itemList);

        return MapperPagedHelper.endPage(itemList, itemDTOList);
    }

    // private

    private void setUserInfo(List<ItemCommentDTO> itemCommentDTOList) {
        HashMap<String, UserDTO> userCache = new HashMap<>();

        for (ItemCommentDTO itemCommentDTO : itemCommentDTOList) {
            UserDTO userDTO = userCache.get(itemCommentDTO.getUserId());    // 使用上一次查询的缓存
            if (null == userDTO) {
                userCache.putIfAbsent(
                        itemCommentDTO.getUserId(),
                        userService.queryUserByUserId(itemCommentDTO.getUserId()));
                userDTO = userCache.get(itemCommentDTO.getUserId());
            }

            itemCommentDTO.setUserFace(userDTO.getFace());
            itemCommentDTO.setNickname(userDTO.getNickname());
        }
    }
}
