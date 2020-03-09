package com.richard.novel.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;


import com.richard.novel.common.base.App;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Created by James
 * Date 2017/12/5.
 * description
 */

public class SystemInfo {

    private SystemInfo(){}

    public static boolean isMainProcess() {

        int pid = android.os.Process.myPid();

        if(App.getInstance() == null || TextUtils.isEmpty(getAppName(App.getInstance(), pid))){
            return false;
        }

        return getAppName(pid).equalsIgnoreCase(App.getInstance().getPackageName());
    }

    public static String getAppName(int pid) {
        String processName = null;
        ActivityManager am = (ActivityManager) App.getInstance().getSystemService(App.getInstance().ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = App.getInstance().getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {

            }
        }
        return processName;
    }

    public static String getAppName(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = am.getRunningAppProcesses();
            while (runningAppProcessInfos.iterator().hasNext()) {
                ActivityManager.RunningAppProcessInfo info = runningAppProcessInfos.iterator().next();
                try {
                    if (info.pid == pid) {
                        return info.processName;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static void setImmersiveMode(boolean hasFocus, View decorView){
        if(hasFocus && Build.VERSION.SDK_INT >= 19){
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

}
