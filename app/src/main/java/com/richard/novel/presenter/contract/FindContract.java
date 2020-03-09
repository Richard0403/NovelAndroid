package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface FindContract {
    interface View extends BaseContract.BaseView{
        void setComment(List<CommentItem> bookShelves);
        void onRefresh();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getComments(int pageNo, int pageSize);

        void sendCmt(String content, Long toUserId, Long toCommentId);

        void deleteCmt(Long id);
    }
}
