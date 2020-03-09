package com.richard.novel.common.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.richard.novel.R;
import com.richard.novel.common.base.App;
import com.richard.novel.common.platform.QQPlatform;
import com.richard.novel.common.platform.WXPlatform;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * By Richard on 2018/1/9.
 */

public class ShareUtils {
    public static final String YYB_LINK = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zxhd";

    private static final int THUMB_SIZE = 150;
    static Pattern pattern = Pattern.compile("\\{(.*?)\\}");

    public static IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            ToastUtil.showSingleToast("分享成功");
        }
        @Override
        public void onError(UiError e) {
            ToastUtil.showSingleToast("分享失败");
        }
        @Override
        public void onCancel() {
            ToastUtil.showSingleToast("分享取消");
        }
    };

    /**
     * 分享文字
     *
     * @param shareContent 分享内容
     * @param type         分享类型，收藏、好友, 朋友圈、
     *                     SendMessageToWX.Req.WXSceneFavorite;
                            SendMessageToWX.Req.WXSceneSession;
                            SendMessageToWX.Req.WXSceneTimeline
     *
     */
    public static void wxShareText(String shareContent, int type) {
        if (!TextUtils.isEmpty(shareContent)) {
            WXTextObject textObj = new WXTextObject();
            textObj.text = shareContent;


            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = textObj;
            // 发送文本类型的消息时，title字段不起作用
            // msg.title = "Title";
            msg.description = shareContent;

            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
            req.message = msg;
            req.scene = type;
            WXPlatform.getWxApi().sendReq(req);

        }
    }

    /**
     * 分享一个图片
     *
     * @param shareBitmap 要分享的图片
     * @param type        分享类型，朋友圈、收藏、好友
     */
    public static void wxShareImage(Bitmap shareBitmap, int type) {
        WXImageObject imgObj = new WXImageObject(shareBitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

//        Bitmap thumbBmp = Bitmap.createScaledBitmap(shareBitmap, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = bmpToByteArray(shareBitmap);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = type;
        WXPlatform.getWxApi().sendReq(req);

    }

    /**
     * 分享一个网页
     *
     * @param httpUrl     要分享的连接
     * @param type        分享类型，朋友圈、收藏、好友
     * @param iconRes     ICON
     * @param title       标题
     * @param description 描述
     */
    public static void wxShareWebPage(String httpUrl, int type, int iconRes, String title, String description) {
        Bitmap icon = BitmapFactory.decodeResource(App.getInstance().getResources(), iconRes);
        wxShareWebPage(httpUrl, type, icon, title, description);
    }

    /**
     * 分享一个网页
     *
     * @param httpUrl     连接
     * @param type        分享类型，朋友圈、收藏、好友
     * @param icon        连接前显示的图标
     * @param title       别人看到的标题
     * @param description 别人看到的描述
     */
    public static void wxShareWebPage(String httpUrl, int type, Bitmap icon, String title, String description) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = httpUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        msg.thumbData = bmpToByteArray(icon);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = type;
        WXPlatform.getWxApi().sendReq(req);

    }

    /**
     * 得到Bitmap的byte
     *
     * @param bitmap 图片
     * @return 返回压缩的图片
     */
    private static byte[] bmpToByteArray(Bitmap bitmap) {

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) 300) / width;
        float scaleHeight = ((float) 300) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);



        int maxkb = 30;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        newbm.compress(Bitmap.CompressFormat.PNG, 100, output);

        LogUtil.e("pic_size==="+output.toByteArray().length);

        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            output.reset(); //清空output
            newbm.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 10;
        }
        LogUtil.e("pic_size==="+output.toByteArray().length);
        return output.toByteArray();

    }

    /**
     * 构建一个唯一标志
     *
     * @param type 分享的类型分字符串
     * @return 返回唯一字符串
     */
    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private class ShareType{
        public int Circle = SendMessageToWX.Req.WXSceneTimeline;
        public int Favorite = SendMessageToWX.Req.WXSceneFavorite;
        public int Friend = SendMessageToWX.Req.WXSceneSession;

    }


    /**
     *  qq通用分享
     */
    public static void qqCommonShare(Activity activity, String title, String subTitle, String targetUrl, String imgUrl, boolean ifQZone) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  subTitle);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);

        //图片中文处理
        String sub[] = imgUrl.split("/");
        String picName = sub[sub.length-1];
        String imgResult = imgUrl.replace(picName, Uri.encode(picName));
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgResult);

        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, App.getInstance().getString(R.string.app_name));
        if(ifQZone){
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        QQPlatform.getmTencent().shareToQQ(activity, params, qqShareListener);

    }

    /**
     * qq和空间分享图片
     * @param activity
     * @param imgPath
     */
    public static void qqOrQZoneShareImage(Activity activity, String imgPath, boolean ifQZone) {
        Bundle params = new Bundle();
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,imgPath);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, App.getInstance().getString(R.string.app_name));
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        if(ifQZone){
            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        }
        QQPlatform.getmTencent().shareToQQ(activity, params,qqShareListener);

    }

    /**
     * 分享音乐
     */
    private static void qqShareMusic(Activity activity, String title, String subTitle, String targetUrl, String imgUrl, String musicUrl ) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_AUDIO);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  subTitle);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  targetUrl);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_AUDIO_URL, musicUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  App.getInstance().getString(R.string.app_name));
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        QQPlatform.getmTencent().shareToQQ(activity, params, qqShareListener);

    }

    /**
     * 分享应用
     */
    private static void qqShareApp(Activity activity, String title, String subTitle, String imgUrl) {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, subTitle);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  App.getInstance().getString(R.string.app_name));
        QQPlatform.getmTencent().shareToQQ(activity, params,qqShareListener);

    }

    /**
     * 分享到qq空间
     */
    public static void shareToQzone (Activity activity, String title, String subTitle, String targetUrl , String imgUrl) {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, subTitle);
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add(imgUrl);
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL,imgUrlList);
        QQPlatform.getmTencent().shareToQQ(activity, params, qqShareListener);

    }

    /**
     * 分享到qq空间，带图片
     */
//    private static void shareToQzoneWithImageList (Activity activity, String title, String subTitle,ArrayList<String> imgList) {
//        final Bundle params = new Bundle();
//        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
//        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
//        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, subTitle);
////        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
//        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgList);
//        QQPlatform.getmTencent().shareToQQ(activity, params, new QQPlatform.ShareUiListener());
//    }


    /**
     * 模板替换，格式化参数
     * @param urlTemplate
     * @param parms
     * @return
     */
//    public static String getShareUrl(String urlTemplate, Object... parms){
//        String pattern = "\\{(.*?)\\}";
//        urlTemplate = urlTemplate.replaceAll(pattern, "%s");
//        String result = String.format(urlTemplate, parms);
//        return AppConst.getBaseUrl() + result;
//    }



}
