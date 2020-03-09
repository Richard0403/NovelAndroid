package com.richard.novel.http.entity.record;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by XiaoU on 2018/9/28.
 */
@Entity
public class TimeRecord implements Serializable{

    @Id
    private long boxId;

    private long bookId;
    private long readTime;
    private long saveTime;
    private boolean isUploaded;

    public TimeRecord() {
    }

    public TimeRecord(long bookId, long readTime, long saveTime, boolean isUploaded) {
        this.bookId = bookId;
        this.readTime = readTime;
        this.saveTime = saveTime;
        this.isUploaded = isUploaded;
    }

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getReadTime() {
        return readTime;
    }

    public void setReadTime(long readTime) {
        this.readTime = readTime;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }
}
