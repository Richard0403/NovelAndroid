package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.BookDetailContract;
import com.richard.novel.presenter.contract.BookSearchContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class BookSearchImpl extends RxPresenter<BookSearchContract.View> implements BookSearchContract.Presenter  {

    @Override
    public void searchBook(int pageNo, int pageSize, String keywords) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE,pageSize);
                map.put(AppConstant.Parm.KEY_WORDS, keywords);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<BookInfo> result) {
                super.onSuccess(result);
                mView.setSearchBook(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "searchBook");
    }
}
