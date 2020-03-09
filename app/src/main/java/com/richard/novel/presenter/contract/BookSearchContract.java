package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface BookSearchContract {
    interface View extends BaseContract.BaseView{
        void setSearchBook(List<BookInfo> bookList);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void searchBook(int pageNo, int pageSize, String key);
    }
}
