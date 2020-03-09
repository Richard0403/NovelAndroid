package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface PersonContract {
    interface View extends BaseContract.BaseView{
        void updateSuccess();
        void setReadTime(long readTime);
        void setUserScore(int score);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void updateUser(String name, String avatar, int sex);
        void getReadTime();
        void getUserScore();
    }
}
