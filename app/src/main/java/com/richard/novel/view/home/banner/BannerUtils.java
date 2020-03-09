package com.richard.novel.view.home.banner;

import android.app.Activity;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.richard.novel.R;
import com.richard.novel.http.entity.home.BannerInfo;

import java.util.List;

/**
 * Created by James
 * Date 2018/6/12.
 * description
 */

public class BannerUtils {

    public static void setPages(ConvenientBanner banner, Activity activity, List<BannerInfo> bannerInfos) {
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new ImageHolderView(itemView,activity);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner;
            }
        }, bannerInfos).setPageIndicator(new int[]{R.mipmap.icon_dot_false, R.mipmap.icon_dot_true})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        banner.setCanLoop(bannerInfos.size() > 1);

    }
}
