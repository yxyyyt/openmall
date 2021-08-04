package com.sciatta.openmall.service.impl;

import com.sciatta.openmall.dao.mapper.ext.ItemImageMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemParamMapper;
import com.sciatta.openmall.dao.mapper.ext.ItemSpecMapper;
import com.sciatta.openmall.dao.mapper.mbg.ItemMapper;
import com.sciatta.openmall.dao.pojo.po.mbg.Item;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemImage;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemParam;
import com.sciatta.openmall.dao.pojo.po.mbg.ItemSpec;
import com.sciatta.openmall.service.ItemService;
import com.sciatta.openmall.service.converter.ItemConverter;
import com.sciatta.openmall.service.pojo.dto.ItemDTO;
import com.sciatta.openmall.service.pojo.dto.ItemImageDTO;
import com.sciatta.openmall.service.pojo.dto.ItemParamDTO;
import com.sciatta.openmall.service.pojo.dto.ItemSpecDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yangxiaoyu on 2021/8/4<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * ItemServiceImpl
 */
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;
    private final ItemImageMapper itemImageMapper;
    private final ItemSpecMapper itemSpecMapper;
    private final ItemParamMapper itemParamMapper;
    
    public ItemServiceImpl(ItemParamMapper itemParamMapper, ItemMapper itemMapper, ItemImageMapper itemImageMapper, ItemSpecMapper itemSpecMapper) {
        this.itemParamMapper = itemParamMapper;
        this.itemMapper = itemMapper;
        this.itemImageMapper = itemImageMapper;
        this.itemSpecMapper = itemSpecMapper;
    }
    
    @Override
    public ItemDTO queryItemById(String id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        
        return ItemConverter.INSTANCE.itemToItemDTO(item);
    }
    
    @Override
    public List<ItemImageDTO> queryItemImagesByItemId(String itemId) {
        List<ItemImage> itemImageList = itemImageMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.itemImageListToItemImageDTOList(itemImageList);
    }
    
    @Override
    public List<ItemSpecDTO> queryItemSpecsByItemId(String itemId) {
        List<ItemSpec> itemSpecList = itemSpecMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.itemSpecListToItemSpecDTOList(itemSpecList);
    }
    
    @Override
    public ItemParamDTO queryItemParamByItemId(String itemId) {
        ItemParam itemParam = itemParamMapper.selectByItemId(itemId);
        
        return ItemConverter.INSTANCE.itemParamToItemParamDTO(itemParam);
    }
}
