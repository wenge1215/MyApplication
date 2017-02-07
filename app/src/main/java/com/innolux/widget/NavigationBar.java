package com.innolux.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innolux.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/20  13:45.
 * 描述：
 */

public class NavigationBar extends RelativeLayout {
    @BindView(R.id.iv_navigation_bars_back)
    ImageView mIvNavigationBarsBack;
    @BindView(R.id.iv_navigation_bars_title)
    TextView mIvNavigationBarsTitle;
    @BindView(R.id.iv_navigation_bars_more)
    ImageView mIvNavigationBarsMore;

    public NavigationBar(Context context) {
        this(context, null);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_navigation_bar, this);
        ButterKnife.bind(this);
        initAttribute(context, attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigationBar);
        String title = typedArray.getString(R.styleable.NavigationBar_NBTitle);
        boolean isShowBack = typedArray.getBoolean(R.styleable.NavigationBar_isShowBack, true);
        boolean isShowMore = typedArray.getBoolean(R.styleable.NavigationBar_isShowMore, false);

        if (isShowBack) {
            mIvNavigationBarsBack.setVisibility(VISIBLE);
        } else {
            mIvNavigationBarsBack.setVisibility(GONE);
        }
        if (isShowMore) {
            mIvNavigationBarsMore.setVisibility(VISIBLE);
        } else {
            mIvNavigationBarsMore.setVisibility(GONE);
        }
        if (title != null) {
            mIvNavigationBarsTitle.setText(title);
        }
        typedArray.recycle();
    }

    @OnClick({R.id.iv_navigation_bars_back, R.id.iv_navigation_bars_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_navigation_bars_back:
                if (mNavigationBarListener != null) {
                    mNavigationBarListener.onClickBack();
                }
                break;
            case R.id.iv_navigation_bars_more:
                if (mNavigationBarListener != null) {
                    mNavigationBarListener.onClickMore();
                }
                break;
        }
    }


    private NavigationBarListener mNavigationBarListener;

    public void setNavigationBarListener(NavigationBarListener navigationBarListener) {
        mNavigationBarListener = navigationBarListener;
    }

    public interface NavigationBarListener {
        void onClickBack();

        void onClickMore();
    }

}
