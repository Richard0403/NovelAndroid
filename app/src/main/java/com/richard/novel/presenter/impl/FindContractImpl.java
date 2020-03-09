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
import com.richard.novel.http.entity.user.Comment;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.BookShelfContract;
import com.richard.novel.presenter.contract.FindContract;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * Created by XiaoU on 2018/9/28.
 */

public class FindContractImpl extends RxPresenter<FindContract.View> implements FindContract.Presenter  {

    @Override
    public void getComments(int pageNo, int pageSize) {
        HttpRequest httpRequest = new HttpRequest<ListEntity<CommentItem>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.CMT_TYPE, "words");
                map.put(AppConstant.Parm.RESOURCE_ID, "");
                map.put(AppConstant.Parm.PAGE_NO, pageNo);
                map.put(AppConstant.Parm.PAGE_SIZE,pageSize);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ListEntity<CommentItem> result) {
                super.onSuccess(result);
                mView.setComment(result.getData());
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "queryComments",true);
    }

    @Override
    public void sendCmt(String content, Long toUserId, Long toCommentId) {

        HttpRequest httpRequest = new HttpRequest<ObjEntity<Comment>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.CMT_TYPE, "words");
                map.put(AppConstant.Parm.RESOURCE_ID, "");
                map.put(AppConstant.Parm.TO_USER_ID, toUserId);
                map.put(AppConstant.Parm.TO_CMT_ID,toCommentId);
                map.put(AppConstant.Parm.CONTENT,content);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<Comment> result) {
                super.onSuccess(result);
                mView.onRefresh();
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "addCmt",true);
    }

    @Override
    public void deleteCmt(Long id) {
        HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.ID, id);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(BaseEntity result) {
                super.onSuccess(result);
                mView.onRefresh();
            }
            @Override
            protected void onObserved(Disposable disposable) {
                addDisposable(disposable);
            }
        };
        httpRequest.start(HomeService.class, "deleteCmt",true);
    }
}
