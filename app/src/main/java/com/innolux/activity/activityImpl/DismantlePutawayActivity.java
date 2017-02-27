package com.innolux.activity.activityImpl;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.app.Constant;
import com.innolux.utils.BarCodeUtils;
import com.innolux.widget.NavigationBar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.innolux.R.id.dismantel_detail_layout_group;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/22.
 * 描述：拆夹上架
 */

public class DismantlePutawayActivity extends BaseActivity implements View.OnFocusChangeListener {


    private static final int TYPE_FINISHED = 2;
    private static final int TYPE_PART = 1;
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;
    @BindView(R.id.dismantel_et_goos_name)
    EditText mDismantelEtGoosName;
    @BindView(R.id.dismantel_et_standard)
    EditText mDismantelEtStandard;
    @BindView(R.id.dismantel_et_type)
    EditText mDismantelEtType;
    @BindView(R.id.dismantel_et_bin_location)
    EditText mDismantelEtBinLocation;
    @BindView(R.id.et_materiel_code_info)
    EditText mEtMaterielCodeInfo;
    @BindView(R.id.dismantel_detail_et_goos_name)
    EditText mDismantelDetailEtGoosName;
    @BindView(R.id.dismantel_detail_et_type)
    EditText mDismantelDetailEtType;
    @BindView(R.id.dismantel_detail_bin_location)
    EditText mDismantelDetailBinLocation;
    @BindView(R.id.dismantel_detail_et_standard)
    EditText mDismantelDetailEtStandard;
    @BindView(R.id.dismantle_btn_save)
    Button mDismantleBtnSave;
    @BindView(R.id.dismantle_checkBox_grind)
    CheckBox mDismantleCheckBoxGrind;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.et_materiel_detail_code_info)
    EditText mEtMaterielDetailCodeInfo;
    @BindView(R.id.dismantel_detail_tv_titlw)
    TextView mDismantelDetailTvTitlw;
    @BindView(R.id.dismantel_tv_base_bin_location)
    TextView mDismantelTvBaseBinLocation;
    @BindView(R.id.dismantel_layout_base)
    LinearLayout mDismantelLayoutBase;
    @BindView(R.id.dismantel_tv_bin_location)
    TextView mDismantelTvBinLocation;
    @BindView(R.id.dismantel_layout_parts)
    LinearLayout mDismantelLayoutParts;
    @BindView(dismantel_detail_layout_group)
    LinearLayout mDismantelDetailLayoutGroup;


    private int mScanType = -1;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_dismantle_putaway;
    }

    @Override
    protected void init() {
        initListener();

    }

    private void initListener() {
        initNBlistener();
        initEditTextFocusLis();
    }

    private void initEditTextFocusLis() {
        mEtMaterielCodeInfo.setOnFocusChangeListener(this);
        mEtMaterielDetailCodeInfo.setOnFocusChangeListener(this);
    }

    private void initNBlistener() {
        mNavigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void onClickBack() {
                finish();
            }

            @Override
            public void onClickMore() {

            }
        });

    }

    @Override
    public void onClickF1() {
        hideKeyboard();

    }

    @Override
    public void onClickF2() {
        hideKeyboard();
    }

    @Override
    public void onClickLifeBtn() {
        hideKeyboard();
        String scan = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
        if (!TextUtils.isEmpty(scan)) {
            switch (mScanType) {
                case TYPE_PART:         //配件身份码或储位码
                    mEtMaterielDetailCodeInfo.setText(scan);
                    //TODO 请求获取配件信息
                    initPartInfo();


                    break;
                case TYPE_FINISHED:     //电极或刀具身份码:
                    //TODO 请求获取电极信息
                    mEtMaterielCodeInfo.setText(scan);

                    //初始化点击信息，数据来源于服务端
                    initFinishedInfo();
                    break;

            }
        }
    }

    /**
     * 模拟数据，初始化拆夹信息
     */
    private void initFinishedInfo() {
        mDismantelEtGoosName.setText("电极");
        mDismantelEtType.setText("良好");
        mDismantelEtBinLocation.setText("A3_B5");
        mDismantelEtStandard.setText("15*15*100");
    }

    /**
     * 初始化物料信息
     */
    private void initPartInfo() {

    }

    @Override
    public void onClickRightBtn() {
        hideKeyboard();
        initFinishedInfo();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_materiel_code_info:
                mScanType = TYPE_FINISHED;
                break;
            case R.id.et_materiel_detail_code_info:
                mScanType = TYPE_PART;
                break;
        }

    }

    @OnClick({R.id.dismantel_layout_base, R.id.dismantel_layout_parts,
            R.id.dismantle_btn_save, R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dismantel_layout_base:
                //TODO  获取底座的信息
                mDismantelDetailLayoutGroup.setVisibility(View.VISIBLE);

                break;
            case R.id.dismantel_layout_parts:
                //TODO  获取铜的信息

                break;
            case R.id.dismantle_btn_save:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_submit:
                break;

        }
    }
}
