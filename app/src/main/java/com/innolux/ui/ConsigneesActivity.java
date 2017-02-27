package com.innolux.ui;

import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.innolux.Barcode;
import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.app.Constant;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.RFIDUtils;
import com.innolux.utils.WarningToneUtil;
import com.innolux.widget.NumView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/17  10:40.
 * 描述：收料
 */
public class ConsigneesActivity extends BaseActivity {
    @BindView(R.id.editText_boom_num)
    EditText mEditTextBoomNum;
    @BindView(R.id.layout_boom_num)
    LinearLayout mLayoutBoomNum;
    @BindView(R.id.cb_is_boom)
    CheckBox mCbIsBoom;
    @BindView(R.id.cb_is_continuous)
    CheckBox mCbIsContinuous;
    @BindView(R.id.lv_boom)
    ListView mLvBoom;
    @BindView(R.id.btn_finish)
    Button mBtnFinish;
    @BindView(R.id.btn_scan)
    Button mBtnScan;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_scan_boom_num)
    Button mBtnScanBoomNum;
    @BindView(R.id.editText_pass_box_num)
    EditText mEditTextPassBoxNum;
    @BindView(R.id.btn_scan_pass_box_num)
    Button mBtnScanPassBoxNum;
    @BindView(R.id.layout_pass_box_num)
    LinearLayout mLayoutPassBoxNum;
    private boolean running = false;        //是否连续扫码中
    private int timeout = Constant.TIME_OUT;
    private List<Barcode> mBarcodes = new ArrayList<>();
    private ArrayList<Map<String, String>> listMap = null;
    //    private SimpleAdapter adapter = null;
    private BoomAdapter mBoomAdapter;
    private int mCount = 0;
    private String mBoomNun;
    private String mEpc;


//    public static String IS_QR_CODE = "is_qr_code";


    @Override
    public int getLayoutResID() {
        return R.layout.activity_consignee;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.overflow, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    protected void init() {
//        initActionBar("收料作业");
        initCBListener();
//        initData();     //测试数据
        initListItemListener();
    }

    /**
     * listView 条目的点击事件
     */
    private void initListItemListener() {
        mLvBoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String barcode = mBoomAdapter.getItem(i).getBarcode();
                toast(barcode + "被点击了");
                Intent intent = new Intent(ConsigneesActivity.this, DetailsActivity.class);
                intent.putExtra(Constant.DETAIL_KEY, barcode);
//                intent.putExtra(IS_QR_CODE,true);

                startActivity(intent);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            Barcode barcode = new Barcode();
            barcode.setBarcodeid(i * 100);
            barcode.setBarcode("100861234567777777" + i);
            barcode.setCount(i * 100);
            barcode.setName("商品" + i);
            mBarcodes.add(0, barcode);
        }
    }

    private void initCBListener() {
        /**
         * boom收料
         */
        mCbIsBoom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mLayoutBoomNum.setVisibility(View.VISIBLE);
                    mLayoutPassBoxNum.setVisibility(View.VISIBLE);
                } else {
                    mLayoutBoomNum.setVisibility(View.GONE);
                    mLayoutPassBoxNum.setVisibility(View.GONE);
                }
            }
        });
        /**
         * 连续扫码
         */
        mCbIsContinuous.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                running = !running;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (running) {
                            SystemClock.sleep(100);
                            String barCode = BarCodeUtils.getInstans().scan(timeout);
                            if (barCode != null && barCode.length() > 0) {
                                displayScanResult(barCode);
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void displayScanResult(final String barCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                sortAndadd(mBarcodes, barCode);
                addListView();
                WarningToneUtil.play(1, 0);
            }
        });
    }

    private void addListView() {
        mBoomAdapter = new BoomAdapter();
        mLvBoom.setAdapter(mBoomAdapter);
    }

    class BoomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mBarcodes != null && mBarcodes.size() > 0) {
                return mBarcodes.size();
            }
            return 0;
        }

        @Override
        public Barcode getItem(int i) {
            if (mBarcodes != null && mBarcodes.size() > 0) {
                return mBarcodes.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ConsigneesActivity.this).inflate(R.layout.listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Barcode barcode = mBarcodes.get(position);
            int count = barcode.getCount();


            holder.mTextViewListItemId.setText(barcode.getBarcodeid() + "");
            holder.mTextViewListItemBarcode.setText(barcode.getBarcode());
            holder.mNumView.setTtCount(count + "");
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.textView_list_item_id)
            TextView mTextViewListItemId;
            @BindView(R.id.textView_list_item_barcode)
            TextView mTextViewListItemBarcode;
            @BindView(R.id.num_view)
            NumView mNumView;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.btn_scan_boom_num, R.id.btn_finish, R.id.btn_scan, R.id.btn_cancel,R.id.btn_scan_pass_box_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_boom_num:
                mBoomNun = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
                mEditTextBoomNum.setText(mBoomNun);
                break;
            case R.id.btn_finish:       //完成扫描，向服务器提交数据
                toast("完成扫描，向服务器提交数据");
                break;
            case R.id.btn_scan:
                toast("点击Scan");
                String barCode = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
                if (barCode != null && barCode.length() > 0) {
                    displayScanResult(barCode);
                }
                break;
            case R.id.btn_cancel:
                mBarcodes.clear();
                mBoomAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_scan_pass_box_num:
                RFIDUtils rfidUtils = RFIDUtils.getInstance();
                mEpc =  rfidUtils.readOnceEPC();
                mEditTextPassBoxNum.setText(mEpc);
                break;
        }
    }
}
