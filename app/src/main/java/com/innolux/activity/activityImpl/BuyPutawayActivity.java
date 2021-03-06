package com.innolux.activity.activityImpl;

import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.bean.BeanClass;
import com.innolux.fragment.BuyPutawayFragment;
import com.innolux.utils.TestUtils;
import com.innolux.widget.NavigationBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/22.
 * 描述：请购料上架，BOOM上架和消耗品上架
 */

public class BuyPutawayActivity extends BaseActivity implements View.OnFocusChangeListener {
    private static final int IDENTITY_CODE = 1;
    private static final int BIN_LOCATION = 2;
    private static final int PUTAWAY_DETIAL = 3;

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

    private boolean mIsBoom = true;
    private ArrayList<BeanClass> mListData;
    private BuyPutawayFragment mBuyPutawayFragment;

    private int mScanType = 3;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_buy_pataway;
    }

    @Override
    public void onNegativeBtnClick(AlertDialog alert) {
        mIsBoom = false;
        initPutawayDetialFragment(mIsBoom);
        alert.dismiss();
    }

    @Override
    public void onPositiveBtnClick(AlertDialog alert) {
        mIsBoom = true;
        initPutawayDetialFragment(mIsBoom);
        alert.dismiss();
    }

    @Override
    protected void init() {
        initDialog("BOOM", "非BOOM");
        initNBlistener();
        initListener();
        initData();


    }

    private void initListener() {
        mEtMaterielCodeInfo.setOnFocusChangeListener(this);
        mEtBoomCode.setOnFocusChangeListener(this);
    }

    private void initFragmentListener() {
        mBuyPutawayFragment.setOnBuyPutawayAdapterListener(new BuyPutawayFragment.OnBuyPutawayAdapterListener() {
            @Override
            public void onInStorageNumChangeLis(String mInStorageNum, int position) {
                //TODO 修改入库数量的回调
                toast("修改入库数量的回调");
                BeanClass beanClass = mListData.get(position);
                beanClass.nowNum = mInStorageNum;
                mBuyPutawayFragment.notifyChangeDatas(mListData);
                mScanType = PUTAWAY_DETIAL;         //查询上架物料明细
            }

            @Override
            public void onListItemClick(int position) {
                //TODO 条目被选中的回调
                toast("条目被选中的回调");
                mScanType = PUTAWAY_DETIAL;         //查询上架物料明细
            }

        });
    }

    private void initData() {
        //测试数据
        mListData = new TestUtils().getListData();
    }

    /**
     * 请购上架作业详情
     *
     * @param b
     */
    private void initPutawayDetialFragment(boolean b) {
        mBuyPutawayFragment = new BuyPutawayFragment(this);
        mBuyPutawayFragment.setModels(mListData);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_detailed, mBuyPutawayFragment, "BuyPutawayFragment")
                .commit();

        initFragmentListener();     //fragment 内部操作的监听器
    }


    private void initNBlistener() {
        mNavigationBar.setTitle("请购料上架");
        mNavigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void onClickBack() {
                finish();
            }

            @Override
            public void onClickMore() {
                hideKeyboard();
                //TODO 查询
                switch (mScanType) {
                    case IDENTITY_CODE:         //焦点在身份码

                        break;
                    case BIN_LOCATION:         //焦点在储位码

                        break;
                    case PUTAWAY_DETIAL:
                        //模拟选中条码信息为1234765的item
                        for (int i = 0; i < mListData.size(); i++) {
                            BeanClass beanClass = mListData.get(i);
                            if (beanClass.codeInfo.equals("1234765")) {
                                mListData.remove(i);
                                mListData.add(0,beanClass);
                                mBuyPutawayFragment.notifyChangeDatas(mListData);
                                mBuyPutawayFragment.setCheckedItem(0);
                            }
                        }
                        break;
                }
            }
        });
    }

    @OnClick({R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                clearDetail();
                break;
            case R.id.btn_submit:
                submitDetail();
                break;
        }
    }

    /**
     * 提交作业记录
     */
    private void submitDetail() {
        if (mIsBoom) {
            toast("提交BOOM作业明细");
        } else {
            toast("提交非BOOM作业明细");
        }
    }


    /**
     * 清除作业记录记录
     */
    private void clearDetail() {
        mEtBoomCode.setText("");
        mEtMaterielCodeInfo.setText("");
        mListData.clear();
        mBuyPutawayFragment.notifyChangeDatas(mListData);
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_materiel_code_info:
                mScanType = IDENTITY_CODE;          //选中身份码
                break;
            case R.id.et_boom_code:
                mScanType = BIN_LOCATION;           //选中储位码
                break;
            default:
                mScanType = PUTAWAY_DETIAL;         //查询上架物料明细
                break;
        }
    }


    @Override
    public void onClickF1() {

    }

    @Override
    public void onClickF2() {

    }


    @Override
    public void onClickLifeBtn() {

    }

    @Override
    public void onClickRightBtn() {

    }


}
