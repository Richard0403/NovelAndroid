package com.richard.novel.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.richard.novel.common.cache.UserPrefer;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.view.main.activity.SplashActivity;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by Richard
 */

public class JPBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_SING_IN = "ACTION_SING_IN";



    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);

        //获取REGISTRATION_ID
        if (intent.getAction().equals(JPushInterface.ACTION_REGISTRATION_ID)) {
            String registartionId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            //保存RegistartionId
            UserPrefer.saveJPushId(registartionId);
        }

        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if(!StringUtils.isEmpty(extra)){
                //跳转页
//                jumpToNotificationPage(context, extra);
            }else{
                Intent defaultIntent = new Intent(context, SplashActivity.class);
                defaultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(defaultIntent);
            }
        }
        //获取自定义附加值---服务端推送的时候才会有
    }
}
