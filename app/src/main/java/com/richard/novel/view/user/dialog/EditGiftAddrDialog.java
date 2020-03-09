package com.richard.novel.view.user.dialog;

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
 * 地址填写页
 */

public class EditGiftAddrDialog extends Dialog {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_addr)
    EditText et_addr;
    @BindView(R.id.iv_send)
    ImageView iv_send;

    private Context mContext;
    private int winW;
    private OnViewClick viewClick;


    /**
     * @param context
     */
    public EditGiftAddrDialog(Context context, OnViewClick viewClick) {
        super(context, R.style.CommentDialog);
        this.mContext = context;
        this.viewClick = viewClick;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_gift_addr);
        ButterKnife.bind(this);
        initView();
        setSize();
    }

    @Override
    public void show() {
        super.show();
    }




    private void initView() {
        iv_send.setOnClickListener(view -> {
            String name = et_name.getText().toString();
            String phone = et_phone.getText().toString();
            String addr = et_addr.getText().toString();
            if(!StringUtils.containsEmpty(name, phone, addr)){
                viewClick.onConfirm(name, phone, addr);
            }else{
                ToastUtil.showSingleToast("参数不能为空");
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
        void onConfirm(String name, String phone, String addr);
    }

}


