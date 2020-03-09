package com.richard.novel.http.api;

/**
 * Created by XiaoU on 2018/10/11.
 */

public interface ResultListener<T> {
    void onSuccess(T t);
    void onFail();
}
