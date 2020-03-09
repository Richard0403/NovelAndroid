package com.richard.novel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richard.novel.R;


/**
 * Created by James
 * Date 2018/2/24.
 * description
 */

public class WhiteFooterView extends LinearLayout {
    public WhiteFooterView(Context context) {
        this(context,null);
    }

    public WhiteFooterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public WhiteFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.footer_white_view, this);
    }
}
