package com.richard.novel.http.loading;

import android.content.Context;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by liukun on 16/3/10.
 */
public class ProgressObserver<T> implements ProgressCancelListener, Observer<T> {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    private Disposable disposable;


    /**
     * 设置网络进度条的Subscriber
     * @param mSubscriberOnNextListener
     * @param context
     * @param canclale 是否可以取消订阅，默认不可取消订阅
     */
    public ProgressObserver(SubscriberOnNextListener mSubscriberOnNextListener, Context context, boolean canclale) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this, canclale);
    }


    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
//        LogUtil.setLog("ProgressObserver",e.printStackTrace());
        if (e instanceof SocketTimeoutException) {
//            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
//            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(context, "服务器异常，请稍后重试", Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError(e);
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
//        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onComplete();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onSubscribe(d);
        }
        showProgressDialog();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onCancel();
        }
    }
}