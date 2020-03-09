package com.richard.novel.common.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.richard.novel.R;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class ImageLoader {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private static class ImageLoaderHolder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }

    private ImageLoader() {

    }

    public static final ImageLoader getInstance() {
        return ImageLoaderHolder.INSTANCE;
    }

    //直接加载网络图片
    public void displayImage(String url, Context context, ImageView imageView, int placeholderImage, int failureImage) {
        Glide.with(context)
                .load(url)
//                .placeholder(placeholderImage)
                .error(failureImage)
//                .centerCrop()
                .into(imageView);
    }

    public void displayImage(String url, Context context, ImageView imageView) {
        displayImage(url, context, imageView, R.mipmap.icon_default_header, R.mipmap.icon_default_header);
    }

    //直接加载网络图片相册
    public void displayAlbumImage(String url, Context context, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.icon_default_header)
                .error(R.mipmap.icon_default_header)
                .fitCenter()
                .into(imageView);
    }


    //加载SD卡图片
    public void displayAlbumImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .fitCenter()
                .into(imageView);

    }

    //加载SD卡图片
    public void displayImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .centerCrop()
                .into(imageView);

    }

    //加载SD卡图片并设置大小
    public void displayImage(Context context, File file, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(file)
                .override(width, height)
                .centerCrop()
                .into(imageView);

    }

    //加载网络图片并设置大小
    public void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .override(width, height)
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片
    public void displayImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .into(imageView);
    }

    //加载drawable图片显示为圆形图片
    public void displayCricleImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    //加载网络图片显示为圆形图片
    public void displayCricleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .error(R.mipmap.icon_default_header)
                .placeholder(R.mipmap.icon_default_header)
                .into(imageView);
    }

    //加载SD卡图片显示为圆形图片
    public void displayCricleImage(Context context, File file, ImageView imageView) {
        Glide.with(context)
                .load(file)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    //加载网络圆角图片
    public void displayRoundImage(Context context, String url, ImageView imageView, int placeholderImage, int failureImage) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 15, 9))
                .placeholder(placeholderImage)
                .error(failureImage)
                .into(imageView);
    }

    //加载网络圆角图片
    public void displayRoundImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 15, 9))
                .into(imageView);
    }

    //将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }


    public void displayGifImage(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .crossFade()
                .placeholder(R.mipmap.icon_default_header)
                .error(R.mipmap.icon_default_header)
                .into(new GlideDrawableImageViewTarget(imageView, 1));

    }
    public void displayGifImage(Context context, int resId, ImageView imageView){
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .placeholder(R.mipmap.icon_default_header)
                .error(R.mipmap.icon_default_header)
                .into(new GlideDrawableImageViewTarget(imageView, 1));

    }

    //网络图片模糊化
    public void displayBlurImage(String url, Context context, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .bitmapTransform(new BlurTransformation(context, 15))
                .into(imageView);
    }


}