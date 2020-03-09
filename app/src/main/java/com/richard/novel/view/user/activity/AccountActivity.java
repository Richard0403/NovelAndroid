package com.richard.novel.view.user.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.common.utils.UIUtil;
import com.richard.novel.http.entity.user.UserAccount;
import com.richard.novel.http.entity.user.UserAccountRecord;
import com.richard.novel.presenter.contract.UserAccountContract;
import com.richard.novel.presenter.impl.UserAccountContractImpl;
import com.richard.novel.view.base.BaseMvpListActivity;
import com.richard.novel.view.main.dialog.ConfirmDialog;
import com.richard.novel.view.user.adapter.UserAccountRecordAdapter;
import com.richard.novel.widget.EmptyView;
import com.richard.novel.widget.ToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AccountActivity extends BaseMvpListActivity<UserAccountContract.Presenter> implements UserAccountContract.View {
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.toolbar)
    ToolBar toolbar;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_btn_1)
    TextView tv_btn_1;
    @BindView(R.id.tv_btn_2)
    TextView tv_btn_2;

    private int accountType;

    private UserAccountRecordAdapter recordAdapter;
    private List<UserAccountRecord> accountRecords = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_account;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        accountType = getIntent().getIntExtra(AppConstant.Extra.EXTRA_ACCOUNT_TYPE, UserAccount.TYPE_SCORE);
        if(accountType == UserAccount.TYPE_SCORE){

        }
        toolbar.setRightOnClickListener(v -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(mContext, getString(R.string.score_rule), null);
            confirmDialog.show();
        });
    }

    @Override
    protected void initData() {
        super.initData();

        mPresenter.getUserAccount(accountType);
    }

    @Override
    public void showError() {

    }

    @Override
    protected UserAccountContract.Presenter bindPresenter() {
        return new UserAccountContractImpl();
    }

    @Override
    public void setUserAccount(UserAccount account) {
        tv_account.setText(StringUtils.parseMoney(account.getAmount()));
    }

    @Override
    public void setAccountRecord(List<UserAccountRecord> recordList) {
        if(getPageNo() == 0){
            accountRecords.clear();
        }
        if (!recordList.isEmpty()) {
            accountRecords.addAll(recordList);
            empty.setVisibility(View.GONE);
        }
        setReqListSuccess(recordList);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_content;
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        recordAdapter = new UserAccountRecordAdapter(accountRecords, accountType);
        return recordAdapter;
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.getAccountRecord(accountType, pageNo, pageSize);
    }

    @Override
    protected void listEmpty() {
        UIUtil.showViews(empty);
    }

    @OnClick(R.id.tv_btn_2)
    protected void deposit(){
        GiftListActivity.start(mContext, GiftListActivity.class);
    }

    public static void start(Context context, int accountType){
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra(AppConstant.Extra.EXTRA_ACCOUNT_TYPE, accountType);
        context.startActivity(intent);
    }
}
