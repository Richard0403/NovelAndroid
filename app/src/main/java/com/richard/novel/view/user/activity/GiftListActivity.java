package com.richard.novel.view.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.presenter.contract.GiftListContract;
import com.richard.novel.presenter.impl.GiftListContractImpl;
import com.richard.novel.view.base.BaseMvpListActivity;
import com.richard.novel.view.main.dialog.ConfirmDialog;
import com.richard.novel.view.user.adapter.GiftListAdapter;
import com.richard.novel.widget.EmptyView;
import com.richard.novel.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GiftListActivity extends BaseMvpListActivity<GiftListContract.Presenter> implements GiftListContract.View {
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.toolbar)
    ToolBar toolbar;
    @BindView(R.id.empty)
    EmptyView empty;

    private List<GiftInfo> giftInfos = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_gift_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        toolbar.setRightOnClickListener(v -> {
            //兑换记录
            GiftBuyRecordActivity.start(mContext, GiftBuyRecordActivity.class);
        });
    }

    @Override
    public void showError() {

    }

    @Override
    protected GiftListContract.Presenter bindPresenter() {
        return new GiftListContractImpl();
    }

    @Override
    public void setGiftList(List<GiftInfo> giftResult) {
        if(getPageNo() == 0){
            giftInfos.clear();
        }
        if (!giftResult.isEmpty()) {
            giftInfos.addAll(giftResult);
        }
        setReqListSuccess(giftResult);
    }

    @Override
    public void onExchange() {
        ConfirmDialog dialog = new ConfirmDialog(mContext, "兑换成功，快去填写收货地址吧", new ConfirmDialog.OnViewClick() {
            @Override
            public void onConfirm() {
                //去兑换记录 填写收货地址
                GiftBuyRecordActivity.start(mContext, GiftBuyRecordActivity.class);
            }

            @Override
            public void onCancel() {

            }
        });
        dialog.show();
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_content;
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        GiftListAdapter giftListAdapter = new GiftListAdapter(giftInfos);
        giftListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            //兑换
            if(view.getId() == R.id.tv_exchange){
                mPresenter.exchange(giftInfos.get(position));
            }
        });
        return giftListAdapter;
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.loadGiftList(pageNo, pageSize);
    }

    @Override
    protected void listEmpty() {
        empty.setVisibility(View.VISIBLE);
    }
}
