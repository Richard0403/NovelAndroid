package com.richard.novel.http.entity.user;


import com.richard.novel.http.entity.base.BaseEntity;

/**
 * by Richard on 2017/9/8
 * desc:
 */
public class UserEntity extends BaseEntity {


    /**
     * data : {"user":{"id":1,"name":"richae0x1.62e798p40","sex":0,"header":"http://oow561q5i.bkt.clouddn.com/703de9f4b2264a16b9710402308f5849.jpg"},"token":"eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX25hbWUiOiI5ODVkNTA3OTY2ZXciLCJjcmVhdGVfZGF0ZSI6MTUyNDMwNDQyNzc4MSwiZXhwIjoxNTI0OTA5MjI3fQ.9vET5TbfQC9NTjHOKdB-rF3OlK07455koD3XBOuN6s9Ulb_sqh-aM3LS9nZR1bf4o-Z8ZOr8KFE4G3t11oSRJg"}
     */

    private UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
