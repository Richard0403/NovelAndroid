package com.richard.novel.common.cache;


import com.richard.novel.http.entity.user.UserInfo;

/**
 * By Richard on 2018/3/9.
 */

public class AppCache {
    private static UserInfo userInfo;

    public static UserInfo getUserInfo(){
        if(userInfo == null){
            userInfo = UserPrefer.getUserInfo();
        }
        return userInfo;
    }
    public static void setUserInfo(UserInfo user){
        userInfo = user;
        UserPrefer.saveUserInfo(userInfo);
    }
}
