package com.richard.novel.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.richard.novel.widget.HomeScroller;
import com.richard.novel.widget.NoManyScrollViewPagerHelper;

/**
 * By Richard on 2018/3/2.
 * 多页面时候，直接切换，不滑动
 */

public class NoManyScrollViewPager extends ViewPager {
    private NoManyScrollViewPagerHelper helper;
    private float startX;
    private float startY;

    public NoManyScrollViewPager(Context context) {
        this(context,null);
    }

    public NoManyScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        helper=new NoManyScrollViewPagerHelper(this);
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item,true);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        HomeScroller scroller=helper.getScroller();
        if(Math.abs(getCurrentItem()-item)>1){
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        }else{
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //判断是左右滑动还是上下滑动
                float endX = ev.getRawX();
                float endY = ev.getRawY();
                if (Math.abs(endX - startX) > Math.abs(endY - startY) ){
                    //如果是左右滑动，请求父控件不要拦截自己的
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    //如果是上下滑动，拦截设置为false
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
