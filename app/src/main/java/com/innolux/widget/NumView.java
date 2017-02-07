package com.innolux.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.innolux.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/9  22:54.
 * 描述：
 */

public class NumView extends LinearLayout implements View.OnClickListener, View.OnLongClickListener {


    @BindView(R.id.ib_minus)
    ImageButton mIbMinus;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.ib_add)
    ImageButton mIbAdd;
    private int mCount = 0;

    public NumView(Context context) {
        this(context, null);
    }

    public NumView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.num_widget, this);
        ButterKnife.bind(this);
        initView();
        initListener();
    }

    private void initView() {
        mTvCount.setText(mCount + "");
    }

    private void initListener() {
        mIbAdd.setOnClickListener(this);
        mIbMinus.setOnClickListener(this);
//        mTvCount.setOnClickListener(this);
        mIbAdd.setOnLongClickListener(this);
        mTvCount.addTextChangedListener(new NumTextWatcher());
    }

    @Override
    public boolean onLongClick(View view) {


        return false;
    }

    class NumTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String s1 = s.toString();
            if (!s1.isEmpty()) {
                mCount = Integer.valueOf(s1);
            } else {
                mCount = 0;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_add:
                mCount++;
                mTvCount.setText(mCount + "");
                if (mOnNumViewClickListener != null) {
                    mOnNumViewClickListener.onAddClick(mCount);
                }
                break;
            case R.id.ib_minus:
                mCount--;
                mTvCount.setText(mCount + "");
                if (mOnNumViewClickListener != null) {
                    mOnNumViewClickListener.onMinusClick(mCount);
                }
                break;
//            case R.id.tv_count:
//
//                break;
        }
    }

    private OnNumViewClickListener mOnNumViewClickListener;

    public void setOnNumViewClickListener(OnNumViewClickListener listener) {
        mOnNumViewClickListener = listener;
    }

    public interface OnNumViewClickListener {
        void onAddClick(int count);

        void onMinusClick(int count);
    }

    public void setTtCount(String cont) {
        mCount = Integer.valueOf(cont);
        mTvCount.setText(cont);
    }
}
