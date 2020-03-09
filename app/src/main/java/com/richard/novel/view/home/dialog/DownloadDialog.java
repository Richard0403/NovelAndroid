package com.richard.novel.view.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richard.novel.R;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.db.BoxStoreHelper;
import com.richard.novel.common.utils.ScreenUtils;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.http.SingleRequest;
import com.richard.novel.http.entity.book.BookChapterContent;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.yuyh.library.imgsel.utils.LogUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Richard on 2017/2/25.
 * 缓存下载框
 */

public class DownloadDialog extends Dialog {
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cancle)
    TextView tv_cancle;
    @BindView(R.id.pb_download)
    ProgressBar pb_download;



    private Context mContext;
    private int winW;
    private OnViewClick viewClick;

    private List<BookChapterInfo> chapterInfos;
    private Subscription mChapterSub;
    private int loadSize=0;
    /**
     * @param context
     */
    public DownloadDialog(Context context, List<BookChapterInfo> chapterInfos, OnViewClick viewClick) {
        super(context, R.style.CommentDialog);
        this.mContext = context;
        this.viewClick = viewClick;
        this.chapterInfos = chapterInfos;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
        ButterKnife.bind(this);
        initView();
        setSize();
    }

    @Override
    public void show() {
        super.show();
        startDownload();
    }




    private void initView() {

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(viewClick!=null){
                    viewClick.onCancle();
                }
            }
        });
    }

    public void setSize() {
        winW = ScreenUtils.getScreenWidth();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 8;
//        p.height = (int) (winW / 10 * 8 * 1.2);
        getWindow().setAttributes(p);
        setCancelable(false);
    }

    public interface OnViewClick{
        void onConfirm();
        void onCancle();
    }

    public void setCancelText(String cancelText){
        tv_cancle.setText(cancelText);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        //取消上次的任务，防止多次加载
        if (mChapterSub != null) {
            mChapterSub.cancel();
        }
    }

    private void startDownload(){


        List<Single<BookChapterInfo>> chapterSingles = new ArrayList<>();
        int chapterSize = chapterInfos.size();
        for (int i = 0; i < chapterSize; ++i) {
            BookChapterInfo bookChapter = chapterInfos.get(i);

            BookChapterContent localChapter = BookQuery.queryChapterContent(bookChapter.getId());
            if(localChapter == null){
                // 网络中获取数据
                Map<String,Object> map = new HashMap<>();
                map.put("chapterId",bookChapter.getId());
                Single<BookChapterInfo> chapterInfoSingle = SingleRequest.getChapterInfo(map);

                chapterSingles.add(chapterInfoSingle);
            }else{
                //本地已经存在
//                mView.finishChapter();
            }
        }
        loadSize = chapterSingles.size();

        Single.concat(chapterSingles)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<BookChapterInfo>() {
                            int loadedSize;
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

                                loadedSize++;
                                pb_download.setProgress(loadedSize*100/loadSize);
                                tv_content.setText(loadedSize+"/"+loadSize+"章");
                            }

                            @Override
                            public void onError(Throwable t) {
                                //只有第一个加载失败才会调用errorChapter
//                                if (bookChapters.get(0).getTitle().equals(title)) {
//                                    mView.errorChapter();
//                                }
                                LogUtils.e(t);
                            }

                            @Override
                            public void onComplete() {
                                dismiss();
                                viewClick.onConfirm();
                            }
                        }
                );
    }


}


