package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface UserContract {
    interface View extends BaseContract.BaseView{
        void setSignIn(UserInfo userInfo);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void signIn(String name, String uid,String header,int sexCode);
    }
}
