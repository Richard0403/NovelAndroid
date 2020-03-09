package com.richard.novel.http.entity.user;

/**
 * Created by XiaoU on 2018/12/5.
 */

public class InviteRecord {
    private long id;

    private UserInfo.UserBean fromUser;
    private UserInfo.UserBean toUser;
    private Integer accountType;
    private Integer amount;

    private long updateTime;
    private long createTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo.UserBean getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserInfo.UserBean fromUser) {
        this.fromUser = fromUser;
    }

    public UserInfo.UserBean getToUser() {
        return toUser;
    }

    public void setToUser(UserInfo.UserBean toUser) {
        this.toUser = toUser;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
