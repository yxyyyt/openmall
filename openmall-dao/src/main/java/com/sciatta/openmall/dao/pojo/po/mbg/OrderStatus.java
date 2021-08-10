package com.sciatta.openmall.dao.pojo.po.mbg;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

public class OrderStatus implements Serializable {
    @ApiModelProperty(value = "订单主键")
    private String orderId;

    @ApiModelProperty(value = "订单状态 10：待付款 20：已付款，待发货 30：已发货，待收货（7天自动确认） 40：交易成功（此时可以评价） 50：交易关闭（待付款时用户取消或长时间未付款，系统识别后自动关闭）")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单创建时间 对应[10:待付款]状态")
    private Date createdTime;

    @ApiModelProperty(value = "支付成功时间 对应[20:已付款，待发货]状态")
    private Date payTime;

    @ApiModelProperty(value = "发货时间 对应[30：已发货，待收货]状态")
    private Date deliverTime;

    @ApiModelProperty(value = "交易成功时间 对应[40：交易成功]状态")
    private Date successTime;

    @ApiModelProperty(value = "交易关闭时间 对应[50：交易关闭]状态")
    private Date closeTime;

    @ApiModelProperty(value = "留言时间，用户在交易成功后的留言时间")
    private Date commentTime;

    private static final long serialVersionUID = 1L;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", orderId=").append(orderId);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", payTime=").append(payTime);
        sb.append(", deliverTime=").append(deliverTime);
        sb.append(", successTime=").append(successTime);
        sb.append(", closeTime=").append(closeTime);
        sb.append(", commentTime=").append(commentTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}