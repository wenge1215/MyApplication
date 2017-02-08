package com.innolux.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.innolux.R;
import com.innolux.app.Constant;
import com.innolux.app.MyApp;
import com.innolux.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/23  11:54.
 * 描述：设置
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.LinearLayout1)
    LinearLayout mLinearLayout1;
    @BindView(R.id.et_setting_addr)
    EditText mEtSettingAddr;
    @BindView(R.id.et_setting_length)
    EditText mEtSettingLength;
    @BindView(R.id.LinearLayout3)
    LinearLayout mLinearLayout3;
    @BindView(R.id.spinner_membank)
    Spinner mSpinnerMembank;
    @BindView(R.id.et_setting_pwd)
    EditText mEtSettingPwd;
    @BindView(R.id.LinearLayout2)
    LinearLayout mLinearLayout2;
    @BindView(R.id.bit_setting_save)
    Button mBitSettingSave;
    @BindView(R.id.btn_setting_cancel)
    Button mBtnSettingCancel;
    @BindView(R.id.iv_setting_minus)
    ImageView mIvSettingMinus;
    @BindView(R.id.et_setting_power)
    EditText mEtSettingPower;
    @BindView(R.id.iv_setting_add)
    ImageView mIvSettingAdd;
    private final String[] strMemBank = {"RESERVE", "EPC", "TID", "USER"};//RESERVE EPC TID USER分别对应0,1,2,3
    private int membank;        //数据区
    private int lockMembank;
    private int mDefaultPower;
    private int mAddr;
    private int mPower;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        initActionBar("设置");
        initView();
        initSpinnerAdapter();
        initListener();
    }

    private void initView() {
        mEtSettingPower.setText(mDefaultPower + "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPower = SPUtils.getInt(MyApp.getContext(), Constant.POWER, 26);
        mEtSettingPower.setText(mPower+"");
    }

    private void initSpinnerAdapter() {
        mSpinnerMembank.setAdapter(new ArrayAdapter<String>(this, R.layout.setting_spinner_item, strMemBank));
    }

    private void initListener() {
        mSpinnerMembank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                membank = arg2;
                lockMembank = arg2 + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @OnClick({R.id.bit_setting_save, R.id.btn_setting_cancel, R.id.iv_setting_minus, R.id.iv_setting_add})
    public void onClick(View view) {
        String power;
        switch (view.getId()) {
            case R.id.bit_setting_save:
                SPUtils.putInt(MyApp.getContext(),Constant.POWER,mPower);
                SPUtils.putInt(MyApp.getContext(),Constant.STARTLOACTION,mPower);
                SPUtils.putInt(MyApp.getContext(),Constant.ENDLOCATION,mPower);




                break;
            case R.id.btn_setting_cancel:
                break;
            case R.id.iv_setting_minus:
                power = mEtSettingPower.getText().toString().trim();
                mPower = Integer.valueOf(power);
                mPower--;
                if (mPower > 26 ) {
                    mPower = 26;
                }else if (mPower<16){
                    mPower = 16;
                }
                mEtSettingPower.setText(mPower+"");
                break;
            case R.id.iv_setting_add:
                power = mEtSettingPower.getText().toString().trim();
                mPower = Integer.valueOf(power);
                mPower++;
                if (mPower > 26 ) {
                    mPower = 26;
                }else if (mPower<16){
                    mPower = 16;
                }
                mEtSettingPower.setText(mPower+"");
                break;
        }
    }
}
