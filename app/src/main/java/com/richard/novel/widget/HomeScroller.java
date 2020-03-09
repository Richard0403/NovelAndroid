package com.richard.novel.widget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * By Richard on 2018/3/13.
 */

public class HomeScroller extends Scroller {
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };


    public boolean noDuration;

    public void setNoDuration(boolean noDuration) {
        this.noDuration = noDuration;
    }

    public HomeScroller(Context context) {
        this(context,sInterpolator);
    }

    public HomeScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        if(noDuration)
            //界面滑动不需要时间间隔
            super.startScroll(startX, startY, dx, dy, 0);
        else
            super.startScroll(startX, startY, dx, dy,duration);
    }
}
