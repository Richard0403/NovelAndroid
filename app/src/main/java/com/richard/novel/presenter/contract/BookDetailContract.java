package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.BaseContract;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface BookDetailContract {
    interface View extends BaseContract.BaseView{
        void setBookDetail(BookInfo bookInfo);
        void onAddShelfSuccess();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getBookDetail(long bookId);
        void addShelf(BookInfo bookInfo);
    }
}
