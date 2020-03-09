package com.richard.novel.view.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.richard.novel.R;
import com.richard.novel.common.utils.ScreenUtils;
import com.richard.novel.common.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Richard on 2017/2/25.
 * 通用提示框
 */

public class ConfirmDialog extends Dialog {
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cancle)
    TextView tv_cancle;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;


    private Context mContext;
    private int winW;
    private OnViewClick viewClick;
    private String content;


    /**
     * @param context
     */
    public ConfirmDialog(Context context, String content, OnViewClick viewClick) {
        super(context, R.style.CommentDialog);
        this.mContext = context;
        this.content = content;
        this.viewClick = viewClick;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);
        ButterKnife.bind(this);
        initView();
        setSize();
    }

    @Override
    public void show() {
        super.show();
    }




    private void initView() {
        if(!StringUtils.isEmpty(content)){
            tv_content.setText(content);
        }
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(viewClick!=null){
                    viewClick.onCancel();
                }
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(viewClick!=null){
                    viewClick.onConfirm();
                }
            }
        });
    }

    public void setSize() {
        winW = ScreenUtils.getScreenWidth();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 8;
//        p.height = (int) (winW / 10 * 8 * 1.2);
        getWindow().setAttributes(p);
        setCancelable(true);
    }

    public interface OnViewClick{
        void onConfirm();
        void onCancel();
    }

    public void setCancelText(String cancelText){
        tv_cancle.setText(cancelText);
    }
    public void setConfirmText(String confirmText){
        tv_confirm.setText(confirmText);
    }


}


