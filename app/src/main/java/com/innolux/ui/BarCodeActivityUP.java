package com.innolux.ui;

import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hsm.barcode.DecodeResult;
import com.hsm.barcode.Decoder;
import com.hsm.barcode.DecoderException;
import com.innolux.Barcode;
import com.innolux.R;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.WarningToneUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/8  19:06.
 * 描述：
 */

public class BarCodeActivityUP extends BaseActivity {


    private final int timeOut = 5000;
    @BindView(R.id.listView_data_list)
    ListView mListViewDataList;
    private Decoder mDecoder;  //扫描解码
    private DecodeResult mDecodeResult; //扫描结果
    private boolean threadRunning = false;

    private List<Barcode> mBarcodes = new ArrayList<>();
    private ArrayList<Map<String, String>> listMap;
    private SimpleAdapter adapter = null;

    @Override
    public int getLayoutResID() {
        return R.layout.barcode_test;
    }

    @Override
    protected void init() {
//        WarningToneUtil.initSoundPool(this);
        mDecodeResult = new DecodeResult();
        mDecoder = new Decoder();
        try {
            mDecoder.connectDecoderLibrary();

        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    public void scan(View view) {
        toast("被点击了");
        WarningToneUtil.play(this,1, 0);
        String barCode = BarCodeUtils.getInstans().scan(timeOut);
        displayScanResult(barCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mDecoder.disconnectDecoderLibrary();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示扫码结果
     * @param barCode
     */

    private void displayScanResult(final String barCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    sortAndadd(mBarcodes,barCode);
                    addListView();
                WarningToneUtil.play(BarCodeActivityUP.this,1, 0);
            }
        });
    }

    /**
     * 将集合中的条码添加到listView中
     */
    private void addListView() {
        listMap = new ArrayList<Map<String, String>>();
        int id = 1;
        for (Barcode barcode : mBarcodes) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id + "");
            map.put("barcode", barcode.getBarcode());
            map.put("count", barcode.getCount() + "");
            listMap.add(map);
            id++;
        }
//        adapter = new SimpleAdapter(this, listMap, R.layout.listview_item,
//                new String[]{"id", "barcode", "count",}, new int[]{
//                R.id.textView_list_item_id,
//                R.id.textView_list_item_barcode,
//                R.id.textView_list_item_count});
//        mListViewDataList.setAdapter(adapter);
        mListViewDataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void onPause() {
        try {
            BarCodeUtils.sDecoder.disconnectDecoderLibrary();
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    long exitSytemTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //减少频繁触发
        if (System.currentTimeMillis() - exitSytemTime < 100) {
//			exitSytemTime = System.currentTimeMillis();
            return true;
        }
        exitSytemTime = System.currentTimeMillis();
        if (keyCode == 131 || keyCode == 132 || keyCode == 133 || keyCode == 134 || keyCode == 135) {
            if (!threadRunning) {
                String barCode = BarCodeUtils.getInstans().scan(timeOut);
                displayScanResult(barCode);
            }
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitSytemTime > 2000) {
                exitSytemTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
