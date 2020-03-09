package com.richard.novel.presenter.impl;

import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.ClassifyContract;
import com.richard.novel.presenter.contract.RecBookContract;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class ClassifyContractImpl extends RxPresenter<ClassifyContract.View> implements ClassifyContract.Presenter  {
    Fragment mFragment;
    public ClassifyContractImpl(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void loadHotBooks(int pageNo, int pageSize, long categoryCode) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.CATEGORY, categoryCode);
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE,pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<BookInfo> result) {
                super.onSuccess(result);
                mView.setHotBooks(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "queryHotBooks",false);
    }


    @Override
    public void loadNewBooks(int pageNo, int pageSize, long categoryCode) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.CATEGORY, categoryCode);
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE,pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<BookInfo> result) {
                super.onSuccess(result);
                mView.setNewBooks(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "queryNewBooks",false);
    }
}
