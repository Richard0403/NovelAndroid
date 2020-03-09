package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface ClassifyContract {
    interface View extends BaseContract.BaseView{
        void setHotBooks(List<BookInfo> hotBooks);
        void setNewBooks(List<BookInfo> newBooks);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadHotBooks(int pageNo, int pageSize, long categoryCode);
        void loadNewBooks(int pageNo, int pageSize, long categoryCode);
    }
}
