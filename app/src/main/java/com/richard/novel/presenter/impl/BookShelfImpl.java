package com.richard.novel.presenter.impl;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.db.BoxStoreHelper;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.BookDetailContract;
import com.richard.novel.presenter.contract.BookShelfContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class BookShelfImpl extends RxPresenter<BookShelfContract.View> implements BookShelfContract.Presenter  {
    @Override
    public void getShelfBooks(int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookShelf>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE, pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<BookShelf> result) {
                super.onSuccess(result);
                mView.setBookShelf(result.getData());
                for(BookShelf bookShelf:result.getData()){
                    if(BookQuery.queryBookShelf(bookShelf.getBookInfo().getId()) == null){
                        BoxStoreHelper.getInstance().put(BookShelf.class, bookShelf);
                        BoxStoreHelper.getInstance().put(BookInfo.class, bookShelf.getBookInfo());
                    }
                }
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "getShelfBooks", false);
    }

    @Override
    public void removeShelf(String... shelfId) {
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                String shelfIds = StringUtils.splitReverse(",", shelfId);
                map.put(AppConstant.Parm.SHELF_IDS, shelfIds);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                EventBus.getDefault().post(new EventMsg(EventMsg.CODE_SHELF));
                for (String id:shelfId){
                    BoxStoreHelper.getInstance().remove(BookQuery.queryBookShelfById(Long.parseLong(id)), BookShelf.class);
                }
            }

            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "removeShelf", true);
    }
}
