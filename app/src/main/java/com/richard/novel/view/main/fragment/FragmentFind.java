package com.richard.novel.view.main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.utils.UIUtil;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.presenter.contract.FindContract;
import com.richard.novel.presenter.impl.FindContractImpl;
import com.richard.novel.view.base.BaseMvpListFragment;
import com.richard.novel.view.find.adapter.CommentAdapter;
import com.richard.novel.view.find.dialog.EditDialog;
import com.richard.novel.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 发现页
 */
public class FragmentFind extends BaseMvpListFragment<FindContract.Presenter> implements FindContract.View {
    @BindView(R.id.sp_refresh)
    SwipeRefreshLayout sp_refresh;
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.empty)
    EmptyView empty;

    private CommentAdapter cmtAdapter;
    private List<CommentItem> cmtList = new ArrayList<>();
    private EditDialog cmtDialog;

    public static FragmentFind newInstance() {
        return new FragmentFind();
    }

    @Override
    protected View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    protected void initView() {
        sp_refresh.setOnRefreshListener(() -> {
            sp_refresh.setRefreshing(false);
            onRefresh();
        });
    }

    @Override
    public void showError() {

    }

    @Override
    public void setComment(List<CommentItem> result) {
        if(getPageNo() == 0 && !result.isEmpty()){
            cmtList.clear();
        }
        if (!result.isEmpty()) {
            UIUtil.goneViews(empty);
            cmtList.addAll(result);
        }
        setReqListSuccess(result);
    }

    @Override
    public void onRefresh() {
        resetPageNo();
        reqList(getPageNo(),getPageSize());
        if(cmtDialog!=null && cmtDialog.isShowing()){
            cmtDialog.dismiss();
        }
    }

    @Override
    protected FindContract.Presenter bindPresenter() {
        return new FindContractImpl();
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        cmtAdapter = new CommentAdapter(cmtList);
        return cmtAdapter;
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.getComments(pageNo,pageSize);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_content;
    }

    @Override
    protected void listEmpty() {
        UIUtil.showViews(empty);
    }

    @OnClick(R.id.rl_send)
    protected void sendCmt(){
        if(cmtDialog == null){
            cmtDialog = new EditDialog(getContext(), "说点啥吧...",5, content -> {
                mPresenter.sendCmt(content, null, null);
            });
        }
        cmtDialog.show();
    }

}
