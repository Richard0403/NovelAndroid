package com.richard.novel.reader;
import com.richard.novel.common.constant.SharedPreferenceHelper;
import com.richard.novel.common.utils.ScreenUtils;
import com.richard.novel.reader.page.PageMode;
import com.richard.novel.reader.page.PageStyle;

/**
 * Created by newbiechen on 17-5-17.
 * 阅读器的配置管理
 */

public class ReadSettingManager {
    /*************实在想不出什么好记的命名方式。。******************/
    public static final int READ_BG_DEFAULT = 0;
    public static final int READ_BG_1 = 1;
    public static final int READ_BG_2 = 2;
    public static final int READ_BG_3 = 3;
    public static final int READ_BG_4 = 4;
    public static final int NIGHT_MODE = 5;

    public static final String SHARED_READ_BG = "shared_read_bg";
    public static final String SHARED_READ_BRIGHTNESS = "shared_read_brightness";
    public static final String SHARED_READ_IS_BRIGHTNESS_AUTO = "shared_read_is_brightness_auto";
    public static final String SHARED_READ_TEXT_SIZE = "shared_read_text_size";
    public static final String SHARED_READ_IS_TEXT_DEFAULT = "shared_read_text_default";
    public static final String SHARED_READ_PAGE_MODE = "shared_read_mode";
    public static final String SHARED_READ_NIGHT_MODE = "shared_night_mode";
    public static final String SHARED_READ_VOLUME_TURN_PAGE = "shared_read_volume_turn_page";
    public static final String SHARED_READ_FULL_SCREEN = "shared_read_full_screen";
    public static final String SHARED_READ_CONVERT_TYPE = "shared_read_convert_type";

    private static volatile ReadSettingManager sInstance;



    public static void setPageStyle(PageStyle pageStyle) {
        SharedPreferenceHelper.save(SHARED_READ_BG, pageStyle.ordinal());
    }

    public static void setBrightness(int progress) {
        SharedPreferenceHelper.save(SHARED_READ_BRIGHTNESS, progress);
    }

    public static void setAutoBrightness(boolean isAuto) {
        SharedPreferenceHelper.save(SHARED_READ_IS_BRIGHTNESS_AUTO, isAuto);
    }

    public static void setDefaultTextSize(boolean isDefault) {
        SharedPreferenceHelper.save(SHARED_READ_IS_TEXT_DEFAULT, isDefault);
    }

    public static void setTextSize(int textSize) {
        SharedPreferenceHelper.save(SHARED_READ_TEXT_SIZE, textSize);
    }

    public static void setPageMode(PageMode mode) {
        SharedPreferenceHelper.save(SHARED_READ_PAGE_MODE, mode.ordinal());
    }

    public static void setNightMode(boolean isNight) {
        SharedPreferenceHelper.save(SHARED_READ_NIGHT_MODE, isNight);
    }

    public static int getBrightness() {
        return SharedPreferenceHelper.getInt(SHARED_READ_BRIGHTNESS, 40);
    }

    public static boolean isBrightnessAuto() {
        return SharedPreferenceHelper.getBoolean(SHARED_READ_IS_BRIGHTNESS_AUTO, true);
    }

    public static int getTextSize() {
        return SharedPreferenceHelper.getInt(SHARED_READ_TEXT_SIZE, (int) ScreenUtils.spToPx(20));
    }

    public static boolean isDefaultTextSize() {
        return SharedPreferenceHelper.getBoolean(SHARED_READ_IS_TEXT_DEFAULT, false);
    }

    public static PageMode getPageMode() {
        int mode = SharedPreferenceHelper.getInt(SHARED_READ_PAGE_MODE, PageMode.SIMULATION.ordinal());
        return PageMode.values()[mode];
    }

    public static PageStyle getPageStyle() {
        int style = SharedPreferenceHelper.getInt(SHARED_READ_BG, PageStyle.BG_0.ordinal());
        return PageStyle.values()[style];
    }

    public static boolean isNightMode() {
        return SharedPreferenceHelper.getBoolean(SHARED_READ_NIGHT_MODE, false);
    }

    public static void setVolumeTurnPage(boolean isTurn) {
        SharedPreferenceHelper.save(SHARED_READ_VOLUME_TURN_PAGE, isTurn);
    }

    public static boolean isVolumeTurnPage() {
        return SharedPreferenceHelper.getBoolean(SHARED_READ_VOLUME_TURN_PAGE, false);
    }

    public static void setFullScreen(boolean isFullScreen) {
        SharedPreferenceHelper.save(SHARED_READ_FULL_SCREEN, isFullScreen);
    }

    public static boolean isFullScreen() {
        return SharedPreferenceHelper.getBoolean(SHARED_READ_FULL_SCREEN, true);
    }

    public static void setConvertType(int convertType) {
        SharedPreferenceHelper.save(SHARED_READ_CONVERT_TYPE, convertType);
    }

    public static int getConvertType() {
        return SharedPreferenceHelper.getInt(SHARED_READ_CONVERT_TYPE, 0);
    }
}
