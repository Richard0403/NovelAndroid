package com.richard.novel.http.api;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/10/11.
 */

public class NetQueries {
    public static void queryChapterList(long bookId, ResultListener listener){
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookChapterInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.BOOK_ID, bookId);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<BookChapterInfo> result) {
                super.onSuccess(result);
                listener.onSuccess(result.getData());
            }

            @Override
            protected void onFail(int code, String msg, BaseEntity entity) {
                super.onFail(code, msg, entity);
                listener.onFail();
            }

            @Override
            protected void onObserved(Disposable disposable) {

            }
        };
        httpRequest.start(HomeService.class, "queryChapterList", true);
    }
}
