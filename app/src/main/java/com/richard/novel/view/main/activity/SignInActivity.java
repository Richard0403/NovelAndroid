package com.richard.novel.view.main.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.richard.novel.R;
import com.richard.novel.common.base.App;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.cache.SysPrefer;
import com.richard.novel.common.cache.UserPrefer;
import com.richard.novel.common.constant.AppConfig;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.UserContract;
import com.richard.novel.presenter.impl.UserContractImpl;
import com.richard.novel.view.base.BaseActivity;
import com.richard.novel.view.base.BaseMVPActivity;
import com.richard.novel.view.base.BaseMVPFragment;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SignInActivity extends BaseMVPActivity<UserContract.Presenter> implements UserContract.View {
    @BindView(R.id.iv_login)
    ImageView iv_login;

    private UserInfo userInfo;
    @Override
    protected int getLayout() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected UserContract.Presenter bindPresenter() {
        return new UserContractImpl();
    }

    @Override
    protected void initData() {
        super.initData();
        userInfo = UserPrefer.getUserInfo();
        if(userInfo != null){
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    }

    public static void start(Context context){
        Intent intent = new Intent(context, SignInActivity.class);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(cn);//ComponentInfo{包名+类名}
        context.startActivity(mainIntent);
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            iv_login.setClickable(false);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            iv_login.setClickable(true);
            if (platform == SHARE_MEDIA.QQ) {
                String uid = data.get("uid");
                String iconurl = data.get("iconurl");
                String name = data.get("name");
                String sex = data.get("gender");

                int sexCode = 0;
                if("男".equals(sex)) {
                    sexCode = 1;
                }else if("女".equals(sex)){
                    sexCode = 0;
                }else{
                    sexCode = 2;
                }
                mPresenter.signIn(name,uid,iconurl,sexCode);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showLongToast("授权失败");
            iv_login.setClickable(true);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showLongToast("授权取消");
            iv_login.setClickable(true);
        }
    };

    @OnClick({R.id.iv_login})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.iv_login:
                if(SysPrefer.IS_DEBUG){//调试用账户
                    mPresenter.signIn("richard","B9B2EC810EE9001A3A05F87BE1B3FC2D","http://q.qlogo.cn/qqapp/1106309587/431B68A936EE7BDBCB3D18728B8D5E4A/100", 0);
                }else{
                    UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, umAuthListener);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void setSignIn(UserInfo userInfo) {
        AppCache.setUserInfo(userInfo);
        SignInActivity.start(mContext, MainActivity.class);
        finish();
    }

    @Override
    public void showError() {

    }

}
