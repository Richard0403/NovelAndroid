package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface BookShelfContract {
    interface View extends BaseContract.BaseView{
        void setBookShelf(List<BookShelf> bookShelves);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getShelfBooks(int pageNo,int pageSize);
        void removeShelf(String... shelfId);
    }
}
