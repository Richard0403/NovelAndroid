package com.richard.novel.http.loading;

/**
 * Created by Administrator on 2017/3/18.
 */


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.richard.novel.R;


public class LoadingDialog extends Dialog {
    private Context context = null;
    private static LoadingDialog customProgressDialog = null;

    public LoadingDialog(Context context){
        super(context,  R.style.CustomDialog);
        this.context = context;
    }


    public static LoadingDialog createDialog(Context context){
        customProgressDialog = new LoadingDialog(context);
        customProgressDialog.setContentView(R.layout.dialog_loading);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setMessage("加载中...");
        return customProgressDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus){

        if (customProgressDialog == null){
            return;
        }

        ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     *
     * [Summary]
     *       setTitile 标题
     * @param strTitle
     * @return
     *
     */
    public LoadingDialog setTitile(String strTitle){
        return customProgressDialog;
    }

    /**
     *
     * [Summary]
     *       setMessage 提示内容
     * @param strMessage
     * @return
     *
     */
    public LoadingDialog setMessage(String strMessage){
        TextView tvMsg = (TextView)customProgressDialog.findViewById(R.id.id_tv_loadingmsg);

        if (tvMsg != null){
            tvMsg.setText(strMessage);
        }

        return customProgressDialog;
    }
}