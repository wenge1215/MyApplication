package com.innolux.activity.activityImpl;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.app.Constant;
import com.innolux.utils.BarCodeUtils;
import com.innolux.utils.RFIDUtils;
import com.innolux.widget.NavigationBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/20  9:36.
 * 描述：退料绑定
 */
public class RollbackActivity extends BaseActivity implements View.OnFocusChangeListener {
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;
    @BindView(R.id.et_materiel_code_info)
    EditText mEtMaterielCodeInfo;
    @BindView(R.id.et_boom_code)
    EditText mEtBoomCode;
    @BindView(R.id.fl_detailed)
    FrameLayout mFlDetailed;
    @BindView(R.id.btn_clear)
    Button mBtnClear;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private int mScanType = -1;


    @Override
    public int getLayoutResID() {
        return R.layout.activity_roll_back;
    }

    @Override
    protected void init() {
        initNBlistener();
        initEditTextFocus();


    }

    private void initEditTextFocus() {
        mEtBoomCode.setOnFocusChangeListener(this);
        mEtMaterielCodeInfo.setOnFocusChangeListener(this);
    }

    private void initNBlistener() {
        mNavigationBar.setTitle("退料绑定");
        mNavigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void onClickBack() {
                finish();
            }

            @Override
            public void onClickMore() {
                toast("查询");
            }
        });
    }

    @OnClick({R.id.btn_clear, R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_boom_code:
                mScanType = 1;      //箱码
                break;

            case R.id.et_materiel_code_info:
                mScanType = 2;      //料号
                break;
        }
    }


    @Override
    public void onClickLifeBtn() {
        String scan = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
        onScanSucceed(scan);
    }

    @Override
    public void onClickRightBtn() {
        String epc = RFIDUtils.getInstance().readOnceEPC();
        onScanSucceed(epc);
    }

    private void onScanSucceed(String epc) {
        if (TextUtils.isEmpty(epc)) {
            toast("读取失败，请重新读取");
            return;
        }
        //根据焦点判断扫码类型
        if (mScanType == 1) {
            mEtBoomCode.setText(epc);
            //TODO send request
        }else if (mScanType == 2){
            mEtMaterielCodeInfo.setText(epc);
            //TODO send request
        }
    }
}
