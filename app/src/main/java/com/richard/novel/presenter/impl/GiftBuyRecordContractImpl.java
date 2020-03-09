package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.UserService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.user.GiftBuyRecord;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.GiftBuyRecordContract;
import com.richard.novel.presenter.contract.GiftListContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class GiftBuyRecordContractImpl extends RxPresenter<GiftBuyRecordContract.View> implements GiftBuyRecordContract.Presenter  {

    @Override
    public void loadGiftBuyRecord(int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<GiftBuyRecord>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE,pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<GiftBuyRecord> result) {
                super.onSuccess(result);
                mView.setGiftBuyRecord(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "queryGiftBuyRecord",true);
    }

    @Override
    public void addAddr(long id, String name, String phone, String addr) {
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.ID, id);
                map.put(AppConstant.Parm.NAME, name);
                map.put(AppConstant.Parm.PHONE, phone);
                map.put(AppConstant.Parm.ADDR, addr);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                ToastUtil.showSingleToast("填写成功");
                mView.onAddAddr();
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "addGiftAddr",true);
    }
}
