package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.api.UserService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.user.Comment;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.FindContract;
import com.richard.novel.presenter.contract.GiftListContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class GiftListContractImpl extends RxPresenter<GiftListContract.View> implements GiftListContract.Presenter  {

    @Override
    public void loadGiftList(int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<GiftInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE,pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<GiftInfo> result) {
                super.onSuccess(result);
                mView.setGiftList(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "queryGiftList",true);
    }

    @Override
    public void exchange(GiftInfo giftInfo) {
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.ID, giftInfo.getId());
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                ToastUtil.showSingleToast("兑换成功");
                mView.onExchange();
                EventBus.getDefault().post(new EventMsg(EventMsg.CODE_SCORE));
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "exchangeGift",true);
    }
}
