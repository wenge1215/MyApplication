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
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/27.
 * 描述：电极上架
 */

public class ElectrodeShelvesActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final int ID_CODE = 1;       //身份码
    private static final int BIN_CODE = 2;      //储位码
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;
    @BindView(R.id.et_materiel_code_info)
    EditText mEtMaterielCodeInfo;
    @BindView(R.id.et_boom_code)
    EditText mEtBoomCode;
    @BindView(R.id.fl_detailed)
    FrameLayout mFlDetailed;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;

    private int mScanType = -1;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_electrode_shelves;
    }
    @Override
    protected void init() {
        initNBListener();
        inEditTextFocusLis();
    }

    private void initNBListener() {
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

    @OnClick({R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                mEtMaterielCodeInfo.setText("");
                mEtBoomCode.setText("");

                toast("取消");

                break;
            case R.id.btn_submit:
                toast("提交");
                break;
        }
    }



    private void inEditTextFocusLis() {
        mEtBoomCode.setOnFocusChangeListener(this);
        mEtMaterielCodeInfo.setOnFocusChangeListener(this);
    }

    @Override
    public void onClickF2() {
        hideKeyboard();
    }

    @Override
    public void onClickF1() {
        hideKeyboard();
    }


    @Override
    public void onClickLifeBtn() {
        hideKeyboard();
        String scan = BarCodeUtils.getInstans().scan(Constant.TIME_OUT);
        if (!TextUtils.isEmpty(scan)) {
            if (mScanType == ID_CODE) {
                mEtBoomCode.setText(scan);
                //TODO
            } else if (mScanType == BIN_CODE) {
                mEtMaterielCodeInfo.setText(scan);
            }
        }
    }

    @Override
    public void onClickRightBtn() {
        hideKeyboard();
        String epc = RFIDUtils.getInstance().readOnceEPC();
        if (!TextUtils.isEmpty(epc)) {
            if (mScanType == ID_CODE) {
                mEtBoomCode.setText(epc);
                //TODO
            } else if (mScanType == BIN_CODE) {
                mEtMaterielCodeInfo.setText(epc);
            }
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_boom_code:
                mScanType = ID_CODE;
                break;
            case R.id.et_materiel_code_info:
                mScanType = BIN_CODE;
                break;
        }
    }
}
