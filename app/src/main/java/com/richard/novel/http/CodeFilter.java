package com.richard.novel.http;


import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

import com.richard.novel.common.base.App;
import com.richard.novel.common.cache.ActivityCollector;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.view.main.activity.MainActivity;
import com.richard.novel.view.main.activity.SignInActivity;

/**
 * By Richard on 2018/5/2.
 */

public class CodeFilter {
    public static final int CODE_SING_OUT_DATE = 401;

    public static void filter(int code, String msg) {
        switch (code){
            case CODE_SING_OUT_DATE:
                AppCache.setUserInfo(null);
                SignInActivity.start(ActivityCollector.getTopActivity());
                break;
        }
    }
}
