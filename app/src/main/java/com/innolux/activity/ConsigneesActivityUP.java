package com.innolux.activity;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.innolux.R;
import com.innolux.bean.BeanClass;
import com.innolux.fragment.BOOMDetialFragment;
import com.innolux.fragment.ConsignessDetailFragmentUp;
import com.innolux.fragment.ConsumeDetialFragment;
import com.innolux.utils.TestUtils;
import com.innolux.widget.FavorTitleBar;
import com.innolux.widget.NavigationBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.innolux.R.id.fl_consignee_detailed;

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
    @BindView(fl_consignee_detailed)
    FrameLayout mFlConsigneeDetailed;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.consignees_favor_title_bar)
    FavorTitleBar mConsigneesFavorTitleBar;
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;

    private int mCurrentClickItem;
    private ArrayList<BeanClass> mListData;
    private ConsignessDetailFragmentUp mConsignessDetailFragment;

    private int mScanType = -1;
    private boolean mIsBOOM;
    private boolean mIsLeft = true;
    private int CODE_INFO = 1;
    private int BOOM_CODE = 2;
    private int PO_CODE = 3;


    @Override
    public int getLayoutResID() {
        return R.layout.activity_consignee_up;

    }

    @Override
    public void onRightBtnClick(AlertDialog alertDialog) {
        mIsBOOM = false;
        initDetailView();
        alertDialog.dismiss();
    }

    @Override
    public void onLeftBtnClick(AlertDialog alertDialog) {
        mIsBOOM = true;
        initDetailView();
        alertDialog.dismiss();
        Log.e("tag","misBOOM:"+mIsBOOM);
    }


    @Override
    protected void init() {
        mNavigationBar.setNavigationBarListener(new NavigationBar.NavigationBarListener() {
            @Override
            public void onClickBack() {
                finish();
            }

            @Override
            public void onClickMore() {
                toast("query");
                hideKeyboard();
            }
        });
        initDialog("BOOM","非BOOM");
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
                initDetailView();
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

    private void initDetailView() {

        if (mIsLeft) {      //如果是左边，展示作业详情
            displayOperationDetial();
        } else {            //展示明细
            displayDetial();
        }


    }

    private void displayDetial() {
        if (mIsBOOM) {
            toast("boom详情");
            //TODO fragment 来实现  BOOMDetialFragment
            BOOMDetialFragment boomDetialFragment = new BOOMDetialFragment(this);
            boomDetialFragment.setModels(mListData);
            getSupportFragmentManager().beginTransaction()
                    .replace(fl_consignee_detailed, boomDetialFragment, "BOOMDetialFragment")
                    .commit();
        } else {
            toast("消耗品详情");
            //TODO fragment 来实现   ConsumeDetialFragment
            ConsumeDetialFragment consumeDetialFragment = new ConsumeDetialFragment(this);
            consumeDetialFragment.setModels(mListData);
            getSupportFragmentManager().beginTransaction()
                    .replace(fl_consignee_detailed, consumeDetialFragment, "ConsumeDetialFragment")
                    .commit();
        }


    }

    /**
     * 展示作业详情
     */
    private void displayOperationDetial() {
        mConsignessDetailFragment = new ConsignessDetailFragmentUp(this);
        mConsignessDetailFragment.setModels(mListData);
        getSupportFragmentManager().beginTransaction()
                .replace(fl_consignee_detailed, mConsignessDetailFragment, "ConsignessDetailFragment")
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
                mCurrentClickItem = position;
                longToast("将：" + position + "行实收数量修改为：" + num);

                BeanClass beanClass = mListData.get(position);
                beanClass.nowNum = num + "";
                mConsignessDetailFragment.notifyChangeDatas(mListData);
            }
        });
    }

//    private void initDialog() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View view = inflater.inflate(R.layout.alter_dialog_now_num_view, null);
//        final AlertDialog alertDialog = builder.setView(view).create();
//        alertDialog.show();
//
//        Button lifeBtn = (Button) view.findViewById(R.id.now_num_dialog_boom);
//        Button rightBtn = (Button) view.findViewById(R.id.now_num_dialog_no_boom);
//
//        lifeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIsBOOM = true;
//                initDetailView();
//                alertDialog.dismiss();
//
//            }
//        });
//
//        rightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIsBOOM = false;
//                initDetailView();
//                alertDialog.dismiss();
//            }
//        });
//
//    }

    @OnClick({R.id.btn_query, R.id.btn_cancel, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                //根据焦点不同，获取不同条码；
                if (mScanType == CODE_INFO) {
                    toast("扫描条码信息");

                } else if (mScanType == BOOM_CODE) {
                    toast("扫描周转箱码");
                } else if (mScanType == PO_CODE) {
                    toast("扫描po码");
                }

                break;
            case R.id.btn_cancel:
                mListData.clear();
                mConsignessDetailFragment.notifyChangeDatas(mListData);

                break;
            case R.id.btn_submit:
                if (mIsBOOM) {
                    toast("BOOM提交");
                } else {
                    toast("非BOOM提交");
                }


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
                mScanType = CODE_INFO;
                break;
            case R.id.et_boom_code:
                mScanType = BOOM_CODE;
                break;
            case R.id.et_po_code:
                mScanType = PO_CODE;
                break;
        }
    }
}
