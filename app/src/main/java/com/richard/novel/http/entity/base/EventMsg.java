package com.richard.novel.http.entity.base;

/**
 * Created by XiaoU on 2018/10/17.
 */

public class EventMsg<T> {
    public static int CODE_SHELF = 0x01;
    public static int CODE_READ_TIME = 0x02;
    public static int CODE_SCORE = 0x03;



    private int code;
    private T data;

    public EventMsg(int code) {
        this.code = code;
    }

    public EventMsg(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
