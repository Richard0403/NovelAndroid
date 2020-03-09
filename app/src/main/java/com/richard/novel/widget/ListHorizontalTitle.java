package com.richard.novel.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richard.novel.R;


/**
 * 右边整个Bar的右边部分
 */
public class ListHorizontalTitle extends FrameLayout {

    private LinearLayout rightLayout;
    private TextView tv_title;
    private TextView descTextView;
    private String rightDesc, title;

    public ListHorizontalTitle(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.NewListTitle);
        title = a.getString(R.styleable.NewListTitle_list_title_name);
        rightDesc = a.getString(R.styleable.NewListTitle_list_title_desc);
        a.recycle();

        initViews();
    }

    private void initViews(){
        LayoutInflater.from(getContext()).inflate(R.layout.layout_horizontal_list_title, this);
        rightLayout = findViewById(R.id.new_list_right_layout);
        tv_title = findViewById(R.id.tv_title);
        descTextView = findViewById(R.id.new_title_right_desc);

        tv_title.setText(title);
        descTextView.setText(rightDesc);
    }

    public void setRightClickListener(OnClickListener onClickListener){
        rightLayout.setOnClickListener(onClickListener);
    }


    public void setTitle(String title){
        this.title = title;
        tv_title.setText(title);
    }

    public void setRightDesc(String desc){
        rightDesc = desc;
        descTextView.setText(desc);
    }
}
