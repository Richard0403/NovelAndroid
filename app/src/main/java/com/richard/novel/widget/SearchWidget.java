package com.richard.novel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richard.novel.R;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.common.utils.ToastUtil;


/**
 * 搜索
 */
public class SearchWidget extends LinearLayout implements View.OnClickListener {

    public interface OnSearchCallback {
        void search(String target);
    }

    private OnSearchCallback callback;

    private EditText mEditText;

    private ImageView mImageView;

    private String hintText;
    private boolean editable;

    public SearchWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initViews(context);
    }

    public SearchWidget(Context context) {
        super(context);
        initAttrs(context);
        initViews(context);
    }

    private void initAttrs(Context context) {
        TypedArray ta = context.obtainStyledAttributes(R.styleable.SearchWidget);
        hintText = ta.getString(R.styleable.SearchWidget_hint_txt);
        editable = ta.getBoolean(R.styleable.SearchWidget_editable, true);
        ta.recycle();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchWidget);
        hintText = ta.getString(R.styleable.SearchWidget_hint_txt);
        ta.recycle();
    }

    private void initViews(Context context) {

        LayoutInflater.from(context).inflate(R.layout.search_widget, this);

        mEditText = findViewById(R.id.search_widget_edit);
        mImageView = findViewById(R.id.search_icon);
        mEditText.setHint(hintText);

        mImageView.setOnClickListener(this);
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH)
            {
                String keywords = v.getText().toString();
                if(!StringUtils.isEmpty(keywords)){
                    onClick(mImageView);
                }
            }
            return false;
        });

        mEditText.setCursorVisible(editable);
        mEditText.setFocusable(editable);
//        mEditText.setFocusableInTouchMode(editable);
        if(editable) mEditText.requestFocus();

    }

    public void setOnSearchCallback(OnSearchCallback onSearchCallback) {
        callback = onSearchCallback;
    }

    @Override
    public void onClick(View v) {
        String content = mEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showSingleToast("请输入您要搜索的内容");
        } else {
            if (callback != null) {
                callback.search(content);
            }
        }
    }
}
