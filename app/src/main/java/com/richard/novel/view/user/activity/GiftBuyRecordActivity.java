package com.richard.novel.view.user.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.http.entity.user.GiftBuyRecord;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.presenter.contract.GiftBuyRecordContract;
import com.richard.novel.presenter.impl.GiftBuyRecordContractImpl;
import com.richard.novel.view.base.BaseMvpListActivity;
import com.richard.novel.view.user.adapter.GiftBuyRecordAdapter;
import com.richard.novel.view.user.dialog.EditGiftAddrDialog;
import com.richard.novel.widget.EmptyView;
import com.richard.novel.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GiftBuyRecordActivity extends BaseMvpListActivity<GiftBuyRecordContract.Presenter> implements GiftBuyRecordContract.View {
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.toolbar)
    ToolBar toolbar;
    @BindView(R.id.empty)
    EmptyView empty;

    private List<GiftBuyRecord> giftBuyRecords = new ArrayList<>();
    private EditGiftAddrDialog addrDialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_gift_buy_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    public void showError() {

    }

    @Override
    protected GiftBuyRecordContract.Presenter bindPresenter() {
        return new GiftBuyRecordContractImpl();
    }


    @Override
    protected RecyclerView getRecyclerView() {
        return clv_content;
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        GiftBuyRecordAdapter recordAdapter = new GiftBuyRecordAdapter(giftBuyRecords);
        recordAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.tv_status && giftBuyRecords.get(position).getOrderStatus() == GiftBuyRecord.ORDER_STATUS_PAYED){
                // 填写收货地址
                if(addrDialog == null){
                    addrDialog = new EditGiftAddrDialog(mContext, (name, phone, addr) -> {
                        mPresenter.addAddr(giftBuyRecords.get(position).getId(), name, phone, addr);
                    });
                }
                addrDialog.show();
            }
        });
        return recordAdapter;
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.loadGiftBuyRecord(pageNo, pageSize);
    }

    @Override
    protected void listEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGiftBuyRecord(List<GiftBuyRecord> giftResult) {
        if(getPageNo() == 0){
            giftBuyRecords.clear();
        }
        if (!giftResult.isEmpty()) {
            giftBuyRecords.addAll(giftResult);
            empty.setVisibility(View.GONE);
        }
        setReqListSuccess(giftResult);
    }

    @Override
    public void onAddAddr() {
        addrDialog.dismiss();
        resetPageNo();
        reqList(getPageNo(), getPageSize());
    }
}
