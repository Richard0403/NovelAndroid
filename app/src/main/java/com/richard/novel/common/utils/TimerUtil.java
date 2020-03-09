package com.richard.novel.common.utils;

import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Richard on 2018/11/27.
 */

public class TimerUtil {
    private static final String TAG = "TimerUtil";

    private static Timer mTimer = null;
    private static TimerTask mTimerTask = null;


    private static long count = 0;
    private static boolean isPause = false;

    private static int delay = 1000;  //1s
    private static int period = 1000;  //1s

    private static TimerListener timerTick;

    private static Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if(timerTick!=null){
                timerTick.onTick(count);
            }
        }
    };

    public static void startTimer(){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    LogUtil.i(TAG, "count: "+String.valueOf(count));
                    sendMessage();

                    do {
                        try {
                            LogUtil.i(TAG, "sleep(1000)...");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    } while (isPause);

                    count ++;
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, delay, period);

    }

    public static void pauseTimer(){
        LogUtil.i(TAG, "Pause");
        isPause = true;
    }

    public static void continueTimer(){
        LogUtil.i(TAG, "continue");
        isPause = false;
    }


    public static void stopTimer(){

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        count = 0;
    }

    public static long getCountDown(){
        return count;
    }


    private static void sendMessage(){
        if (mHandler != null) {
            Message message = Message.obtain(mHandler);
            mHandler.sendMessage(message);
        }
    }

    public static void setTimerListener(TimerListener listener){
        timerTick = listener;
    }

    public static void resetCount() {
        count = 0;
    }

    public interface TimerListener{
        void onTick(long count);
    }


}
