package com.richard.novel.view.user.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.utils.ShareUtils;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.CodeFilter;
import com.richard.novel.http.entity.user.InviteRecord;
import com.richard.novel.presenter.contract.InviteContract;
import com.richard.novel.presenter.impl.InviteContractImpl;
import com.richard.novel.view.base.BaseMvpListActivity;
import com.richard.novel.view.find.dialog.EditDialog;
import com.richard.novel.view.user.adapter.InviteRecordAdapter;
import com.richard.novel.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteActivity extends BaseMvpListActivity<InviteContract.Presenter> implements InviteContract.View {
    @BindView(R.id.tv_invite_code)
    TextView tv_invite_code;
    @BindView(R.id.tv_invite_count)
    TextView tv_invite_count;
    @BindView(R.id.clv_content)
    RecyclerView clv_content;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.tv_write_code)
    TextView tv_write_code;

    private List<InviteRecord> mRecordList = new ArrayList<>();
    private EditDialog editDialog;
    @Override
    protected int getLayout() {
        return R.layout.activity_invite;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setGuideView();

        checkInviteCode();
    }

    private void checkInviteCode() {
        String inviteCode =  AppCache.getUserInfo().getUser().getInviteCode();
        if(StringUtils.isEmpty(inviteCode)){
            ToastUtil.showSingleToast("重新登录获取邀请码");
            CodeFilter.filter(CodeFilter.CODE_SING_OUT_DATE, "重新登录获取邀请码");
        }else{
            String code = "我的邀请码:<font color='#ffa248'>"+ inviteCode +"</font>(复制)";
            tv_invite_code.setText(Html.fromHtml(code));
        }
    }

    @Override
    public void showError() {

    }

    private void setGuideView() {
        NewbieGuide.with(this)
                .setLabel("guide1")
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(tv_write_code)
                        .setLayoutRes(R.layout.layout_guide_invite))
                .show();
    }


    @Override
    protected InviteContract.Presenter bindPresenter() {
        return new InviteContractImpl();
    }

    @Override
    public void setInviteRecord(List<InviteRecord> inviteRecord) {
        if(getPageNo() == 0){
            mRecordList.clear();
        }
        if (!inviteRecord.isEmpty()) {
            mRecordList.addAll(inviteRecord);
            empty.setVisibility(View.GONE);
        }
        setReqListSuccess(inviteRecord);
    }

    @Override
    public void setInviteCount(int count) {
        tv_invite_count.setText("我已邀请"+count+"人");
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_content;
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        InviteRecordAdapter recordAdapter = new InviteRecordAdapter(mRecordList);
        return recordAdapter;
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.getInviteRecord(pageNo, pageSize);
    }

    @Override
    protected void listEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.tv_write_code, R.id.tv_invite_code, R.id.tv_invite})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.tv_write_code:
                //填写
                if(editDialog==null){
                    editDialog = new EditDialog(mContext, "填写邀请码", 1, content -> {
                        mPresenter.writeCode(content);
                        editDialog.dismiss();
                    });
                }
                editDialog.show();
                break;
            case R.id.tv_invite_code:
                //复制到粘贴板
                ClipboardManager myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", AppCache.getUserInfo().getUser().getInviteCode());
                myClipboard.setPrimaryClip(myClip);
                ToastUtil.showSingleToast("已复制");
                break;
            case R.id.tv_invite:
                ShareUtils.qqCommonShare(this,
                        "悠然读书--免费无广告",
                        "免费无广告的小说app， 重要的话只说一遍",
                        "http://ifinder.cc","http://qiniu.ifinder.cc/ic_launcher.png", false);
                break;
        }
    }
}
