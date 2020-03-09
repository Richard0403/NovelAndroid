package com.richard.novel.http.entity.user;

import io.objectbox.annotation.Id;

/**
 * Created by XiaoU on 2018/12/13.
 */

public class GiftBuyRecord {
    public static final int ORDER_STATUS_UNPAY = 0X01;
    public static final int ORDER_STATUS_PAYED = 0X02;
    public static final int ORDER_STATUS_ADD_ADDR = 0X03;
    public static final int ORDER_STATUS_DELIVERED = 0X04;
    public static final int ORDER_STATUS_COMPLETE = 0X05;
    public static final int ORDER_STATUS_CANCEL = 0X06;


    private long id;

    private UserInfo user;

    private GiftInfo giftInfo;

    private Integer orderStatus;

    private Integer price;

    private long updateTime;

    private long createTime;

    private String deliverName;
    private String deliverPhone;
    private String deliverAddr;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public GiftInfo getGiftInfo() {
        return giftInfo;
    }

    public void setGiftInfo(GiftInfo giftInfo) {
        this.giftInfo = giftInfo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getDeliverPhone() {
        return deliverPhone;
    }

    public void setDeliverPhone(String deliverPhone) {
        this.deliverPhone = deliverPhone;
    }

    public String getDeliverAddr() {
        return deliverAddr;
    }

    public void setDeliverAddr(String deliverAddr) {
        this.deliverAddr = deliverAddr;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
