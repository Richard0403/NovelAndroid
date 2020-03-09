package com.richard.novel.common.cache;


import com.richard.novel.common.constant.SharedPreferenceHelper;
import com.richard.novel.http.entity.user.UserInfo;

import java.util.List;

/**
 * By Richard on 2018/4/21.
 */

public class UserPrefer extends SharedPreferenceHelper{

    public static final String USER_INFO = "USER_INFO";
    public static final String SEARCH_RECORD = "SEARCH_RECORD";
    public static final String JPUSH_ID = "JPUSH_ID";


    public static void saveUserInfo(UserInfo userInfo){
        setObject(USER_INFO, userInfo);
    }
    public static UserInfo getUserInfo(){
        return getObject(USER_INFO, UserInfo.class);
    }

    public static void setSearchRecord(List<String> searchRecord){
        setObjectList(SEARCH_RECORD, searchRecord);
    }
    public static List<String> getSearchRecord(){
        return SharedPreferenceHelper.getObjectList(SEARCH_RECORD, String[].class);
    }

    public static void saveJPushId(String registerId) {
        save(JPUSH_ID, registerId);
    }

    public static String getJPushId(){
        return getString(JPUSH_ID);
    }
}
