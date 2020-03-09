package com.richard.novel.common.platform;

import com.richard.novel.common.base.App;
import com.richard.novel.common.constant.AppConfig;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
/**
 * Created by James
 * Date 2017/12/8.
 * description 微信公众平台登录及分享
 * 1.微信登录，微信登录基于OAuth2.0协议标准构建，先去微信注册App，然后发起登录请求以获取code，用户同意后，通过code获取用户access_token，参见
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419317851&token=&lang=zh_CN
 */
public class WXPlatform {

    private static IWXAPI api;


    private WXPlatform(){
        throw new RuntimeException(getClass().getSimpleName() + " should not be instantiated");
    }

    /**
     * 向微信注册 App 需要在应用启动时调用，否则在后续getWXApi时，将抛出异常
     */
    public static IWXAPI registAPIToWX(){
        api = WXAPIFactory.createWXAPI(App.getInstance(), AppConfig.WECHAT.WECHAT_ID, true);
        api.registerApp(AppConfig.WECHAT.WECHAT_ID);
        return api;
    }

    public static IWXAPI getWxApi(){
        if(api == null){
            registAPIToWX();
        }
        return api;
    }


    /**
     * 向微信发起登录请求
     * 返回结果在
     * @link {com.xiaou.mobile.wxapi.WXEntryActivity#}
     * 中处理
     */
    public static void login(){
        if (api.isWXAppInstalled()) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "xiaou_wx_login";
            api.sendReq(req);
        }else{
            ToastUtil.showSingleToast("您还未安装微信客户端");
        }
    }

    public static void loginWithCode(String code, WXEntryActivity wxEntryActivity) {
        //TODO 登录
    }

    public static void bindWithCode(String code, WXEntryActivity wxEntryActivity) {
        //TODO 绑定
    }

    private String getURL(String code){
        return  "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" +
                AppConfig.WECHAT.WECHAT_ID + "&secret=" + AppConfig.WECHAT.WECHAT_KEY
                +"&code=" + code + "&grant_type=authorization_code";
    }

}
