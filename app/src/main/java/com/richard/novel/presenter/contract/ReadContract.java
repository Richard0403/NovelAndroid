package com.richard.novel.presenter.contract;


import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by newbiechen on 17-5-16.
 */

public interface ReadContract extends BaseContract {
    interface View extends BaseContract.BaseView {
        void showCategory(List<BookChapterInfo> bookChapterList);
        void finishChapter();
        void errorChapter();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadCategory(long bookId);
        void loadChapter(long bookId, List<BookChapterInfo> bookChapterList);
        void updateShelf(long bookId, int chapterPos, int pagePos);

        void uploadReadTime();
    }
}
