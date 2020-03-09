package com.richard.novel.common.base;

import android.support.multidex.MultiDexApplication;

import com.richard.novel.common.platform.QQPlatform;
import com.richard.novel.common.platform.WXPlatform;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

public class App extends MultiDexApplication {
    public static App INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        PlatformConfig.setQQZone("1106309587","hTQwH9JyDt92nwPr");

        QQPlatform.registAPIToQQ();
        WXPlatform.registAPIToWX();

        JPushInterface.setDebugMode(false);
        JPushInterface.init(getApplicationContext());

    }


    public static App getInstance(){
        return INSTANCE;
    }

}
