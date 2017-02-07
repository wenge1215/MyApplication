package com.innolux.utils;

import com.hsm.barcode.DecodeResult;
import com.hsm.barcode.Decoder;
import com.hsm.barcode.DecoderConfigValues;
import com.hsm.barcode.DecoderException;
import com.hsm.barcode.SymbologyConfig;
import com.innolux.Barcode;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/16  14:23.
 * 描述：扫描的工具类
 */

public class BarCodeUtils {

    public static DecodeResult sDecodeResult = new DecodeResult();
    public static Decoder sDecoder  = new Decoder();
    public static BarCodeUtils mBarCodeUtils = null;

    public static BarCodeUtils getInstans(){
        if (mBarCodeUtils == null) {
            synchronized (BarCodeUtils.class) {
                if (mBarCodeUtils == null) {
                  return   mBarCodeUtils = new BarCodeUtils();
                }
            }
        }
        return mBarCodeUtils;
    }

    private  void init() {
        try {
            sDecoder.connectDecoderLibrary();
            settingPara();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        sDecoder.startScanning();
                        Thread.sleep(100);
                        sDecoder.stopScanning();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }
    private void settingPara() {
        try {
            //设置EAN13校验位
            SymbologyConfig config = new SymbologyConfig(DecoderConfigValues.SymbologyID.SYM_EAN13);
            config.Flags = 5 ;
            config.Mask = 1 ;
            sDecoder.setSymbologyConfig(config);
            sDecoder.disableSymbology(DecoderConfigValues.SymbologyID.SYM_ALL);


            sDecoder.enableSymbology(DecoderConfigValues.SymbologyID.SYM_QR);
            sDecoder.enableSymbology(DecoderConfigValues.SymbologyID.SYM_PDF417);
            sDecoder.enableSymbology(DecoderConfigValues.SymbologyID.SYM_EAN13);
            sDecoder.enableSymbology(DecoderConfigValues.SymbologyID.SYM_CODE128) ;

            sDecoder.enableSymbology(DecoderConfigValues.SymbologyID.SYM_DATAMATRIX);

        } catch (DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public String scan(int timeOut){
        init();
        try {
            sDecoder.waitForDecodeTwo(timeOut,sDecodeResult);
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        if (sDecodeResult.length > 0) {
            try {
                byte[] barcodeByteData = sDecoder.getBarcodeByteData();
                try {
                    String braCode = new String(barcodeByteData, "utf-8");
                    return braCode;

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } catch (DecoderException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 将条码保存到集合中
     *
     * @param list
     * @param barcode
     * @return
     */
    private List<Barcode> sortAndadd(List<Barcode> list, String barcode) {
        Barcode goods = new Barcode();
        goods.setBarcode(barcode);
        int temp = 1;        //记录条码出现的次数
        //集合为空，直接添加并返回
        if (list == null || list.size() == 0) {
            goods.setCount(temp);
            list.add(goods);
            return list;
        }
        //集合不为空
        for (int i = 0; i < list.size(); i++) {
            //获取的二维码已存在
            if (barcode.equals(list.get(i).getBarcode())) {
                temp = list.get(i).getCount() + temp;
                goods.setCount(temp);
                for (int j = i; j > 0; j--) {
                    list.set(j, list.get(j - 1));
                }
                list.set(0, goods);
                return list;
            }
        }
        //
        Barcode lastgoods = list.get(list.size() - 1);
        for (int j = list.size() - 1; j >= 0; j--) {
            if (j == 0) {
                goods.setCount(temp);
                list.set(j, goods);
            } else {
                list.set(j, list.get(j - 1));
            }
        }
        list.add(lastgoods);
        return list;
    }

}
