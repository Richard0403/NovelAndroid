package com.richard.novel.view.main.adapter;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.richard.novel.R;
import com.richard.novel.view.main.fragment.FragmentFind;
import com.richard.novel.view.main.fragment.FragmentHome;
import com.richard.novel.view.main.fragment.FragmentPerson;
import com.richard.novel.view.main.fragment.FragmentShelf;

/**
 * By Richard on 2018/1/2.
 * 参考 https://github.com/HomHomLin/AdvancedPagerSlidingTabStrip
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter implements AdvancedPagerSlidingTabStrip.IconTabProvider {
    public static final int VIEW_FIRST = 0;
    public static final int VIEW_SECOND = 1;
    public static final int VIEW_THIRD = 2;
    public static final int VIEW_FOURTH = 3;
    private static final int VIEW_SIZE = 4;

    private int tabSelectedImages[] = {R.mipmap.icon_b_home_true, R.mipmap.icon_b_shelf_true, R.mipmap.icon_b_find_true, R.mipmap.icon_b_person_true};
    private int tabUnselectedImages[] = {R.mipmap.icon_b_home_false, R.mipmap.icon_b_shelf_false, R.mipmap.icon_b_find_false, R.mipmap.icon_b_person_false};
    private String tabTitles[] = {"悠然", "书架", "发现", "我的"};

    private FragmentHome homeFragment;
    private FragmentShelf shelfFragment;
    private FragmentFind findFragment;
    private FragmentPerson personFragment;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case VIEW_FIRST:
                if (homeFragment == null) {
                    homeFragment = FragmentHome.newInstance();
                }
                return homeFragment;
            case VIEW_SECOND:
                if (shelfFragment == null) {
                    shelfFragment = FragmentShelf.newInstance();
                }
                return shelfFragment;
            case VIEW_THIRD:
                if (findFragment == null) {
                    findFragment = FragmentFind.newInstance();
                }
                return findFragment;
            case VIEW_FOURTH:
                if (personFragment == null) {
                    personFragment = FragmentPerson.newInstance();
                }
                return personFragment;
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return VIEW_SIZE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Integer getPageIcon(int position) {
        return tabUnselectedImages[position];
    }

    @Override
    public Integer getPageSelectIcon(int position) {
        return tabSelectedImages[position];
    }

    @Override
    public Rect getPageIconBounds(int position) {
        return null;
    }

}
