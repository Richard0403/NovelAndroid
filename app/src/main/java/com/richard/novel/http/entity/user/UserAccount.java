package com.richard.novel.http.entity.user;

import io.objectbox.annotation.Id;

/**
 * Created by XiaoU on 2018/12/5.
 */

public class UserAccount {
    public static final Integer TYPE_SCORE = 0X01;
    public static final Integer TYPE_CASH = 0X02;
    public static final Integer TYPE_BONUS = 0X03;


    private long id;

    private UserInfo user;
    private Integer type;
    private String name;
    private Integer amount = 0;
    private Integer frozenAmount = 0;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(Integer frozenAmount) {
        this.frozenAmount = frozenAmount;
    }
}
