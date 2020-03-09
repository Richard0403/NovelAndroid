package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.cache.UserPrefer;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.api.UserService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.http.entity.user.ReadTimeSummary;
import com.richard.novel.http.entity.user.UserAccount;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.HomeContract;
import com.richard.novel.presenter.contract.PersonContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class PersonContractImpl extends RxPresenter<PersonContract.View> implements PersonContract.Presenter  {

    @Override
    public void updateUser(String name, String avatar, int sex) {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<UserInfo.UserBean>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.NAME, name);
                map.put(AppConstant.Parm.HEADER, avatar);
                map.put(AppConstant.Parm.SEX, sex);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<UserInfo.UserBean> result) {
                super.onSuccess(result);
                AppCache.getUserInfo().setUser(result.getData());
                UserPrefer.saveUserInfo(AppCache.getUserInfo());
                mView.updateSuccess();
            }
            @Override
            protected void onObserved(Disposable disposable) {
            }
        };
        httpRequest.start(UserService.class, "updateUser",true);
    }

    @Override
    public void getReadTime() {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<ReadTimeSummary>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.TIME, System.currentTimeMillis());
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<ReadTimeSummary> result) {
                super.onSuccess(result);
                ReadTimeSummary timeSummary = result.getData();
                mView.setReadTime(timeSummary == null? 0: result.getData().getReadTime());
            }
            @Override
            protected void onObserved(Disposable disposable) {
            }
        };
        httpRequest.start(UserService.class, "queryReadTime");
    }

    @Override
    public void getUserScore() {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<UserAccount>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.TYPE, UserAccount.TYPE_SCORE);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<UserAccount> result) {
                super.onSuccess(result);
                UserAccount account = result.getData();
                mView.setUserScore(account == null? 0 : result.getData().getAmount());
            }
            @Override
            protected void onObserved(Disposable disposable) {
            }
        };
        httpRequest.start(UserService.class, "queryUserAccount");
    }
}
