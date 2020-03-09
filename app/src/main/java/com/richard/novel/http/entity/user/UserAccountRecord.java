package com.richard.novel.http.entity.user;

/**
 * Created by XiaoU on 2018/12/6.
 */

public class UserAccountRecord {
    private long id;
    private UserInfo user;
    private Integer type;
    private Integer amount;
    private Integer amountChange;
    private String title;


    private long updateTime;
    private long createTime;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountChange() {
        return amountChange;
    }

    public void setAmountChange(Integer amountChange) {
        this.amountChange = amountChange;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
