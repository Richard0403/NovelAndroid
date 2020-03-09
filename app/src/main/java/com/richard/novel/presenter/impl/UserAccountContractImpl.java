package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.api.UserService;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.user.UserAccount;
import com.richard.novel.http.entity.user.UserAccountRecord;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.UserAccountContract;
import com.richard.novel.presenter.contract.UserContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class UserAccountContractImpl extends RxPresenter<UserAccountContract.View> implements UserAccountContract.Presenter  {
    @Override
    public void getUserAccount(int type) {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<UserAccount>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.TYPE, type);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<UserAccount> result) {
                super.onSuccess(result);
                mView.setUserAccount(result.getData());
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "queryUserAccount", false);
    }

    @Override
    public void getAccountRecord(int type, int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<UserAccountRecord>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.TYPE, type);
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE, pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<UserAccountRecord> result) {
                super.onSuccess(result);
                mView.setAccountRecord(result.getData());
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "queryUserAccountRecord", true);
    }
}
