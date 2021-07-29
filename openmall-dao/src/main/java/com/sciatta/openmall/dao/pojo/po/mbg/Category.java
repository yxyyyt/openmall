package com.sciatta.openmall.dao.pojo.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Category implements Serializable {
    @ApiModelProperty(value = "商品分类主键")
    private Integer id;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类类型")
    private Integer type;

    @ApiModelProperty(value = "商品分类外键")
    private Integer parentId;

    @ApiModelProperty(value = "图标")
    private String logo;

    @ApiModelProperty(value = "口号")
    private String slogan;

    @ApiModelProperty(value = "分类图")
    private String catImage;

    @ApiModelProperty(value = "背景颜色")
    private String bgColor;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", parentId=").append(parentId);
        sb.append(", logo=").append(logo);
        sb.append(", slogan=").append(slogan);
        sb.append(", catImage=").append(catImage);
        sb.append(", bgColor=").append(bgColor);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}