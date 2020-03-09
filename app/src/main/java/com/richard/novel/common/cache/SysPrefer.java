package com.richard.novel.common.cache;


import com.richard.novel.BuildConfig;
import com.richard.novel.common.base.App;
import com.richard.novel.common.constant.SharedPreferenceHelper;
import com.richard.novel.common.utils.VersionUtil;

public class SysPrefer {

    public static final String PREFERENCE_NAME = "config";

    public static final boolean IS_DEBUG = BuildConfig.DEBUG;

    public static boolean getIsFirstRun(){
        return SharedPreferenceHelper.getBoolean(VersionUtil.getVerName(App.getInstance()), true);
    }
    public static void setIsFirstRun(boolean isFirstRun){
        SharedPreferenceHelper.save(VersionUtil.getVerName(App.getInstance()), false);
    }

}
