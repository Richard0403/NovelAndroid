package com.richard.novel.view.main.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.OnClick;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.richard.novel.R;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.constant.AppConfig;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.user.UserAccount;
import com.richard.novel.presenter.contract.PersonContract;
import com.richard.novel.presenter.impl.PersonContractImpl;
import com.richard.novel.view.base.BaseMVPFragment;

import butterknife.BindView;
import com.richard.novel.view.find.dialog.EditDialog;
import com.richard.novel.view.user.activity.AccountActivity;
import com.richard.novel.view.user.activity.GiftListActivity;
import com.richard.novel.view.user.activity.InviteActivity;
import com.richard.novel.widget.BallsCollisionView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * 首页
 */
public class FragmentPerson extends BaseMVPFragment<PersonContract.Presenter> implements PersonContract.View{
    @BindView(R.id.iv_header)
    ImageView iv_header;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.btv_ball)
    BallsCollisionView btv_ball;
    @BindView(R.id.tv_invite_code)
    TextView tv_invite_code;
    @BindView(R.id.tv_service)
    TextView tv_service;

    private EditDialog cmtDialog;
    private List<BallsCollisionView.BallData> models = new ArrayList<>();

    public static FragmentPerson newInstance() {
        return new FragmentPerson();
    }

    @Override
    protected View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }

    @Override
    protected void initView() {
        tv_service.setText("客服QQ群："+ AppConfig.QQ.SERVICE_QQ);
        setUserInfo();
        setBallView();
    }



    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);

        mPresenter.getReadTime();
        mPresenter.getUserScore();
    }

    @Override
    protected PersonContract.Presenter bindPresenter() {
        return new PersonContractImpl();
    }


    @Override
    public void showError() {

    }

    @OnClick(R.id.tv_name)
    protected void changeName(){
        if(cmtDialog==null){
            cmtDialog = new EditDialog(getContext(),
                    "起个名字吧...",
                    1,
                    content -> {
                        mPresenter.updateUser(content,AppCache.getUserInfo().getUser().getHeader(),AppCache.getUserInfo().getUser().getSex());
                    });
        }
        cmtDialog.show();
    }

    private void setUserInfo(){
        ImageLoader.getInstance().displayCricleImage(getContext(), AppCache.getUserInfo().getUser().getHeader(),iv_header);
        tv_name.setText(AppCache.getUserInfo().getUser().getName());
    }

    private void setBallView() {
        models.add(new BallsCollisionView.BallData(0,"今日阅读\n0"));
        models.add(new BallsCollisionView.BallData(1,"我的积分\n0"));
        models.add(new BallsCollisionView.BallData(2,"积分兑换"));

        btv_ball.setBallData(models);
        btv_ball.setBallViewListener(taskId -> {
            switch (taskId){
                case 0:
                    ToastUtil.showSingleToast("多读多赚积分~");
                    break;
                case 1:
                    AccountActivity.start(getContext(), UserAccount.TYPE_SCORE);
                    break;
                case 2:
                    GiftListActivity.start(getContext(), GiftListActivity.class);
                    break;
            }
        });
    }

    @Override
    public void updateSuccess() {
        setUserInfo();
        if(cmtDialog!=null && cmtDialog.isShowing()){
            cmtDialog.dismiss();
        }
    }

    @Override
    public void setReadTime(long readTime) {
        models.get(0).setContent("今日阅读\n"+(readTime/60)+"min");
        btv_ball.setBallData(models);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void readTimeChange(EventMsg msg) {
        if(msg.getCode() == EventMsg.CODE_READ_TIME){
            mPresenter.getReadTime();
        }
        if(msg.getCode() == EventMsg.CODE_SCORE){
            mPresenter.getUserScore();
        }
    }

    @Override
    public void setUserScore(int score) {
        models.get(1).setContent("我的积分\n"+score/100);
        btv_ball.setBallData(models);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.tv_invite_code,R.id.tv_service})
    protected void OnInvite(View view){
        switch (view.getId()){
            case R.id.tv_service:
                //复制到粘贴板
                ClipboardManager myClipboard = (ClipboardManager)getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", AppConfig.QQ.SERVICE_QQ);
                myClipboard.setPrimaryClip(myClip);
                ToastUtil.showSingleToast("已复制QQ群");
                break;
            case R.id.tv_invite_code:
                InviteActivity.start(getContext(), InviteActivity.class);
                break;
        }

    }


}
