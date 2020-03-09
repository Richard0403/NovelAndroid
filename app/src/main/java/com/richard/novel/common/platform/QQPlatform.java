package com.richard.novel.common.platform;

import android.app.Activity;

import com.richard.novel.common.base.App;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.constant.AppConfig;
import com.richard.novel.common.utils.LogUtil;
import com.richard.novel.common.utils.ToastUtil;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * By Richard on 2018/1/9.
 */

public class QQPlatform {
    private static Tencent mTencent;
    private static LoginUiListener loginlistener;

    public static void registAPIToQQ(){
        mTencent = Tencent.createInstance(AppConfig.QQ.QQ_ID, App.getInstance());
    }
    public static Tencent getmTencent(){
        if(mTencent == null){
            registAPIToQQ();
        }
        return mTencent;
    }

    public static LoginUiListener getLoginlistener(){
        if(loginlistener == null){
            loginlistener = new LoginUiListener(){
                @Override
                protected void doComplete(JSONObject values) {
                    super.doComplete(values);
                    LogUtil.i("doComplete", values.toString());
                   try {
                       String access_token = values.getString("access_token");
                       LogUtil.i("doComplete", access_token);
                       if(AppCache.getUserInfo() == null){
                           logWithToken(access_token);
                       }else{
                           bindWithCode(access_token);
                       }

                   } catch (JSONException e) {
                       LogUtil.e("QQ授权，获取access_token失败");
                   }
                }
            };
        }
        return loginlistener;
    }

    private static void bindWithCode(String access_token) {
        //TODO 绑定
    }

    private static void logWithToken(String access_token) {
        //TODO 登录
    }

    public static void login( Activity activity) {
        //SCOPE = “get_user_info,add_t”；所有权限用“all”
        if (!getmTencent().isSessionValid()) {
            getmTencent().login(activity, "all", getLoginlistener());
        } else {
            getmTencent().logout(activity);
            getmTencent().login(activity, "all", getLoginlistener());
        }
    }


    private static class LoginUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            ToastUtil.showSingleToast("授权成功");
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(o));
                doComplete(jsonObject);
            } catch (JSONException e) {
                LogUtil.e("QQ授权解析失败");
            }
        }

        protected void doComplete(JSONObject values){};

        @Override
        public void onError(UiError e) {
            LogUtil.e("QQ 授权失败");
        }

        @Override
        public void onCancel() {
            LogUtil.e("QQ 授权取消");
        }
    }

}
