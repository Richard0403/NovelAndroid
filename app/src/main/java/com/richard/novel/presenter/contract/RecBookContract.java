package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface RecBookContract {
    interface View extends BaseContract.BaseView{
        void setHotBooks(List<BookInfo> hotBooks);
        void setBanner(List<BannerInfo> bannerList);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadHotBookList(int pageNo, int pageSize);
        void loadBanner();
    }
}
