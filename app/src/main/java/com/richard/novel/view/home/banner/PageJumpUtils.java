package com.richard.novel.view.home.banner;

import android.app.Activity;

import com.richard.novel.view.home.activity.BookDetailActivity;

/**
 * Created by XiaoU on 2018/10/15.
 */

public class PageJumpUtils {
    public static void jumpToPage(Activity activity, int type, long resourceId){
        switch (type){
            case 1001:
                BookDetailActivity.start(activity, resourceId);
                break;
            case 1002:
                BookDetailActivity.start(activity, resourceId);
                break;
        }
    }
}
