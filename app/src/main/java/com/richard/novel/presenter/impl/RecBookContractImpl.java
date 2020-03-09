package com.richard.novel.presenter.impl;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.richard.novel.common.base.App;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.http.entity.home.Version;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.HomeContract;
import com.richard.novel.presenter.contract.RecBookContract;
import com.richard.novel.view.base.BaseActivity;
import com.richard.novel.view.main.dialog.UpdateTipDialog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class RecBookContractImpl extends RxPresenter<RecBookContract.View> implements RecBookContract.Presenter  {
    Fragment mFragment;
    public RecBookContractImpl(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void loadHotBookList(int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
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
    public void loadBanner() {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BannerInfo>>() {
            @Override
            public String createJson() {
                Map<String,Object> map = new HashMap<>();
                map.put("sort",1);
                return new Gson().toJson(map);
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
            @Override
            protected void onSuccess(ListEntity<BannerInfo> result) {
                super.onSuccess(result);
                mView.setBanner(result.getData());
            }
        };
        httpRequest.start(HomeService.class, "getBanners");
    }
}
