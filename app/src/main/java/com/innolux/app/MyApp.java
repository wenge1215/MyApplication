package com.innolux.app;

import android.app.Application;
import android.content.Context;


/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/18  16:31.
 * 描述：
 */

public class MyApp extends Application {
    private static Context sContext;
    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {//程序的入口方法
        super.onCreate();

        //创建一些整个app常用的一些对象
        //上下文
        sContext = getApplicationContext();

    }

}
