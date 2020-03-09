package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface GiftListContract {
    interface View extends BaseContract.BaseView{
        void setGiftList(List<GiftInfo> bookCategories);
        void onExchange();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadGiftList(int pageNo, int pageSize);
        void exchange(GiftInfo giftInfo);
    }
}
