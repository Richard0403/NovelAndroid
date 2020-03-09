package com.richard.novel.widget;

import android.content.Context;
import android.content.res.TypedArray;
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

public class EmptyView extends LinearLayout {

    private String tips;

    private TextView textView;

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        tips = ta.getString(R.styleable.EmptyView_tips);
        ta.recycle();
        initViews();
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, this);

        textView = findViewById(R.id.empty_tips);
        textView.setText(tips);
    }

    public void setTips(String tips){
        this.tips = tips;
        textView.setText(tips);
    }

}
