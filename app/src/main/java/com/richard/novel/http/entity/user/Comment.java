package com.richard.novel.http.entity.user;

import java.util.HashSet;
import java.util.Set;

import io.objectbox.annotation.Id;

/**
 * Created by XiaoU on 2018/11/8.
 */

public class Comment {
    private Long id;
    private String content;

    private String commentType;
    private Long resourceId;
    private UserInfo.UserBean user;
    private Long toCommentId;
    private UserInfo.UserBean toUser;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public UserInfo.UserBean getUser() {
        return user;
    }

    public void setUser(UserInfo.UserBean user) {
        this.user = user;
    }

    public Long getToCommentId() {
        return toCommentId;
    }

    public void setToCommentId(Long toCommentId) {
        this.toCommentId = toCommentId;
    }

    public UserInfo.UserBean getToUser() {
        return toUser;
    }

    public void setToUser(UserInfo.UserBean toUser) {
        this.toUser = toUser;
    }
}
