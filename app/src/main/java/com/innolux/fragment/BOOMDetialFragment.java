package com.innolux.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.innolux.R;
import com.innolux.adapter.BOOMDetialRightAdapter;
import com.innolux.adapter.MyLeftAdapter;
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

public class BOOMDetialFragment extends Fragment {

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

    private MyLeftAdapter mMyLeftAdapter;
    private BOOMDetialRightAdapter mRightAdapter;


    public BOOMDetialFragment(Context context) {
        this.mContext = context;
    }

    public void setModels(List<BeanClass> models) {
        this.models = models;
    }

    public void notifyChangeDatas(List<BeanClass> models) {
        this.models = models;
        mMyLeftAdapter.notifyDataSetChanged();
        mRightAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.boom_detial_layout_tab_view, container, false);
        ButterKnife.bind(this, view);
        // 设置两个水平控件的联动
        mTitleHorsv.setScrollView(mContentHorsv);
        mContentHorsv.setScrollView(mTitleHorsv);

        // 添加左边内容数据
//        mLeftTitleContainer.setBackgroundColor();
        mLeftTitleContainer.setBackgroundResource(R.color.common_google_signin_btn_text_dark_disabled);
        mMyLeftAdapter = new MyLeftAdapter(mContext, models);
        mLeftContainerListview.setAdapter(mMyLeftAdapter);
        UtilTools.setListViewHeightBasedOnChildren(mLeftContainerListview);

        // 添加右边内容数据
//        mRightTitleContainer.setBackgroundColor(Color.GRAY);
        mRightAdapter = new BOOMDetialRightAdapter(mContext,models);
        mRightContainerListview.setAdapter(mRightAdapter);
        UtilTools.setListViewHeightBasedOnChildren(mRightContainerListview);
        return view;
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
