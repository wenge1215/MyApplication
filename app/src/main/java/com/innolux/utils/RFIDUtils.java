package com.innolux.utils;


import android.content.Context;

import com.android.hdhe.uhf.reader.Tools;
import com.android.hdhe.uhf.reader.UhfReader;
import com.innolux.R;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/18  14:41.
 * 描述：读写RFID的工具类
 */

public class RFIDUtils {
    private Context mContext;
    private UhfReader sReader = UhfReader.getInstance(); //超高频读写器
//    private final String[] strMemBank = {"RESERVE", "EPC", "TID", "USER"};//RESERVE EPC TID USER分别对应0,1,2,3


    public RFIDUtils(Context context) {
        mContext = context;
        initPower();
    }

    //单次读取
    public String readOnceEPC() {
        int memBank = 1;//数据区
        int startAddr = 2;//起始地址
        int length = 8;//读取数据的长度
        byte[] accessPassword = Tools.HexString2Bytes(mContext.getString(R.string.accessPassword));
        byte[] datas = sReader.readFrom6C(memBank, startAddr, length, accessPassword);
        if (datas != null && datas.length > 0) {
            String epc = Tools.Bytes2HexString(datas, datas.length);
            return epc;
        }
        return "";
    }

    /**
     * 写入数据到EPC
     * @param writeData
     * @return true 写数据成功
     */
    public boolean writeEPC(String writeData){
        int memBank = 1;//数据区 EPC
        int startAddr = 2;//起始地址
//        int length = 6;//读取数据的长度
        byte[] accessPassword = Tools.HexString2Bytes(mContext.getString(R.string.accessPassword));
        byte[] dataBytes = Tools.HexString2Bytes(writeData);
        //dataLen = dataBytes/2 dataLen是以字为单位的
       return sReader.writeTo6C(accessPassword, memBank, startAddr, dataBytes.length/2, dataBytes);

    }
    private void initPower() {
        //获取用户设置功率,并设置
//        SharedPreferences shared = mContext.getSharedPreferences("power", 0);
//        int value = shared.getInt("value", 26);
//        Log.e("", "value" + value);
        int value = 26;
        sReader.setOutputPower(value);
    }

    /**
     * 关闭RFID读写器
     */
    public void closeReaner(){
        if (sReader != null) {
            sReader.close();
        }
    }
}
