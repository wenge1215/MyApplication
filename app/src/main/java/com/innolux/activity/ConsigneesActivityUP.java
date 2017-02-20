package com.innolux.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.innolux.R;
import com.innolux.bean.BeanClass;
import com.innolux.fragment.ConsignessDetailFragmentUp;
import com.innolux.ui.BaseActivity;
import com.innolux.utils.TestUtils;
import com.innolux.widget.FavorTitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/17  10:40.
 * 描述：收料
 */
public class ConsigneesActivityUP extends BaseActivity implements View.OnFocusChangeListener {

    @BindView(R.id.et_materiel_code_info)
    EditText mEtMaterielCodeInfo;
    @BindView(R.id.et_boom_code)
    EditText mEtBoomCode;
    @BindView(R.id.et_po_code)
    EditText mEtPoCode;
    @BindView(R.id.btn_query)
    Button mBtnScan;
    @BindView(R.id.fl_consignee_detailed)
    FrameLayout mFlConsigneeDetailed;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.consignees_favor_title_bar)
    FavorTitleBar mConsigneesFavorTitleBar;

    private int mCurrentClickItem;
    private ArrayList<BeanClass> mListData;
    private ConsignessDetailFragmentUp mConsignessDetailFragment;

    private int mScanType = -1;
    private boolean mIsBOOM;
    private boolean mIsLeft = true;


    @Override
    public int getLayoutResID() {
        return R.layout.activity_consignee_up;

    }

    @Override
    protected void init() {
        initActionBar("收料作业");
        initDialog();
        initData();     //初始化模拟数据
        initFavorTitleBarz();
        initEditTextFoucsLis();

    }

    private void initFavorTitleBarz() {
        mConsigneesFavorTitleBar.setFavorTitleBarListener(new FavorTitleBar.FavorTitleBarListener() {
            @Override
            public void onFavorTitleBarClick(String oriention) {
                if (FavorTitleBar.LEFT == oriention) {      //左边为选中状态
                    mIsLeft = true;
                } else if (FavorTitleBar.RIGHT == oriention) {  //右边被选中
                    mIsLeft = false;
                }
            }
        });


    }

    private void initEditTextFoucsLis() {
        mEtMaterielCodeInfo.setOnFocusChangeListener(this);
        mEtBoomCode.setOnFocusChangeListener(this);
        mEtPoCode.setOnFocusChangeListener(this);
    }

    //测试数据
    private void initData() {
        mListData = new TestUtils().getListData();
    }

    private void initDetailView(boolean b) {
        mIsBOOM = b;        //是否为boom收货
        if (mIsLeft) {      //如果是左边，展示作业详情
            displayOperationDetial();
        } else {            //展示明细
            displayDetial();
        }


    }

    private void displayDetial() {
        if (mIsBOOM) {

        } else {

        }


    }

    /**
     * 展示作业详情
     */
    private void displayOperationDetial() {
        mConsignessDetailFragment = new ConsignessDetailFragmentUp(this);
        mConsignessDetailFragment.setModels(mListData);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_consignee_detailed, mConsignessDetailFragment, "ConsignessDetailFragment")
                .commit();

        mConsignessDetailFragment.setItemListener(new ConsignessDetailFragmentUp.OnConsignessDetailFragmentListItemListener() {
            /**
             * 列表条目的点击事件
             * @param position
             */
            @Override
            public void onListItemClick(int position) {
                mCurrentClickItem = position;
            }

            /**
             * 实收数量改变时的回掉
             *
             * @param num 修改之后的数量
             * @param position  修改的条目
             */
            @Override
            public void onlistItenNowNomChange(int num, int position) {
                longToast("将：" + position + "行实收数量修改为：" + num);

                BeanClass beanClass = mListData.get(position);
                beanClass.nowNum = num + "";
                mConsignessDetailFragment.notifyChangeDatas(mListData);
            }
        });
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提示");
        builder.setMessage("是否为BOOM收料");

        builder.setNegativeButton("非BOOM收料", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initDetailView(false);
            }
        });
        builder.setPositiveButton("BOOM收料", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initDetailView(true);
            }
        });
        builder.create().show();

    }

    @OnClick({R.id.btn_query, R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                //根据焦点不同，获取不同条码；

                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_submit:
                break;
        }
    }

    @Override
    public void onClickF1() {
        toast("F1");
    }

    @Override
    public void onClickF2() {
        toast("F2");
    }

    @Override
    public void onClickLifeBtn() {
        toast("life");
    }

    @Override
    public void onClickRightBtn() {
        toast("right");
    }

    @Override
    public void onClickBack() {
        showOnBackDialog();
    }

    /**
     * EditText焦点监听
     *
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_materiel_code_info:
                mScanType = 1;
                break;
            case R.id.et_boom_code:
                mScanType = 2;
                break;
            case R.id.et_po_code:
                mScanType = 3;
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
