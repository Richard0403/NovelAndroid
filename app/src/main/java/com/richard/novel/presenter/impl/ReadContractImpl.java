package com.richard.novel.presenter.impl;


import com.google.gson.Gson;
import com.richard.novel.common.base.App;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.db.BoxStoreHelper;
import com.richard.novel.common.db.TimeRecordQuery;
import com.richard.novel.common.utils.NetworkUtils;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.SingleRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.api.UserService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.book.BookChapterContent;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.record.TimeRecord;
import com.richard.novel.presenter.RxPresenter;
import com.richard.novel.presenter.contract.ReadContract;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by newbiechen on 17-5-16.
 */

public class ReadContractImpl extends RxPresenter<ReadContract.View>
        implements ReadContract.Presenter {
    private static final String TAG = "ReadPresenter";

    private Subscription mChapterSub;

    @Override
    public void loadCategory(long bookId) {
        //获取目录，优先本地获取
        List<BookChapterInfo> chapterInfoList = BookQuery.queryChapterList(bookId);

        if(chapterInfoList!=null && !chapterInfoList.isEmpty()) {
            mView.showCategory(chapterInfoList);
        }else{
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
                    BoxStoreHelper.getInstance().put(BookChapterInfo.class, result.getData());
                    mView.showCategory(result.getData());
                }
                @Override
                protected void onObserved(Disposable disposable) {
                    addDisposable(disposable);
                }
            };
            httpRequest.start(HomeService.class, "queryChapterList", true);
        }
    }

    @Override
    public void loadChapter(long bookId, List<BookChapterInfo> bookChapters) {
        // 获取章节内容
        int size = bookChapters.size();

        //取消上次的任务，防止多次加载
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }

        List<Single<BookChapterInfo>> chapterInfos = new ArrayList<>(bookChapters.size());

        // 将要下载章节，转换成网络请求。
        for (int i = 0; i < size; ++i) {
            BookChapterInfo bookChapter = bookChapters.get(i);

            BookChapterContent localChapter = BookQuery.queryChapterContent(bookChapter.getId());
            if(localChapter == null){
                // 网络中获取数据
                Map<String,Object> map = new HashMap<>();
                map.put("chapterId",bookChapter.getId());
                Single<BookChapterInfo> chapterInfoSingle = SingleRequest.getChapterInfo(map);

                chapterInfos.add(chapterInfoSingle);
            }else{
                //本地已经存在
//                mView.finishChapter();
            }
        }

        Single.concat(chapterInfos)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<BookChapterInfo>() {

                            @Override
                            public void onSubscribe(Subscription s) {
                                s.request(Integer.MAX_VALUE);
                                mChapterSub = s;
                            }

                            @Override
                            public void onNext(BookChapterInfo chapterInfo) {
                                //存储数据
                                BookChapterContent chapterContent = new BookChapterContent();
                                chapterContent.parse(chapterInfo);
                                BoxStoreHelper.getInstance().put(BookChapterContent.class, chapterContent);

                                //将获取到的数据进行存储
                            }

                            @Override
                            public void onError(Throwable t) {
                                //只有第一个加载失败才会调用errorChapter
//                                if (bookChapters.get(0).getTitle().equals(title)) {
//                                    mView.errorChapter();
//                                }
                                LogUtils.e(t);
                                mView.finishChapter();
                            }

                            @Override
                            public void onComplete() {
                                mView.finishChapter();
                            }
                        }
                );
    }

    @Override
    public void updateShelf(long bookId, int chapterPos, int pagePos) {
        BookShelf bookShelf = BookQuery.queryBookShelf(bookId);
        if(bookShelf!=null){
            HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
                @Override
                public String createJson() {
                    Map<String, Object> map = new HashMap();
                    map.put(AppConstant.Parm.SHELF_ID, bookShelf.getId());
                    map.put(AppConstant.Parm.CHAPTER_POS, chapterPos);
                    map.put(AppConstant.Parm.PAGE_POS, pagePos);
                    return new Gson().toJson(map);
                }

                @Override
                protected void onSuccess(BaseEntity result) {
                    super.onSuccess(result);
                    bookShelf.setChapterPos(chapterPos);
                    bookShelf.setPagePos(pagePos);
                    BoxStoreHelper.getInstance().put(BookShelf.class, bookShelf);
                    EventBus.getDefault().post(new EventMsg(EventMsg.CODE_SHELF));
                }
                @Override
                protected void onObserved(Disposable disposable) {
                }
            };
            httpRequest.start(HomeService.class, "updateShelfBook");
        }
    }

    @Override
    public void uploadReadTime() {
        //TODO 上传时间
        List<TimeRecord> recordList = TimeRecordQuery.queryNotUploadRecord();
        if(!recordList.isEmpty() && NetworkUtils.isAvailable(App.getInstance())){
            HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
                @Override
                public String createJson() {
                    Map<String, Object> map = new HashMap();
                    map.put(AppConstant.Parm.READ_RECORDS, new Gson().toJson(recordList));
                    return new Gson().toJson(map);
                }

                @Override
                protected void onSuccess(BaseEntity result) {
                    super.onSuccess(result);
                    for(int i = 0;i<recordList.size();i++){
                        recordList.get(i).setUploaded(true);
                    }
                    BoxStoreHelper.getInstance().put(TimeRecord.class, recordList);
                    EventBus.getDefault().post(new EventMsg(EventMsg.CODE_READ_TIME));
                    EventBus.getDefault().post(new EventMsg(EventMsg.CODE_SCORE));
                }
                @Override
                protected void onObserved(Disposable disposable) {

                }
            };
            httpRequest.start(UserService.class, "uploadReadTime");
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }
    }

}
