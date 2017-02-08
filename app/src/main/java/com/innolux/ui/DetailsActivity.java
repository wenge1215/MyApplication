package com.innolux.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.innolux.R;
import com.innolux.app.Constant;
import com.innolux.bean.BarcodeDetail;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.RFIDUtils;
import com.innolux.utils.WarningToneUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/18  11:26.
 * 描述：物料详情
 */

public class DetailsActivity extends BaseActivity {

    @BindView(R.id.editText_material_num)
    EditText mEditTextMaterialNum;
    @BindView(R.id.btn_scan_material_num)
    Button mBtnScanMaterialNum;
    @BindView(R.id.layout_material_num)
    LinearLayout mLayoutMaterialNum;
    @BindView(R.id.lv_material_detail)
    ListView mLvMaterialDetail;
    @BindView(R.id.detial_cb_rfid)
    CheckBox mDetialCbRfid;

    private boolean isRFID = false;
    private String mMaterialNum;

    RFIDUtils mRFIDUtils = new RFIDUtils(this);
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;
    private boolean runFlag = true;
    private boolean startFlag = false;
    //    private UhfReader mReader = UhfReader.getInstance();
    private Map<String, String> mMap;


    @Override

    public int getLayoutResID() {
        return R.layout.activity_detail;
    }

    @Override
    protected void init() {
        initActionBar("物料详情");
        initIntent();
        initChickBoxLisenter();
//        Thread thread = new InventoryThread();
//        thread.start();
        initData();
    }

    private void initData() {
        BarcodeDetail bean = new BarcodeDetail();
        bean.setEndGoodTime("hahahha");
        bean.setGoodCode("123455555");
        bean.setGoodName("哈哈哈");
        bean.setGoodNum(9999);
        bean.setGoodPrice(1.8888);
        bean.setGoodRack("ahfhashfhshf");
        bean.setOrderSpec("为维护服务费");
        bean.setStockNum(555);
        bean.setSupplier("第三方");


        mMap = new HashMap<>();
        mMap.put("EndGoodTime", bean.getEndGoodTime());
        mMap.put("GoodCode", bean.getGoodCode());
        mMap.put("GoodName", bean.getGoodName());
        mMap.put("GoodNum", bean.getGoodNum() + "");
        mMap.put("GoodPrice", bean.getGoodPrice() + "");
        mMap.put("GoodRack", bean.getGoodRack());
        mMap.put("OrderSpec", bean.getOrderSpec());
        mMap.put("StockNum", bean.getStockNum() + "");
        mMap.put("Supplier", bean.getSupplier());

        DetailAdapter adapter = new DetailAdapter();
        mLvMaterialDetail.setAdapter(adapter);

    }

    class DetailAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            int size = mMap.size();
            return size;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(DetailsActivity.this, R.layout.detail_listview_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Set<String> data = mMap.keySet();
            Object[] datas = data.toArray();
            holder.mDetialItemTitle.setText(datas[position] + "");
            holder.mDetailItemExplain.setText(mMap.get(datas[position]));
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.detial_item_title)
            TextView mDetialItemTitle;
            @BindView(R.id.detail_item_explain)
            TextView mDetailItemExplain;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    private void initChickBoxLisenter() {
        mDetialCbRfid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRFID = b;
            }
        });
    }
    private void initIntent() {
        Intent intent = getIntent();
        mMaterialNum = intent.getStringExtra(Constant.DETAIL_KEY);
        if (!TextUtils.isEmpty(mMaterialNum)) {
            //TODO 请求网络，获取物料详情
            startRequest();
        }
    }

    @OnClick(R.id.btn_scan_material_num)
    public void onClick() {
        if (isRFID) {
            //TODO 读取rfid
            String epc = mRFIDUtils.readOnceEPC();
            if (!TextUtils.isEmpty(epc)) {
                WarningToneUtil.play(1,0);
                mMaterialNum = epc;
            }else
                mMaterialNum = "";
        } else {
            String barCode = BarCodeUtils.getInstans().scan(5000);
            if (!TextUtils.isEmpty(barCode)) {
                WarningToneUtil.play(1, 0);
                mMaterialNum = barCode;
            } else {
                mMaterialNum = "";
            }
        }

        //设置editText，发起网路请求
        startRequest();
//        startFlag = !startFlag;
    }

    /**
     * 发起网络请求，获取物料详细信息
     */
    private void startRequest() {
        mEditTextMaterialNum.setText(mMaterialNum);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRFIDUtils.closeReaner();
    }

}
