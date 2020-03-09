package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.db.BoxStoreHelper;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.api.UserService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.user.InviteRecord;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.BookShelfContract;
import com.richard.novel.presenter.contract.InviteContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class InviteContractImpl extends RxPresenter<InviteContract.View> implements InviteContract.Presenter  {
    @Override
    public void getInviteRecord(int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<InviteRecord>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE, pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<InviteRecord> result) {
                super.onSuccess(result);
                mView.setInviteRecord(result.getData());
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "getInviteRecord", true);
    }

    @Override
    public void getInviteCount() {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<Integer>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<Integer> result) {
                super.onSuccess(result);
                mView.setInviteCount(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "getInviteCount");
    }

    @Override
    public void writeCode(String code) {
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.INVITE_CODE, code);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                ToastUtil.showSingleToast("填写成功");
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(UserService.class, "writeInviteCount", true);
    }

}
