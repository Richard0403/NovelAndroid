package com.richard.novel.http.entity.base;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class ObjEntity<T> extends BaseEntity {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
