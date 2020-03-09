package com.richard.novel.view.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.richard.novel.BuildConfig;
import com.richard.novel.R;
import com.richard.novel.common.cache.ActivityCollector;
import com.richard.novel.common.cache.SysPrefer;
import com.richard.novel.common.utils.PermissionUtils;
import com.richard.novel.view.main.dialog.ConfirmDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;

/**
 * Created Richard
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    private PermissionUtils.OnGetPermissionCallback onGetPermissionCallback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);
        mContext = this;
        MobclickAgent.setCatchUncaughtExceptions(!SysPrefer.IS_DEBUG);
        initView(savedInstanceState);
        initData();
//        setStateBar();
    }

    protected abstract int getLayout();// 兼容原版

    protected abstract void initView(Bundle savedInstanceState);//兼容原版

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

//    private void setStateBar(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public static <T> void start(Context context, Class <T> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }


    public void setOnGetPermissionCallback(PermissionUtils.OnGetPermissionCallback onGetPermissionCallback) {
        this.onGetPermissionCallback = onGetPermissionCallback;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){//申请成功
            if (onGetPermissionCallback != null){
                onGetPermissionCallback.onGetPermission(true);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Add array length check logic to avoid ArrayIndexOutOfBoundsException
                if (permissions.length > 0 && !shouldShowRequestPermissionRationale(permissions[0])){
                    showPermissionSettingDialog(requestCode);
                } else {//申请失败
                    if (onGetPermissionCallback != null){
                        onGetPermissionCallback.onGetPermission(false);
                    }
                }
            } else {//申请失败
                if (onGetPermissionCallback != null){
                    onGetPermissionCallback.onGetPermission(false);
                }
            }
        }
    }
    private void showPermissionSettingDialog(int requestCode) {
        String permissionName = PermissionUtils.getPermissionName(this, requestCode);
        String msg = String.format(getString(R.string.set_permission_in_setting), permissionName);
        ConfirmDialog confirmDialog = new ConfirmDialog(this, msg, new ConfirmDialog.OnViewClick() {
            @Override
            public void onConfirm() {
                toSetPermission();
            }
            @Override
            public void onCancel() {}
        });
        confirmDialog.show();
        confirmDialog.setCancelText("取消");
        confirmDialog.setConfirmText("去开启");
    }

    private void toSetPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        startActivity(intent);
    }

}
