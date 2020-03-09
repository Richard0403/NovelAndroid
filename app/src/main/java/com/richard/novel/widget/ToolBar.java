package com.richard.novel.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.richard.novel.R;

/**
 * Created by James
 * Date 2018/6/9.
 * description 带回退标志的ToolBar，右边可以设置按钮
 * 没人生来杰出。-奥格瑞姆·毁灭之锤
 */
public class ToolBar extends FrameLayout {
    public static final int RIGHT_VIEW_TEXT = 2;
    public static final int RIGHT_VIEW_IMAGE = 1;

    private FrameLayout backLayout, rightLayout, fl_root;
    private ImageView backImageView, rightImageView;
    private TextView titleTextView, rightTextView;
    private RelativeLayout viewStub;

    private int backIconRes, rightIconRes, titleColorRes;
    private String title, rightTxt;
    private int rightView;
    private int backgroundRes;

    private boolean isViewStubInflated = false;

    public ToolBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.NewToolBar);
        backIconRes = a.getResourceId(R.styleable.NewToolBar_back_icon, R.mipmap.icon_arrow_left);
        rightIconRes = a.getResourceId(R.styleable.NewToolBar_right_icon, 0);
        titleColorRes = a.getResourceId(R.styleable.NewToolBar_title_color, R.color.txt_deep);
        title = a.getString(R.styleable.NewToolBar_new_tool_bar_title);
        rightTxt = a.getString(R.styleable.NewToolBar_right_text);
        rightView = a.getInt(R.styleable.NewToolBar_right_view, 0);
        backgroundRes = a.getResourceId(R.styleable.NewToolBar_background_color,R.color.white);
        a.recycle();
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_new_tool_bar, this);

        backLayout = findViewById(R.id.new_toolbar_back);
        rightLayout = findViewById(R.id.new_toolbar_right);
        backImageView = findViewById(R.id.new_toolbar_back_img);
        titleTextView = findViewById(R.id.new_toolbar_title);
        viewStub = findViewById(R.id.new_toolbar_stub_view);
        fl_root = findViewById(R.id.fl_root);

        if (!TextUtils.isEmpty(title)) {
            titleTextView.setText(title);
        }

        titleTextView.setTextColor(getContext().getResources().getColor(titleColorRes));
        fl_root.setBackgroundResource(backgroundRes);
        if (rightView != 0) {
            createViewStub();
        }

        backImageView.setImageResource(backIconRes);

        backLayout.setOnClickListener(v ->
                ((Activity) getContext()).finish()
        );
    }

    public void setRightOnClickListener(OnClickListener onClickListener) {
        rightLayout.setOnClickListener(onClickListener);
    }

    public void setTitle(String title) {
        this.title = title;
        titleTextView.setText(title);
    }

    public void setRightTxt(String text){
        this.rightTxt = text;
        this.rightView = RIGHT_VIEW_TEXT;
        createViewStub();
    }

    public void setRightIcon(int resId) {
        rightIconRes = resId;
        this.rightView = RIGHT_VIEW_IMAGE;
        createViewStub();
    }

    public void setBackIcon(int resId) {
        backImageView.setImageResource(resId);
    }

    public void setBackOnClickListener(OnClickListener onClickListener){
        backLayout.setOnClickListener(onClickListener);
    }



    private void createViewStub() {
        if (rightView == RIGHT_VIEW_TEXT) {
            View toolbarTextView = LayoutInflater.from(getContext()).inflate(R.layout.new_tool_bar_textview, null);
            viewStub.removeAllViews();
            viewStub.addView(toolbarTextView);
            if (rightTxt!=null) {
                rightTextView = toolbarTextView.findViewById(R.id.new_toolbar_right_txt);
                rightTextView.setText(rightTxt);
                rightTextView.setTextColor(getContext().getResources().getColor(titleColorRes));
            }

        } else if (rightView == RIGHT_VIEW_IMAGE) {
            View toolbarImageView = LayoutInflater.from(getContext()).inflate(R.layout.new_tool_bar_imageview, null);
            viewStub.removeAllViews();
            viewStub.addView(toolbarImageView);
            if (rightIconRes != 0) {
                rightImageView = toolbarImageView.findViewById(R.id.new_toolbar_right_img);
                rightImageView.setImageResource(rightIconRes);
            }
        }
    }
}
