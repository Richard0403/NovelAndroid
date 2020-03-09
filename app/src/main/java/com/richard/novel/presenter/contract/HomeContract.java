package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.BaseContract;
import com.richard.novel.http.entity.book.BookInfo;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface HomeContract{
    interface View extends BaseContract.BaseView{
        void setBookCategory(List<BookCategory> bookCategories);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void checkUpdate();
        void loadBookCategory();
    }
}
