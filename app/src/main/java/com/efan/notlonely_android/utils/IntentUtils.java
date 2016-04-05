package com.efan.notlonely_android.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by linqh0806 on 16-2-2.
 */
public class IntentUtils {
    /**
     * 开启activity
     * @param context
     * @param cls
     */
    public static void startActivity(Activity context, Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }

    /**
     * 开启activity并关闭当前activity
     * @param context
     * @param cls
     */
    public static void startActivityAndFinish(Activity context, Class<?> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 延时一段时间后开启activity
     * @param context
     * @param cls
     * @param delaytime
     */
    public static void startActivityForDelay(final Activity context,final Class<?> cls,final long delaytime){
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(delaytime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context,cls);
                context.startActivity(intent);
            };
        }.start();
    }

    /**
     * 延时一段时间后开启activity并关闭当前activity
     * @param context
     * @param cls
     * @param delaytime
     */
    public static void startActivityForDelayAndFinish(final Activity context,final Class<?> cls,final long delaytime){
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(delaytime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context,cls);
                context.startActivity(intent);
                context.finish();
            };
        }.start();
    }
}
