package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.HomeContract;
import com.richard.novel.presenter.contract.UserContract;
import com.richard.novel.view.main.activity.MainActivity;
import com.richard.novel.view.main.activity.SignInActivity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class UserContractImpl extends RxPresenter<UserContract.View> implements UserContract.Presenter  {

    @Override
    public void signIn(String name, String uid, String header, int sexCode) {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<UserInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put("name", name);
                map.put("qqOpenId", uid);
                map.put("header", header);
                map.put("sex", sexCode);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<UserInfo> result) {
                super.onSuccess(result);
                mView.setSignIn(result.getData());
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "signIn", true);
    }
}
