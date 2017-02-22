package com.innolux.activity.activityImpl;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.widget.NavigationBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/22.
 * 描述：请购料上架，BOOM上架和消耗品上架
 */

public class BuyPutawayActivity extends BaseActivity {
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

    private boolean mISBoom = true;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_buy_pataway;
    }

    @Override
    public void onRightBtnClick(AlertDialog alertDialog) {
        mISBoom = true;
    }

    @Override
    public void onLeftBtnClick(AlertDialog alertDialog) {
        mISBoom = false;
    }

    @Override
    protected void init() {
        initDialog("BOOM", "非BOOM");
        initNBlistener();

    }

    private void initNBlistener() {
        mNavigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void onClickBack() {
                finish();
            }

            @Override
            public void onClickMore() {
                //TODO 查询
            }
        });
    }

    @OnClick({R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:

                break;
            case R.id.btn_submit:

                break;
        }
    }
}
