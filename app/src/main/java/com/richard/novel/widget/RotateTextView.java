package com.richard.novel.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * By Richard on 2018/5/12.
 */

public class RotateTextView extends AppCompatTextView {
    public RotateTextView(Context context) {
        super(context);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //倾斜度45,上下左右居中
        canvas.translate(getMeasuredHeight()/8,-getMeasuredWidth()/8);
        canvas.rotate(45, getMeasuredWidth()/2, getMeasuredHeight()/2);
        super.onDraw(canvas);
    }
}
