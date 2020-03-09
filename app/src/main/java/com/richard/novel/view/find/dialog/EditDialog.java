package com.richard.novel.view.find.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.richard.novel.R;
import com.richard.novel.common.utils.ScreenUtils;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.common.utils.ToastUtil;

/**
 * Created by Richard on 2017/2/25.
 * 通用输入框
 */

public class EditDialog extends Dialog {
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.iv_send)
    ImageView iv_send;

    private Context mContext;
    private int winW;
    private OnViewClick viewClick;
    private String content;
    private int minLines;


    /**
     * @param context
     */
    public EditDialog(Context context, String content, int minLines, OnViewClick viewClick) {
        super(context, R.style.CommentDialog);
        this.mContext = context;
        this.content = content;
        this.viewClick = viewClick;
        this.minLines = minLines;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_edit);
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
            et_content.setHint(content);
        }
        et_content.setMinLines(minLines);
        iv_send.setOnClickListener(view -> {
            if(!StringUtils.isEmpty(et_content.getText().toString())){
                viewClick.onConfirm(et_content.getText().toString());
            }else{
                ToastUtil.showSingleToast("写点什么吧");
            }
        });
    }

    public void setSize() {
        winW = ScreenUtils.getScreenWidth();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = winW / 10 * 9;
//        p.height = (int) (winW / 10 * 8 * 1.2);
        getWindow().setAttributes(p);
        setCancelable(true);
        getWindow().setGravity(Gravity.CENTER);
    }

    public interface OnViewClick{
        void onConfirm(String content);
    }

}


