package com.richard.novel.common.utils;


import android.util.Log;

import com.richard.novel.common.cache.SysPrefer;


/**
 * Created by James
 * Date 2017/12/5.
 * description
 */
public class LogUtil {

    private static final String TAG = "com.xiaou.mobile.log";

    public static void i(String tag, String msg){
        if(SysPrefer.IS_DEBUG){
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(SysPrefer.IS_DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(SysPrefer.IS_DEBUG){
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if(SysPrefer.IS_DEBUG){
            Log.e(tag, msg);
        }
    }

    /**
     * 也可以使用这种方式输出log，默认TAG为xiaou
     * @param msg msm
     */
    public static void i(String msg){
        i(TAG, msg);
    }

    public static void d(String msg){
        d(TAG, msg);
    }

    public static void v(String msg){
        v(TAG, msg);
    }

    public static void e(String msg){
        e(TAG, msg);
    }

}
