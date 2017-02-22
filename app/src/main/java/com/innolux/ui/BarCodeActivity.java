package com.innolux.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hsm.barcode.DecodeResult;
import com.hsm.barcode.Decoder;
import com.hsm.barcode.DecoderConfigValues;
import com.hsm.barcode.DecoderException;
import com.innolux.Barcode;
import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.WarningToneUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/8  19:06.
 * 描述：
 */

public class BarCodeActivity extends BaseActivity {
    @BindView(R.id.editText_barcode_count)
    EditText mEditTextBarcodeCount;
    @BindView(R.id.button_clear)
    Button mButtonClear;
    @BindView(R.id.receive_txt)
    LinearLayout mReceiveTxt;
    @BindView(R.id.listView_data_list)
    ListView mListViewDataList;
    @BindView(R.id.button_scan)
    Button mButtonScan;
    @BindView(R.id.button_exit)
    Button mButtonExit;
    @BindView(R.id.linearLayout1)
    LinearLayout mLinearLayout1;
    @BindView(R.id.checkbox_per_100ms)
    CheckBox mCheckboxPer100ms;
    @BindView(R.id.checkBox_lighting)
    CheckBox mCheckBoxLighting;
    private Decoder mDecoder;  //扫描解码
    private DecodeResult mDecodeResult; //扫描结果
    private boolean threadRunning = false;
    private final int timeOut = 5000;
    private List<Barcode> listBarcode = new ArrayList<Barcode>();
    private List<Map<String, String>> listMap;
    private SimpleAdapter adapter = null;


//    displayScanResult

    @Override
    public int getLayoutResID() {
        return R.layout.activity_barcode;
    }

    @Override
    protected void init() {
//        WarningToneUtil.initSoundPool(this);
        /**
         * 注册一个广播接收者，对按键进行监听
         */
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);
        initView();

    }

    private void initView() {
        mCheckBoxLighting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    if (isChecked) {
                        BarCodeUtils.sDecoder.setLightsMode(DecoderConfigValues.LightsMode.ILLUM_ONLY);
                    } else {
                        BarCodeUtils.sDecoder.setLightsMode(DecoderConfigValues.LightsMode.ILLUM_AIM_ON);
                    }
                } catch (DecoderException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        mCheckboxPer100ms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mButtonScan.setClickable(false);
                    mButtonScan.setTextColor(Color.GRAY);
                    threadRunning = true;
                    running = true;
                    scanThread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            while (running) {
                                if (threadRunning) {
                                    String barCode = BarCodeUtils.getInstans().scan(timeOut);
                                    displayScanResult(barCode);
                                    try {
                                        Thread.sleep(300);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }
                    });
                    scanThread.start();
                } else {
                    threadRunning = false;
                    running = false;
                    mButtonScan.setClickable(true);
                    mButtonScan.setTextColor(Color.BLACK);
                    scanThread = null;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
//					if(scanTimer != null){
//						scanTimer.cancel();
//					}
                }

            }
        });
    }

    boolean running = true;
    private Thread scanThread;


    long exitSytemTime = 0;
    //监听按键消息
    private BroadcastReceiver keyReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            int keyCode = intent.getIntExtra("keyCode", 0);
            if (keyCode == 0) {//兼容H941
                keyCode = intent.getIntExtra("keycode", 0);
            }
            boolean keyDown = intent.getBooleanExtra("keydown", false);
            Log.e("", "KEYcODE = " + keyCode + ", Down = " + keyDown);
            if (keyDown) {
                //减少频繁触发
                if (System.currentTimeMillis() - exitSytemTime < 100) {
//					exitSytemTime = System.currentTimeMillis();
                    return;
                }
                exitSytemTime = System.currentTimeMillis();
                if (!threadRunning) {
//                    String barCode = scan(timeOut);
                    String barCode = BarCodeUtils.getInstans().scan(timeOut);
                    displayScanResult(barCode);

                }
            }
        }
    };

    boolean scanning = false;

    @OnClick({R.id.button_clear, R.id.button_scan, R.id.button_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_clear:
                mEditTextBarcodeCount.setText("");
                listBarcode.removeAll(listBarcode);
                mListViewDataList.setAdapter(null);
                break;
            case R.id.button_scan:
                String barCode = BarCodeUtils.getInstans().scan(timeOut);
                displayScanResult(barCode);
                break;
            case R.id.button_exit:
                finish();

                break;
        }
    }

    /**
     * 扫描
     *
     * @param timeout
     * @return
     */


    /**
     * 显示扫码结果
     *
     * @param barCode
     */
    private void displayScanResult(final String barCode) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                sortAndadd(listBarcode, barCode);
                mEditTextBarcodeCount.setText(listBarcode.size() + "");
                addListView();
                WarningToneUtil.play(1, 0);
            }
        });

    }

    private void addListView() {
        listMap = new ArrayList<Map<String, String>>();
        int id = 1;
        for (Barcode barcode : listBarcode) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", id + "");
            map.put("barcode", barcode.getBarcode());
            map.put("count", barcode.getCount() + "");
            listMap.add(map);
        }
//        adapter = new SimpleAdapter(this, listMap, R.layout.listview_item,
//                new String[]{"id", "barcode", "count",}, new int[]{
//                R.id.textView_list_item_id,
//                R.id.textView_list_item_barcode,
//                R.id.textView_list_item_count});
//        mListViewDataList.setAdapter(adapter);

    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(keyReceiver);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        //scanThread.interrupt();
        running = false;
        threadRunning = false;
        mCheckboxPer100ms.setChecked(false);
        if (mDecoder != null) {
            try {
                mDecoder.disconnectDecoderLibrary();
            } catch (DecoderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.onPause();
    }

}
