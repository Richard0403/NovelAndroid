package com.richard.novel.http.entity.user;

/**
 * Created by XiaoU on 2018/12/5.
 */

public class ReadTimeSummary {
    private String date;
    private Long times;
    private Long readTime;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public Long getReadTime() {
        return readTime;
    }

    public void setReadTime(Long readTime) {
        this.readTime = readTime;
    }
}
