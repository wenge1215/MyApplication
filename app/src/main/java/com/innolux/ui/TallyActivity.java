package com.innolux.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.app.Constant;
import com.innolux.bean.Barcode;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.RFIDUtils;
import com.innolux.utils.WarningToneUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/20  9:36.
 * 描述：理货
 */
public class TallyActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.tv_bin_location_num)
    TextView mTvBinLocationNum;
    @BindView(R.id.editText_demand_num)
    EditText mEditTextDemandNum;
    @BindView(R.id.btn_scan_demand_num)
    Button mBtnScanDemandNum;
    @BindView(R.id.layout_bin_location_num)
    LinearLayout mLayoutBinLocationNum;
    @BindView(R.id.lv_tally_demand_detial)
    ListView mLvTallyDemandDetial;
    @BindView(R.id.cb_is_fixed)
    CheckBox mCbIsFixed;
    @BindView(R.id.cb_whold_out)
    CheckBox mCbWholdOut;
    @BindView(R.id.textView4)
    TextView mTextView4;
    @BindView(R.id.et_tally_pass_box_num)
    EditText mEtTallyPassBoxNum;
    @BindView(R.id.btn_scan_pass_box)
    Button mBtnScanPassBox;
    @BindView(R.id.et_bin_location_num)
    EditText mEtBinLocationNum;
    @BindView(R.id.btn_scan_bin_location)
    Button mBtnScanBinLocation;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.textView2)
    TextView mTextView2;
    @BindView(R.id.tv_material_info_bin_location_code)
    TextView mTvMaterialInfoBinLocationCode;
    @BindView(R.id.tv_material_info_bin_location)
    TextView mTvMaterialInfoBinLocation;
    @BindView(R.id.tv_material_info_code)
    TextView mTvMaterialInfoCode;
    @BindView(R.id.tv_material_info_name)
    TextView mTvMaterialInfoName;
    @BindView(R.id.tv_material_info_type)
    TextView mTvMaterialInfoType;
    @BindView(R.id.tv_material_info_stock)
    TextView mTvMaterialInfoStock;
    @BindView(R.id.layout_material_info)
    LinearLayout mLayoutMaterialInfo;
    @BindView(R.id.lv_boom_detial)
    ListView mLvBoomDetial;
    @BindView(R.id.et_out_lib_num)
    EditText mEtOutLibNum;
    @BindView(R.id.btn_complete_out_lib)
    Button mBtnCompleteOutLib;
    @BindView(R.id.btn_tally_complete)
    Button mBtnTallyComplete;
    @BindView(R.id.btn_tally_cancel)
    Button mBtnTallyCancel;
    @BindView(R.id.layout_boom_detial)
    LinearLayout mLayoutBoomDetial;
    @BindView(R.id.layout_outLid_num)
    LinearLayout mLayoutOutLidNum;
    @BindView(R.id.sv_bin_location_info)
    ScrollView mSvBinLocationInfo;
    @BindView(R.id.layout_scan_bin_location)
    LinearLayout mLayoutScanBinLocation;

    private List<Barcode> mBoomData = new ArrayList<>();
    private List<Barcode> mDeandData = new ArrayList<>();
    private boolean isFixed = false;
    private boolean isWholdOut = false;
    private int mOutLibNum = 0;
    private TallyDemandDetialAdapter mDemandDetialAdapter;
    private TallyBoomDetialAdapter mBoomDetialAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_tally;
    }

    @Override
    protected void init() {
//        initActionBar("理货作业");
        initCBNoamol();
        initCBListener();
        initData();
//        initAdapter();
//        WarningToneUtil.play(this, 1, 0);

    }

    private void initCBListener() {
        mCbIsFixed.setOnCheckedChangeListener(this);
        mCbWholdOut.setOnCheckedChangeListener(this);
    }

    /**
     * 需求单
     */
    private void initDemandAdapter() {
        mDemandDetialAdapter = new TallyDemandDetialAdapter();
        mLvTallyDemandDetial.setAdapter(mDemandDetialAdapter);

    }

    private void initBoomAdapter() {
        mBoomDetialAdapter = new TallyBoomDetialAdapter();
        mLvBoomDetial.setAdapter(mBoomDetialAdapter);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            Barcode barcode = new Barcode();
            barcode.setBarcode("21341412545445" + i);       //物料编码
            barcode.setName("储位" + i);
            barcode.setCount(new Random().nextInt(20));
            barcode.setBarcodeid(new Random().nextInt(20));//需求量
            mBoomData.add(barcode);
            mDeandData.add(barcode);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_is_fixed:
                isFixed = b;
                break;
            case R.id.cb_whold_out:
                isWholdOut = b;
                break;
        }
        initVisbility();
    }

    private void initVisbility() {
        if (isFixed) {       //固定储位
            mLayoutBoomDetial.setVisibility(View.GONE);
            mCbWholdOut.setVisibility(View.INVISIBLE);

//            mLayoutMaterialInfo.setVisibility(View.INVISIBLE);
            mSvBinLocationInfo.setVisibility(View.VISIBLE);
            mLayoutOutLidNum.setVisibility(View.VISIBLE);

        } else if (isWholdOut) {       //非固定储位，整出
            mCbIsFixed.setVisibility(View.INVISIBLE);
            mSvBinLocationInfo.setVisibility(View.GONE);
            mLayoutOutLidNum.setVisibility(View.GONE);
            mLayoutScanBinLocation.setVisibility(View.GONE);

            mCbWholdOut.setVisibility(View.VISIBLE);
            mLayoutBoomDetial.setVisibility(View.VISIBLE);
        } else if (!isFixed && !isWholdOut) {         //非固定储位，非整出
            initCBNoamol();

        }


    }

    private void initCBNoamol() {
        mLayoutBoomDetial.setVisibility(View.GONE);
        mSvBinLocationInfo.setVisibility(View.VISIBLE);

        mLayoutOutLidNum.setVisibility(View.VISIBLE);
        mCbWholdOut.setVisibility(View.VISIBLE);
        mCbIsFixed.setVisibility(View.VISIBLE);
        mLayoutScanBinLocation.setVisibility(View.VISIBLE);

    }

    class TallyDemandDetialAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDeandData != null && mDeandData.size() > 0) {
                return mDeandData.size();
            }
            return 0;
        }

        @Override
        public Barcode getItem(int i) {
            return mDeandData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(TallyActivity.this).inflate(R.layout.tally_list_view_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Barcode barcode = mDeandData.get(i);
            holder.mTallListViewItemMaterialNum.setText(barcode.getBarcode());
            holder.mTallListViewItemBinLoaction.setText(barcode.getName());
            holder.mTallListViewItemOutLibNum.setText(barcode.getCount() + "");
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tall_list_view_item_material_num)
            TextView mTallListViewItemMaterialNum;
            @BindView(R.id.tall_list_view_item_bin_loaction)
            TextView mTallListViewItemBinLoaction;
            @BindView(R.id.tall_list_view_item_outLib_num)
            TextView mTallListViewItemOutLibNum;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    class TallyBoomDetialAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mBoomData != null && mBoomData.size() > 0) {
                return mBoomData.size();
            }
            return 0;
        }

        @Override
        public Barcode getItem(int i) {
            return mBoomData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(TallyActivity.this).inflate(R.layout.tally_list_view_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Barcode barcode = mBoomData.get(i);
            holder.mTallListViewItemMaterialNum.setText(barcode.getBarcode());
            holder.mTallListViewItemBinLoaction.setText(barcode.getName());
            holder.mTallListViewItemOutLibNum.setText(barcode.getBarcodeid() + "");
            return view;
        }

        class ViewHolder {
            @BindView(R.id.tall_list_view_item_material_num)
            TextView mTallListViewItemMaterialNum;
            @BindView(R.id.tall_list_view_item_bin_loaction)
            TextView mTallListViewItemBinLoaction;
            @BindView(R.id.tall_list_view_item_outLib_num)
            TextView mTallListViewItemOutLibNum;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick({R.id.btn_scan_demand_num, R.id.btn_scan_pass_box, R.id.btn_scan_bin_location, R.id.btn_complete_out_lib, R.id.btn_tally_complete, R.id.btn_tally_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_demand_num:
                WarningToneUtil.play(1, 0);
                toast("点击Scan");
//                String name = mBoomData.get(2).getName();
//                System.out.println(name);
                initDemandAdapter();

                break;
            case R.id.btn_scan_pass_box:
                RFIDUtils rfidUtils = new RFIDUtils(this);
                String epc = rfidUtils.readOnceEPC();
                if (isWholdOut) {
                    if (!TextUtils.isEmpty(epc)) {
                        WarningToneUtil.play( 1, 0);
                        mEtTallyPassBoxNum.setText(epc);
                        //TODO 异步获取周转箱物料明细，并进行展示
                        initBoomAdapter();

                    } else {
                        toast("获取周转箱身份码失败，请点击重新获取");
                    }
                } else {
                    if (!TextUtils.isEmpty(epc)) {
                        WarningToneUtil.play(1, 0);
                        mEtTallyPassBoxNum.setText(epc);
                        //TODO 当非整出的时候获取到的周转箱码为出库周转箱码

                    } else {
                        toast("获取周转箱身份码失败，请点击重新获取");
                    }
                }

                break;
            case R.id.btn_scan_bin_location:
                String barCode = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
                if (!TextUtils.isEmpty(barCode)) {
                    WarningToneUtil.play(1, 0);
                    mEtBinLocationNum.setText(barCode);
                }

                if (isFixed || (!isFixed && !isWholdOut)) {
                    mLayoutMaterialInfo.setVisibility(View.VISIBLE);

                } else {
                    initBoomAdapter();
                }
                break;
            case R.id.btn_complete_out_lib:
                hideKeyboard();
                mOutLibNum = getOutLibNum();
                break;
            case R.id.btn_tally_complete:
                //TODO 提交理货
                if (isWholdOut) {
                    //储位周转箱和储位解除绑定关系
                    toast("储位周转箱和储位解除绑定关系");
                } else {
                    //理货周转箱与理货物料进行绑定关系
                    toast("理货周转箱与理货物料进行绑定关系");
                }
                break;
            case R.id.btn_tally_cancel:
                if (isWholdOut) {
                    mBoomData.clear();
                    if (mBoomDetialAdapter != null) {
                        mBoomDetialAdapter.notifyDataSetChanged();
                    }
                } else {
                    mLayoutMaterialInfo.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
    public int getOutLibNum() {
        String trim = mEtOutLibNum.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            return Integer.valueOf(trim);
        }
        return 0;
    }
}
