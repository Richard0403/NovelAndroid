package com.richard.novel.common.utils;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * UIUtil
 */
public class UIUtil {

    public static void showViews(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void hideViews(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.INVISIBLE) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void goneViews(View... views) {
        for (View view : views) {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void setClickable(boolean clickable, View... views) {
        for (View view : views) {
            view.setClickable(clickable);
        }
    }

    public static LinearLayoutManager getHorizontalManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
    }

    public static LinearLayoutManager getScrollManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
    }

    /**
     * 通过反射的方式，重新设置TabLayout中TabView的宽度
     * @param tabLayout
     */
    public static void resetTabWidth(TabLayout tabLayout) {
        tabLayout.post(() -> {
            try {
                LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
                int dp10 = ScreenUtils.dpToPxInt(10);

                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);
                    Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);

                    TextView mTextView = (TextView) mTextViewField.get(tabView);
                    tabView.setPadding(0, 0, 0, 0);

                    int width = 0;
                    width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width;
                    params.leftMargin = dp10;
                    params.rightMargin = dp10;
                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
