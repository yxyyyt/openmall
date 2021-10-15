package com.sciatta.openmall.search.pojo.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * Created by yangxiaoyu on 2021/10/14<br>
 * All Rights Reserved(C) 2017 - 2021 SCIATTA<br><p/>
 * Item
 */
@Data
@Document(indexName = "openmall-items", type = "_doc", createIndex = false)
public class Item implements Serializable {
    private static final long serialVersionUID = -432997845455080347L;
    // Item
    @Id
    @Field(store = true, type = FieldType.Text, index = false)
    private String id;
    
    @Field(store = true, type = FieldType.Text, index = true)
    private String itemName;
    
    @Field(store = true)
    private Integer sellCounts;
    
    // ItemImage
    @Field(store = true, type = FieldType.Text, index = false)
    private String url;
    
    // ItemSpec
    @Field(store = true, type = FieldType.Integer)
    private Integer priceDiscount;
}
