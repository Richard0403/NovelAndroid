package com.richard.novel.http.loading;

import io.reactivex.disposables.Disposable;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(Throwable e);
    void onComplete();
    void onCancel();
    void onSubscribe(Disposable disposable);
}
