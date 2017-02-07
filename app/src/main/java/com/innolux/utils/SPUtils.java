package com.innolux.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.innolux.app.Constant;


/**
 * 创建者： WENGE .
 * 创建日期： 2016/12/16  14:28.
 * 描述：
 */

public class SPUtils {

    /**
     * 保存一个String
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences(Constant.SETTING, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取一个String
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context,String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context,String key,String def){
        SharedPreferences sp = context.getSharedPreferences(Constant.SETTING, Context.MODE_PRIVATE);
        return sp.getString(key, def);
    }

    /**
     * 保存一个boolean值
     * @param context
     * @param key
     * @param b
     */
    public static void putBoolean(Context context,String key,boolean b){
        SharedPreferences sp = context.getSharedPreferences(Constant.SETTING, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, b);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean def) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SETTING, Context.MODE_PRIVATE);
        return sp.getBoolean(key, def);
    }
}
