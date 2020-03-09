package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.user.InviteRecord;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface InviteContract {
    interface View extends BaseContract.BaseView{
        void setInviteRecord(List<InviteRecord> inviteRecord);
        void setInviteCount(int count);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getInviteRecord(int pageNo, int pageSize);
        void getInviteCount();
        void writeCode(String code);
    }
}
