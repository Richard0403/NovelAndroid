package com.richard.novel.view.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.Toast;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.richard.novel.R;
import com.richard.novel.common.base.App;
import com.richard.novel.common.utils.PermissionUtils;
import com.richard.novel.view.base.BaseActivity;
import com.richard.novel.view.main.dialog.UpdateTipDialog;
import com.richard.novel.view.main.adapter.HomeViewPagerAdapter;
import com.richard.novel.view.main.fragment.FragmentShelf;
import com.richard.novel.widget.NoManyScrollViewPager;

import java.io.File;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_content)
    NoManyScrollViewPager vp_content;
    @BindView(R.id.tab_bottom)
    AdvancedPagerSlidingTabStrip tab_bottom;

    private HomeViewPagerAdapter pagerAdapter;

    public static final String EXTRA_TAB_INDEX = "EXTRA_TAB_INDEX";
    public static final String EXTRA_SUB_TAB_INDEX = "EXTRA_SUB_TAB_INDEX";
    private long mExitTime;
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        pagerAdapter = new HomeViewPagerAdapter(getSupportFragmentManager());
        vp_content.setAdapter(pagerAdapter);
        vp_content.setOffscreenPageLimit(pagerAdapter.getCount());
        tab_bottom.setViewPager(vp_content);
        tab_bottom.setSelectItem(0);
    }

    @Override
    protected void initData() {
        requestPermission();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        if(requestCode == UpdateTipDialog.INSTALL_PACKAGES_REQUESTCODE){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(UpdateTipDialog.savePath)), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void requestPermission(){
        PermissionUtils.checkStoragePermission(this, isSuccess -> {
            PermissionUtils.checkLocationPermission(MainActivity.this, null);
        });
    }
    public static void start(int tabIndex, int subTabIndex) {
        Intent intent = new Intent(App.getInstance(), MainActivity.class);
        intent.putExtra(EXTRA_TAB_INDEX, tabIndex);
        intent.putExtra(EXTRA_SUB_TAB_INDEX, subTabIndex);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.getInstance().startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            FragmentShelf shelf = (FragmentShelf) pagerAdapter.getItem(HomeViewPagerAdapter.VIEW_SECOND);
            if(shelf.isSelectShow()){
                shelf.showSelect(false);
                return true;
            }

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int tabIndex = intent.getIntExtra(EXTRA_TAB_INDEX, -1);
        int subTabIndex = intent.getIntExtra(EXTRA_SUB_TAB_INDEX, 0);
        if(tabIndex>=0 && tabIndex<4){
            tab_bottom.setSelectItem(tabIndex);
            vp_content.setCurrentItem(tabIndex);
        }
    }
}
