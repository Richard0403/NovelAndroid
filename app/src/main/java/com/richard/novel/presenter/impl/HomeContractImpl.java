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
import com.richard.novel.view.base.BaseActivity;
import com.richard.novel.view.main.dialog.UpdateTipDialog;
import com.richard.novel.view.main.fragment.FragmentHome;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class HomeContractImpl  extends RxPresenter<HomeContract.View> implements HomeContract.Presenter  {
    Fragment mFragment;
    public HomeContractImpl(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void loadBookCategory() {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookCategory>>() {
            @Override
            public String createJson() {
                return super.createJson();
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
            @Override
            protected void onSuccess(ListEntity<BookCategory> result) {
                super.onSuccess(result);
                mView.setBookCategory(result.getData());
            }
        };
        httpRequest.start(HomeService.class, "queryCategory",true);
    }


    @Override
    public void checkUpdate() {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<Version>>() {
            @Override
            public String createJson() {
                Map<String,Object> map = new HashMap<>();
                return new Gson().toJson(map);
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
            @Override
            protected void onSuccess(ObjEntity<Version> result) {
                super.onSuccess(result);
                Version version = result.getData();
                PackageManager packageManager = App.getInstance().getPackageManager();
                try {
                    PackageInfo packInfo = packageManager.getPackageInfo(App.getInstance().getPackageName(), 0);
                    if (version!=null && version.getVersionCode() > packInfo.versionCode) {
                        UpdateTipDialog tipDialog = new UpdateTipDialog((BaseActivity) mFragment.getActivity(),version);
                        tipDialog.show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        httpRequest.start(HomeService.class, "getVersion");
    }
}
