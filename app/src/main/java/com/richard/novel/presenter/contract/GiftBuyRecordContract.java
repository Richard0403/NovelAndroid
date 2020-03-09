package com.richard.novel.presenter.contract;

import com.richard.novel.http.entity.user.GiftBuyRecord;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.presenter.BaseContract;

import java.util.List;

/**
 * Created by XiaoU on 2018/9/26.
 */

public interface GiftBuyRecordContract {
    interface View extends BaseContract.BaseView{
        void setGiftBuyRecord(List<GiftBuyRecord> bookCategories);
        void onAddAddr();
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void loadGiftBuyRecord(int pageNo, int pageSize);
        void addAddr(long id, String name, String phone, String addr);
    }
}
