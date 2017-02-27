package com.innolux.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.innolux.R;
import com.innolux.adapter.BuyPutawayRightAdapter;
import com.innolux.adapter.MyLeftAdapter;
import com.innolux.bean.BeanClass;
import com.innolux.utils.UtilTools;
import com.innolux.widget.SyncHorizontalScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/22.
 * 描述：请购上架的作业详情
 */

public class BuyPutawayFragment extends Fragment {
    @BindView(R.id.left_title_container)
    LinearLayout mLeftTitleContainer;
    @BindView(R.id.right_title_container)
    LinearLayout mRightTitleContainer;
    @BindView(R.id.title_horsv)
    SyncHorizontalScrollView mTitleHorsv;
    @BindView(R.id.left_container_listview)
    ListView mLeftContainerListview;
    @BindView(R.id.left_container)
    LinearLayout mLeftContainer;
    @BindView(R.id.right_container_listview)
    ListView mRightContainerListview;
    @BindView(R.id.right_container)
    LinearLayout mRightContainer;
    @BindView(R.id.content_horsv)
    SyncHorizontalScrollView mContentHorsv;


    private Context mContext;
    private List<BeanClass> models;         //rightModel
    private boolean mIsBoom;

    private MyLeftAdapter mMyLeftAdapter;
    private BuyPutawayRightAdapter mMyRightAdapter;

    public BuyPutawayFragment(Context context/*,boolean b*/) {
        this.mContext = context;
        //        this.mIsBoom = b;
    }

    public void setModels(List<BeanClass> models) {
        this.models = models;
    }

    public void notifyChangeDatas(List<BeanClass> models) {
        this.models = models;
        mMyLeftAdapter.notifyDataSetChanged();
        mMyRightAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_putaway_fragment_layout, null);
        ButterKnife.bind(this, view);

        // 设置两个水平控件的联动
        mTitleHorsv.setScrollView(mContentHorsv);
        mContentHorsv.setScrollView(mTitleHorsv);

        mLeftTitleContainer.setBackgroundResource(R.color.common_google_signin_btn_text_dark_disabled);
        mMyLeftAdapter = new MyLeftAdapter(mContext, models);
        mLeftContainerListview.setAdapter(mMyLeftAdapter);
        UtilTools.setListViewHeightBasedOnChildren(mLeftContainerListview);

        mMyRightAdapter = new BuyPutawayRightAdapter(mContext, models/*,mIsBoom*/);
        mRightContainerListview.setAdapter(mMyRightAdapter);
        UtilTools.setListViewHeightBasedOnChildren(mRightContainerListview);

        initListener();
        return view;
    }

    private void initListener() {
        mMyRightAdapter.setOnInStorageNumChangeListener(new BuyPutawayRightAdapter.OnInStorageNumChangeListener() {
            @Override
            public void onInStorageNumChange(String inStorageNum, int position) {
                if (inStorageNum != null) {
                    //                   int  mInStorageNum = Integer.valueOf(inStorageNum);
                    if (mOnBuyPutawayAdapterListener != null) {
                        mOnBuyPutawayAdapterListener.onInStorageNumChangeLis(inStorageNum, position);
                    }
                } else {
                    Toast.makeText(mContext, "您的输入为空，如要修改数量，请重新输入", Toast.LENGTH_LONG).show();
                }
            }
        });

        mMyRightAdapter.setOnInStorageNumClickListener(new BuyPutawayRightAdapter.OnInStorageNumClickListener() {
            @Override
            public void onInStorageNumClick(int position) {
                setItemColor(position);
            }
        });

        //ListView 条目点击事件
        mRightContainerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnBuyPutawayAdapterListener != null) {
                    mOnBuyPutawayAdapterListener.onListItemClick(position);
                }
                setItemColor(position);
            }
        });

    }

    private void setItemColor(int position) {
        int childCount = mRightContainerListview.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mRightContainerListview.getChildAt(i);
            if (position == i) {
                childAt.setBackgroundColor(Color.RED);
            } else {
                childAt.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private OnBuyPutawayAdapterListener mOnBuyPutawayAdapterListener;

    public void setCheckedItem(int i) {
        setItemColor(0);
//        for (int j = 0; j < models.size(); j++) {
//            BeanClass beanClass = models.get(j);
//            int codeInfo = Integer.valueOf(beanClass.codeInfo);
//            if (codeInfo == i) {
////                mRightContainerListview.setSelected(true);
////                mLeftContainerListview.setSelected(true);
////                mLeftContainerListview.smoothScrollToPosition(j);
//                mRightContainerListview.smoothScrollToPosition(j);
//                setItemColor(j);
////                mMyRightAdapter.notifyDataSetChanged();
//            }
//        }


    }

    public interface OnBuyPutawayAdapterListener {
        void onInStorageNumChangeLis(String mInStorageNum, int position);

        void onListItemClick(int position);
    }

    public void setOnBuyPutawayAdapterListener(OnBuyPutawayAdapterListener onBuyPutawayAdapterListener) {
        mOnBuyPutawayAdapterListener = onBuyPutawayAdapterListener;
    }
}
