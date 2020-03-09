package com.richard.novel.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by James
 * Date 2017/12/23.
 * description
 */

public abstract class BaseFragment extends Fragment {

    protected Unbinder mUnbinder;

    protected abstract View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    protected abstract void initData();
    protected abstract void initView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = getLayout(inflater, container, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);

        initView();

        initData();

        return view;
    }

    @Override
    public void onDestroyView() {
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
