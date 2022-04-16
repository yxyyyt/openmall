package com.sciatta.openmall.item.api;

import com.sciatta.openmall.item.pojo.dto.*;
import com.sciatta.openmall.item.pojo.query.ItemCommentQuery;
import com.sciatta.openmall.pojo.PagedGridResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Rain on 2022/4/5<br>
 * All Rights Reserved(C) 2017 - 2022 SCIATTA <br> <p/>
 * ItemService
 */
@FeignClient("openmall-item-service")
@RequestMapping("item-api")
public interface ItemService {
    @GetMapping("item")
    ItemDTO queryItemById(@RequestParam("itemId") String id);

    @GetMapping("itemsBySpecIds")
    List<ItemDTO> queryItemsBySpecIds(@RequestParam("specIds") String specIds);

    @GetMapping("itemImages")
    List<ItemImageDTO> queryItemImagesByItemId(@RequestParam("itemId") String itemId);

    @GetMapping("itemSpecs")
    List<ItemSpecDTO> queryItemSpecsByItemId(@RequestParam("itemId") String itemId);

    @GetMapping("itemParam")
    ItemParamDTO queryItemParamByItemId(@RequestParam("itemId") String itemId);

    @GetMapping("pagedItemComments")
    PagedGridResult queryItemComments(
            @RequestBody ItemCommentQuery itemCommentQuery,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping("pagedUserComments")
    PagedGridResult queryUserComments(
            @RequestParam("userId") String userId,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping("countComments")
    ItemCommentLevelCountDTO queryCommentLevelCounts(@RequestParam("itemId") String itemId);

    @PostMapping("decreaseStock")
    void decreaseItemSpecStock(@RequestParam("specId") String specId,
                               @RequestParam("buyCounts") int buyCounts);

    @PostMapping("saveComments")
    void saveComments(
            @RequestParam("orderId") String orderId,
            @RequestParam("userId") String userId,
            @RequestBody List<ItemCommentQuery> commentList);

    @GetMapping("itemsByKeywords")
    PagedGridResult searchItemsByKeywords(
            @RequestParam("keywords") String keywords,
            @RequestParam("sort") String sort,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);

    @GetMapping("itemsByCatId")
    PagedGridResult searchItemsByCatId(
            @RequestParam("catId") Integer catId,
            @RequestParam("sort") String sort,
            @RequestParam(value = "page", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize);
}
