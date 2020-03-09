package com.richard.novel.presenter.impl;

import android.widget.Toast;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.BookDetailContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class BookDetailImpl extends RxPresenter<BookDetailContract.View> implements BookDetailContract.Presenter  {
    @Override
    public void getBookDetail(long bookId) {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<BookInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.BOOK_ID, bookId);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<BookInfo> result) {
                super.onSuccess(result);
                mView.setBookDetail(result.getData());
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "queryBookDetail", true);
    }

    @Override
    public void addShelf(BookInfo bookInfo) {
        HttpRequest httpRequest = new HttpRequest() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.BOOK_ID, bookInfo.getId());
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                ToastUtil.showSingleToast("添加成功");
                EventBus.getDefault().post(new EventMsg(EventMsg.CODE_SHELF));
                mView.onAddShelfSuccess();
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "addShelfBook",true);
    }

}
