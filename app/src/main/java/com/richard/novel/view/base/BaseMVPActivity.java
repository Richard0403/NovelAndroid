package com.richard.novel.view.base;

import com.richard.novel.presenter.BaseContract;

/**
 * Created by XiaoU on 2018/9/10.
 */

public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends BaseActivity{
    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void initData() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter){
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
