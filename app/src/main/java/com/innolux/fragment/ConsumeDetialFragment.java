package com.innolux.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innolux.R;
import com.innolux.bean.BeanClass;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/16.
 * 描述：收料详情
 */

public class ConsumeDetialFragment extends Fragment {

    private Context mContext;
    private List<BeanClass> models;         //rightModel

    public ConsumeDetialFragment(Context context) {
        this.mContext = context;
    }

    public void setModels(List<BeanClass> models) {
        this.models = models;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tab_view, container, false);
        ButterKnife.bind(this, view);

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
