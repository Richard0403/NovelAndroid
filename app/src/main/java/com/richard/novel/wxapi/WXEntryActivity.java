package com.richard.novel.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.platform.WXPlatform;
import com.richard.novel.common.utils.LogUtil;
import com.richard.novel.common.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by James
 * Date 2017/12/8.
 * description 微信返回消息页，此类不可以更改名字和位置
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
        api = WXPlatform.getWxApi();
        if(!api.handleIntent(getIntent(), this)){
            finish();
            LogUtil.i(TAG, "finish");
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.i(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        LogUtil.i(TAG, "onResp");
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                switch (baseResp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        String code = ((SendAuth.Resp) baseResp).code;
                        LogUtil.i(TAG, "onResp code"+code);
                        // TODO 获取 code 成功，拿code去服务器请求登录结果
                        if(AppCache.getUserInfo()==null){
                            //登录
                            WXPlatform.loginWithCode(code, WXEntryActivity.this);
                        }else{
                            //绑定
                            WXPlatform.bindWithCode(code,WXEntryActivity.this);
                        }
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        // TODO 分享成功
                        ToastUtil.showSingleToast("分享成功");
                        break;
                    default:
                        break;
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtil.showSingleToast("您取消了操作");
                if(baseResp.getType() == RETURN_MSG_TYPE_SHARE){

                }
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
}
