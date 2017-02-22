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
import com.innolux.adapter.MyLeftAdapter;
import com.innolux.adapter.MyRightAdapter;
import com.innolux.bean.BeanClass;
import com.innolux.utils.UtilTools;
import com.innolux.widget.SyncHorizontalScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/16.
 * 描述：收料详情
 */

public class ConsignessDetailFragmentUp extends Fragment {

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

    private List<String> leftlList;
    private List<BeanClass> models;         //rightModel
    private int mNowNum;
    private MyLeftAdapter mMyLeftAdapter;
    private MyRightAdapter mMyRightAdapter;

    public ConsignessDetailFragmentUp(Context context) {
        this.mContext = context;
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
        View view = inflater.inflate(R.layout.layout_tab_view, container, false);
        ButterKnife.bind(this, view);
        // 设置两个水平控件的联动
        mTitleHorsv.setScrollView(mContentHorsv);
        mContentHorsv.setScrollView(mTitleHorsv);

        // 添加左边内容数据
        //        mLeftTitleContainer.setBackgroundColor();
        mLeftTitleContainer.setBackgroundResource(R.color.common_plus_signin_btn_text_dark_disabled);
        mMyLeftAdapter = new MyLeftAdapter(mContext, models);
        mLeftContainerListview.setAdapter(mMyLeftAdapter);
        UtilTools.setListViewHeightBasedOnChildren(mLeftContainerListview);

        // 添加右边内容数据
        //        mRightTitleContainer.setBackgroundColor(Color.GRAY);
        mMyRightAdapter = new MyRightAdapter(mContext, models);
        mRightContainerListview.setAdapter(mMyRightAdapter);
        UtilTools.setListViewHeightBasedOnChildren(mRightContainerListview);

        initListener(mMyRightAdapter);
        return view;
    }

    private void initListener(MyRightAdapter myRightAdapter) {

        /**
         * 条目实收数量点击的监听
         */
        myRightAdapter.setOnNowNumClickListener(new MyRightAdapter.onNowNumClickListener() {
            @Override
            public void onNuwNumClick(int position) {
                setItemColor( position);
            }
        });

        /**
         * 条目实收数量改变的监听
         */
        myRightAdapter.setOnNowNumChangeListener(new MyRightAdapter.OnNowNumChangeListener() {
            @Override
            public void onNowNumChange(String nowNum, int position) {
                if (nowNum != null) {
                    mNowNum = Integer.valueOf(nowNum);
                    if (mItemListener != null) {
                        mItemListener.onlistItenNowNomChange(mNowNum, position);
                    }
                } else {
                    Toast.makeText(mContext, "您的输入为空，如要修改实收数量，请重新输入", Toast.LENGTH_LONG).show();
                }
            }
        });


        /**
         * 条目点击事件监听
         */
        mRightContainerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mItemListener != null) {
                    mItemListener.onListItemClick(position);
                }
                setItemColor(position);

            }
        });
    }

    private void setItemColor( int position) {
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


    //收货详情列表条目点击事件的监听器
    private OnConsignessDetailFragmentListItemListener mItemListener;

    public interface OnConsignessDetailFragmentListItemListener {
        void onListItemClick(int position);

        void onlistItenNowNomChange(int num, int position);
    }
    public void setItemListener(OnConsignessDetailFragmentListItemListener itemListener) {
        mItemListener = itemListener;
    }
}
