package com.innolux.ui;

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

import com.innolux.R;
import com.innolux.app.Constant;
import com.innolux.bean.Barcode;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.RFIDUtils;
import com.innolux.utils.WarningToneUtil;
import com.innolux.widget.FavorTitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/17  10:41.
 * 描述：上架
 */
public class PutawayActivity extends BaseActivity {
    @BindView(R.id.favor_title_bar)
    FavorTitleBar mFavorTitleBar;
    @BindView(R.id.tv_bin_location_num)
    TextView mTvBinLocationNum;
    @BindView(R.id.editText_boom_num)
    EditText mEditTextBoomNum;
    @BindView(R.id.btn_scan_boom_num)
    Button mBtnScanBoomNum;
    @BindView(R.id.layout_bin_location_num)
    LinearLayout mLayoutBinLocationNum;
    @BindView(R.id.tv_pass_box_num)
    TextView mTvPassBoxNum;
    @BindView(R.id.editText_pass_box_num)
    EditText mEditTextPassBoxNum;
    @BindView(R.id.btn_scan_pass_box_num)
    Button mBtnScanPassBoxNum;
    @BindView(R.id.layout_pass_box_num)
    LinearLayout mLayoutPassBoxNum;
    @BindView(R.id.boom_detail)
    ListView mBoomDetail;
    @BindView(R.id.btn_complete)
    Button mBtnComplete;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    @BindView(R.id.cb_rfid)
    CheckBox mCbRfid;
    @BindView(R.id.layoout_boom)
    LinearLayout mLayooutBoom;
    @BindView(R.id.layout_no_boom)
    LinearLayout mLayoutNoBoom;
//    非boom上架信息
    @BindView(R.id.tv_bin_location_info_code)
    TextView mTvBinLocationInfoCode;
    @BindView(R.id.tv_bin_location_info_name)
    TextView mTvBinLocationInfoName;
    @BindView(R.id.tv_bin_location_info_type)
    TextView mTvBinLocationInfoType;
    @BindView(R.id.tv_bin_location_info_count)
    TextView mTvBinLocationInfoCount;
    @BindView(R.id.layout_bin_location_info)
    LinearLayout mLayoutBinLocationInfo;
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
    @BindView(R.id.et_material_info_count)
    EditText mEtMaterialInfoCount;
    @BindView(R.id.layout_material_info)
    LinearLayout mLayoutMaterialInfo;

    private boolean isBOOM = true;       //是否为BOOM收料
    private boolean isRFID = false;      //是否读取RFID
    private String mBinLocation;         //储位码
    private String mBox2Mateial;         //周转箱或储位码
    private RFIDUtils mRfidUtils;

    private List<Barcode> mDatas = new ArrayList<>();


    @Override

    public int getLayoutResID() {
        return R.layout.activity_putaway;
    }

    @Override
    protected void init() {
        initActionBar("上架作业");
        initListenter();
        initData();
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            Barcode barcode = new Barcode();
            barcode.setBarcode("1234567890" + i);
            barcode.setName("十字螺丝刀" + i);
            barcode.setCount(new Random().nextInt(10));
            barcode.setBarcodeid(i);
            mDatas.add(barcode);
        }
    }

    private void initListenter() {
        mFavorTitleBar.setFavorTitleBarListener(new FavorTitleBar.FavorTitleBarListener() {
            @Override
            public void onFavorTitleBarClick(String oriention) {
                if (FavorTitleBar.RIGHT.equals(oriention)) {        //非BOOM收料
                    isBOOM = !isBOOM;
                    mTvPassBoxNum.setText("物料码：");
                    mBtnScanPassBoxNum.setText("扫描物料码");
                    mLayooutBoom.setVisibility(View.GONE);
                    mLayoutNoBoom.setVisibility(View.VISIBLE);
                        /* 非boom上架是隐藏储位和物料信息*/
                    initInfo(View.INVISIBLE);

                } else if (FavorTitleBar.LEFT.equals(oriention)) {
                    isBOOM = !isBOOM;
                    mTvPassBoxNum.setText("周转箱码：");
                    mBtnScanPassBoxNum.setText("扫描周转箱");
                    mLayooutBoom.setVisibility(View.VISIBLE);
                    mLayoutNoBoom.setVisibility(View.GONE);

                }
            }
        });
        mCbRfid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isRFID = b;
            }
        });
    }

    @OnClick({R.id.btn_scan_boom_num, R.id.btn_scan_pass_box_num, R.id.btn_complete, R.id.btn_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_boom_num:        //扫描储位码
//                mBinLocation = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
                String barCode = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
                if (barCode != null && barCode.length() > 5) {
                    mBinLocation = barCode;
                    WarningToneUtil.play(1, 0);
                } else {
                    mBinLocation = "";
                    toast("二维码读取失败，请重新读取");

                }
                mEditTextBoomNum.setText(mBinLocation);
                break;
            case R.id.btn_scan_pass_box_num:    //扫描周转箱/物料码
                if (isRFID) {
                    if (mRfidUtils == null) {
                        mRfidUtils = new RFIDUtils(this);
                        WarningToneUtil.play(1, 0);
                    }
                    String epc = mRfidUtils.readOnceEPC();
                    if (epc != null && epc.length() > 10) {
                        mBox2Mateial = epc;
//                        WarningToneUtil.play(1, 0);
//                        initListView();
                    } else {
                        mBox2Mateial = "";
                        toast("RFID读取失败，请新读取");
                    }
                } else {
                    mBox2Mateial = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
//                    WarningToneUtil.play(1, 0);
//                    initListView();
                }
                if (mBox2Mateial != null && mBox2Mateial.length() > 10) {
                    WarningToneUtil.play(1, 0);
                    mEditTextPassBoxNum.setText(mBox2Mateial);
                    if (isBOOM) {
                        initListView();
                    } else {
                        initInfo(View.VISIBLE);
                    }
                }
                break;
            case R.id.btn_complete:     //完成上架
                //TODO 将物料码/周转箱或储位码上传到服务器，完成上架
                if (!TextUtils.isEmpty(mBinLocation) && !TextUtils.isEmpty(mBox2Mateial)) {
                    toast("上传成功");
                }
                break;
            case R.id.btn_clear:        //清空列表
                if (isBOOM) {
                    mDatas.clear();
                } else {
                    initInfo(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void initInfo(int visible) {
        mLayoutBinLocationInfo.setVisibility(visible);
        mLayoutMaterialInfo.setVisibility(visible);
    }

    private void initListView() {
        BoomDetialAdapter adapter = new BoomDetialAdapter();
        mBoomDetail.setAdapter(adapter);
    }

    class BoomDetialAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Barcode getItem(int i) {
            return mDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(PutawayActivity.this, R.layout.putaway_list_view_boom_item, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            Barcode barcode = mDatas.get(i);
            holder.mTextViewListItemId.setText(barcode.getBarcode());
            holder.mTextViewListItemBarcode.setText(barcode.getName());
            holder.mTvListItemBinLocation.setText(barcode.getCount() + "");
            holder.mTvListItemNum.setText(barcode.getBarcodeid() + "");
            return view;
        }

        class ViewHolder {
            @BindView(R.id.textView_list_item_id)
            TextView mTextViewListItemId;
            @BindView(R.id.textView_list_item_barcode)
            TextView mTextViewListItemBarcode;
            @BindView(R.id.tv_list_item_bin_location)
            TextView mTvListItemBinLocation;
            @BindView(R.id.tv_list_item_num)
            TextView mTvListItemNum;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
