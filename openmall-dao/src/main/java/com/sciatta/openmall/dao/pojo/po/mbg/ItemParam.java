package com.sciatta.openmall.dao.pojo.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class ItemParam implements Serializable {
    @ApiModelProperty(value = "商品参数主键")
    private String id;

    @ApiModelProperty(value = "商品外键")
    private String itemId;

    @ApiModelProperty(value = "产地，例：中国江苏")
    private String productPlace;

    @ApiModelProperty(value = "保质期，例：180天")
    private String foodPeriod;

    @ApiModelProperty(value = "品牌名，例：三只大灰狼")
    private String brand;

    @ApiModelProperty(value = "生产厂名，例：大灰狼工厂")
    private String factoryName;

    @ApiModelProperty(value = "生产厂址，例：大灰狼生产基地")
    private String factoryAddress;

    @ApiModelProperty(value = "包装方式，例：袋装")
    private String packagingMethod;

    @ApiModelProperty(value = "规格重量，例：35g")
    private String weight;

    @ApiModelProperty(value = "存储方法，例：常温5~25°")
    private String storageMethod;

    @ApiModelProperty(value = "食用方式，例：开袋即食")
    private String eatMethod;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(String productPlace) {
        this.productPlace = productPlace;
    }

    public String getFoodPeriod() {
        return foodPeriod;
    }

    public void setFoodPeriod(String foodPeriod) {
        this.foodPeriod = foodPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryAddress() {
        return factoryAddress;
    }

    public void setFactoryAddress(String factoryAddress) {
        this.factoryAddress = factoryAddress;
    }

    public String getPackagingMethod() {
        return packagingMethod;
    }

    public void setPackagingMethod(String packagingMethod) {
        this.packagingMethod = packagingMethod;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStorageMethod() {
        return storageMethod;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }

    public String getEatMethod() {
        return eatMethod;
    }

    public void setEatMethod(String eatMethod) {
        this.eatMethod = eatMethod;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", itemId=").append(itemId);
        sb.append(", productPlace=").append(productPlace);
        sb.append(", foodPeriod=").append(foodPeriod);
        sb.append(", brand=").append(brand);
        sb.append(", factoryName=").append(factoryName);
        sb.append(", factoryAddress=").append(factoryAddress);
        sb.append(", packagingMethod=").append(packagingMethod);
        sb.append(", weight=").append(weight);
        sb.append(", storageMethod=").append(storageMethod);
        sb.append(", eatMethod=").append(eatMethod);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}