package com.richard.novel.http.entity.base;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class ListEntity<T> extends BaseEntity {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
