package com.richard.novel.view.home.banner;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.richard.novel.R;
import com.richard.novel.common.base.App;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.http.entity.home.BannerInfo;

/**
 * Created by James
 * Date 2018/6/12.
 * description
 */

public class ImageHolderView extends Holder<BannerInfo> {

    private ImageView mImageView;
    private Activity mActivity;
    public ImageHolderView(View itemView, Activity activity){
        super(itemView);
        this.mActivity = activity;
    }

    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.iv_banner);
    }

    @Override
    public void updateUI(BannerInfo data) {

        ImageLoader.getInstance().displayImage(data.getCover(), App.getInstance(), mImageView);
        mImageView.setOnClickListener(v -> {
            try {
                PageJumpUtils.jumpToPage(mActivity, data.getType(), data.getResourceId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }



}
