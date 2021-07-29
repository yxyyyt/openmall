package com.sciatta.openmall.dao.pojo.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    @ApiModelProperty(value = "订单主键（订单编号）")
    private String id;

    @ApiModelProperty(value = "用户外键")
    private String userId;

    @ApiModelProperty(value = "收货人快照")
    private String receiverName;

    @ApiModelProperty(value = "收货人手机号快照")
    private String receiverMobile;

    @ApiModelProperty(value = "收货地址快照")
    private String receiverAddress;

    @ApiModelProperty(value = "订单总价格")
    private Integer totalAmount;

    @ApiModelProperty(value = "实际支付总价格")
    private Integer realPayAmount;

    @ApiModelProperty(value = "邮费 默认为零，代表包邮")
    private Integer postAmount;

    @ApiModelProperty(value = "支付方式")
    private Integer payMethod;

    @ApiModelProperty(value = "买家留言")
    private String leftMsg;

    @ApiModelProperty(value = "扩展字段")
    private String extend;

    @ApiModelProperty(value = "买家是否评价 1：已评价 0：未评价")
    private Integer isComment;

    @ApiModelProperty(value = "逻辑删除状态 1：删除 0：未删除")
    private Integer isDelete;

    @ApiModelProperty(value = "创建时间（成交时间）")
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Integer realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Integer getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Integer postAmount) {
        this.postAmount = postAmount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
        sb.append(", userId=").append(userId);
        sb.append(", receiverName=").append(receiverName);
        sb.append(", receiverMobile=").append(receiverMobile);
        sb.append(", receiverAddress=").append(receiverAddress);
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", realPayAmount=").append(realPayAmount);
        sb.append(", postAmount=").append(postAmount);
        sb.append(", payMethod=").append(payMethod);
        sb.append(", leftMsg=").append(leftMsg);
        sb.append(", extend=").append(extend);
        sb.append(", isComment=").append(isComment);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}