package com.innolux.app;

import android.app.Application;
import android.content.Context;

import com.innolux.utils.SPUtils;


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

        //初始化设置参数功率
        SPUtils.putInt(sContext, Constant.POWER, 26);   //功率
        SPUtils.putInt(sContext,Constant.STARTLOACTION,2);
        SPUtils.putInt(sContext,Constant.ENDLOCATION,8);

    }

}
