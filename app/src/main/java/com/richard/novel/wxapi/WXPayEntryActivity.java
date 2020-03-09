package com.richard.novel.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.richard.novel.R;
import com.richard.novel.common.platform.WXPlatform;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.view.base.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
//    @BindView(R.id.iv_face)
//    ImageView iv_face;
//    @BindView(R.id.tv_pay_result)
//    TextView tv_pay_result;
//    @BindView(R.id.tv_pay_count)
//    TextView tv_pay_count;
//    @BindView(R.id.tv_complete)
//    TextView tv_complete;

    private IWXAPI api;

    private boolean isPaySuccess;

    @Override
    protected int getLayout() {
        return 0;
//        return R.layout.layout_pay_result;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        api = WXPlatform.getWxApi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            /* 0	成功	展示成功页面
            -1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
            -2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP*/
           int errCode = resp.errCode;
           switch (errCode){
               case 0:
//                   tv_complete.postDelayed(()->{//延迟刷新用户信息，防止课时到账延迟
//                       EventBus.getDefault().post(new EventMsg(EventMsg.CODE_REQUIRE_USER_INFO));
//                   },2*1000);
                   setPayResultView(true);
                   break;
               case -1:
               case -2:
                   setPayResultView(false);
                   break;
           }
        }
    }

    private void setPayResultView(boolean success){
        isPaySuccess = success;
//        iv_face.setImageResource(success? R.mipmap.icon_pay_true:R.mipmap.icon_pay_false);
//        tv_pay_result.setText(success?"支付成功":"支付失败");
//        tv_pay_count.setText(success?"":"请重试");
//        tv_complete.setText(success?"完成":"重新购买");
    }


//    @OnClick(R.id.tv_complete)
//    protected void complete(){
//        if(isPaySuccess){
//            CourseTimesActivity.start(mContext, CourseTimesActivity.class);
//        }
//        finish();
//    }
}
