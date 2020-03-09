package com.richard.novel.http.entity.user;

import io.objectbox.annotation.Id;

/**
 * Created by XiaoU on 2018/11/8.
 */

public class Praise {
    private long id;
    private UserInfo.UserBean user;
    private long resourceId;
    private Integer resourceType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserInfo.UserBean getUser() {
        return user;
    }

    public void setUser(UserInfo.UserBean user) {
        this.user = user;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }


    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }
}
