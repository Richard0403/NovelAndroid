package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.user.UserAccount;
import com.richard.novel.http.entity.user.UserAccountRecord;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface UserAccountContract {
    interface View extends BaseContract.BaseView{
        void setUserAccount(UserAccount account);
        void setAccountRecord(List<UserAccountRecord> recordList);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void getUserAccount(int type);
        void getAccountRecord(int type, int pageNo, int pageSize);
    }
}
