/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.richard.novel.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Richard.
 * @date 16/9/2.
 */
public class FormatUtils {

    private static final long ONE_MINUTE = 60 * 1000L;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_WEEK = 7 * ONE_DAY;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    private static String[] chnNumChar = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static String[] chnUnitSection = {"", "万", "亿", "万亿"};
    private static String[] chnUnitChar = {"", "十", "百", "千"};

    /**
     * 时间格式化
     *
     * @param pattern yyyy-MM-dd HH:mm:ss.SSS";
     * @param time
     * @return
     */
    public static String getFormatDateTime(String pattern, long time) {
        String dateStr = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Date curDate = new Date(time);
            dateStr = formatter.format(curDate);
        }catch (Exception e){
            LogUtil.e("时间格式化错误");
        }
        return dateStr;
    }

    public static String getFormatCash(float money){
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(money);
    }


    /**
     * 根据Date获取描述性时间，如3分钟前，1天前
     *
     * @param time
     * @return
     */
    public static String getDescriptionFromTime(long time) {
        long delta = System.currentTimeMillis() - time;
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 60L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    public static String formatWordCount(int wordCount) {
        if (wordCount / 10000 > 0) {
            return (int) ((wordCount / 10000f) + 0.5) + "万字";
        } else if (wordCount / 1000 > 0) {
            return (int) ((wordCount / 1000f) + 0.5) + "千字";
        } else {
            return wordCount + "字";
        }
    }

    public static String numberToChinese(int num) {//转化一个阿拉伯数字为中文字符串
        if (num == 0) {
            return "零";
        }
        int unitPos = 0;//节权位标识
        String All = "";
        String chineseNum = "";//中文数字字符串
        boolean needZero = false;//下一小结是否需要补零
        String strIns = "";
        while (num > 0) {
            int section = num % 10000;//取最后面的那一个小节
            if (needZero) {//判断上一小节千位是否为零，为零就要加上零
                All = chnNumChar[0] + All;
            }
            chineseNum = sectionTOChinese(section, chineseNum);//处理当前小节的数字,然后用chineseNum记录当前小节数字
            if (section != 0) {//此处用if else 选择语句来执行加节权位
                strIns = chnUnitSection[unitPos];//当小节不为0，就加上节权位
                chineseNum = chineseNum + strIns;
            } else {
                strIns = chnUnitSection[0];//否则不用加
                chineseNum = strIns + chineseNum;
            }
            All = chineseNum + All;
            chineseNum = "";
            needZero = (section < 1000) && (section > 0);
            num = num / 10000;
            unitPos++;
        }
        return All;
    }

    private static String sectionTOChinese(int section, String chineseNum) {
        String setionChinese;//小节部分用独立函数操作
        int unitPos = 0;//小节内部的权值计数器
        boolean zero = true;//小节内部的制零判断，每个小节内只能出现一个零
        while (section > 0) {
            int v = section % 10;//取当前最末位的值
            if (v == 0) {
                if (!zero) {
                    zero = true;//需要补零的操作，确保对连续多个零只是输出一个
                    chineseNum = chnNumChar[0] + chineseNum;
                }
            } else {
                zero = false;//有非零的数字，就把制零开关打开
                setionChinese = chnNumChar[v];//对应中文数字位
                setionChinese = setionChinese + chnUnitChar[unitPos];//对应中文权位
                chineseNum = setionChinese + chineseNum;
            }
            unitPos++;
            section = section / 10;
        }
        return chineseNum;
    }

}