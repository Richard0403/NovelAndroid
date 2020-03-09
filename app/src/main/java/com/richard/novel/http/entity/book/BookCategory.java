package com.richard.novel.http.entity.book;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by XiaoU on 2018/9/28.
 */
@Entity
public class BookCategory implements Serializable{
    /**
     * updateTime : 1521785976000
     * createTime : 1521785976000
     * code : 102
     * parent_code : 0
     * name : 心理学
     * status : 0
     * comment : null
     * icon :
     * media_type : 1000
     */
    @Id
    private long boxId;


    private long updateTime;
    private long createTime;
    @SerializedName("code")

    private long codeX;
    private int parent_code;
    private String name;
    private int status;
    private String comment;
    private String icon;
    private int media_type;


    public BookCategory() {
    }

    public BookCategory(long codeX, String name) {
        this.codeX = codeX;
        this.name = name;
    }

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
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

    public long getCodeX() {
        return codeX;
    }

    public void setCodeX(long codeX) {
        this.codeX = codeX;
    }

    public int getParent_code() {
        return parent_code;
    }

    public void setParent_code(int parent_code) {
        this.parent_code = parent_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }
}
