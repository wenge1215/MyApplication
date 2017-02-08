package com.innolux.app;

import android.app.Application;
import android.content.Context;

import com.innolux.utils.SPUtils;
import com.innolux.utils.WarningToneUtil;


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
        WarningToneUtil.initSoundPool(sContext);
        initSetting();
    }

    private void initSetting() {
        SPUtils.putString(MyApp.getContext(),Constant.ACCESSPASSWORD,"00000000");
        SPUtils.putInt(MyApp.getContext(),Constant.POWER,26);
        SPUtils.putInt(MyApp.getContext(),Constant.DATAREGION,1);
        SPUtils.putInt(MyApp.getContext(),Constant.STARTLOACTION,Integer.valueOf(2));
        SPUtils.putInt(MyApp.getContext(),Constant.ENDLENGTH,Integer.valueOf(8));
    }
}
