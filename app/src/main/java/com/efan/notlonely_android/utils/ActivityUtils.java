package com.efan.notlonely_android.utils;

/**
 * Created by linqh0806 on 2016/5/18.
 */
public class ActivityUtils {
    /**
     * 第一次和第二次的退出间隔时间基准
     */
    private static final long EXIT_TWICE_INTERVAL = 2000;

    private static long mExitTime = 0;

    /**
     * 第二次按退出则返回true,否则返回false
     * @return
     */
    public static boolean exitTwice() {
        long newExitTime = System.currentTimeMillis();
        if (newExitTime - mExitTime > EXIT_TWICE_INTERVAL) {
            mExitTime = newExitTime;
            return false;
        } else {
            return true;
        }
    }
}
