package com.richard.novel.http.entity.home;


/**
 * By Richard on 2018/1/6.
 */

public class Version{


    /**
     * data : {"updateTime":1542079949000,"createTime":1542079858000,"status":0,"id":1,"isForce":0,"size":7,"updateContent":"更新{n}1.支持最新书籍{n}2.修正了评论回复的bug","url":"http://qiniu.ifinder.cc/Note_V1.1.0_2018-11-13-11-28_release.apk","versionCode":10100,"versionName":"1.1.0"}
     */

    /**
     * updateTime : 1542079949000
     * createTime : 1542079858000
     * status : 0
     * id : 1
     * isForce : 0
     * size : 7
     * updateContent : 更新{n}1.支持最新书籍{n}2.修正了评论回复的bug
     * url : http://qiniu.ifinder.cc/Note_V1.1.0_2018-11-13-11-28_release.apk
     * versionCode : 10100
     * versionName : 1.1.0
     */

    private long updateTime;
    private long createTime;
    private int status;
    private int id;
    private int isForce;
    private Float size;
    private String updateContent;
    private String url;
    private int versionCode;
    private String versionName;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsForce() {
        return isForce;
    }

    public void setIsForce(int isForce) {
        this.isForce = isForce;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
