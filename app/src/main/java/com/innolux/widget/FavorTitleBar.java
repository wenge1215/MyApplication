package com.innolux.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innolux.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavorTitleBar extends RelativeLayout implements View.OnClickListener {
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String BRCH = "back";
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.indicator_left)
    View mIndicatorLeft;
    @BindView(R.id.layout_left)
    RelativeLayout mLayoutLeft;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.indicator_right)
    View mIndicatorRight;
    @BindView(R.id.layout_right)
    RelativeLayout mLayoutRight;
    @BindView(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.activity_favor)
    RelativeLayout mActivityFavor;

    private FavorTitleBarListener mFavorTitleBarListener;

//    private TextView mTvTitle;
//    private RelativeLayout mLayoutLeft;
//    private RelativeLayout mLayoutRight;
//    private ImageView mImgBack;
//    private TextView mTvLeft;
//    private TextView mTvRight;
//    private View mIndicatorLeft;
//    private View mIndicatorRight;
//    private LinearLayout mLinearLayout;


    // 左侧被选中
    public static final int SELECTED_LEFT = 0;
    // 右侧被选中
    public static final int SELECTED_RIGHT = 1;

    public FavorTitleBar(Context context) {
        this(context, null);
    }

    public FavorTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.special_brand_favor_view, this);
        ButterKnife.bind(this);
        initAttribute(context, attrs);
        initEvent();
    }

    /**
     * 初始化自定义属性
     */
    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FavorTitleBar);
        String textLeft = typedArray.getString(R.styleable.FavorTitleBar_ftbLeftText);
        String textRight = typedArray.getString(R.styleable.FavorTitleBar_ftbRightText);
        boolean isShow = typedArray.getBoolean(R.styleable.FavorTitleBar_ftbIsShowBar, true);
        int anInt = typedArray.getInt(R.styleable.FavorTitleBar_svSelected, 0);

        /**
         *设置默认选中，默认左边
         */
        switch (anInt) {
            case SELECTED_LEFT:
                mTvLeft.setSelected(true);
                break;
            case SELECTED_RIGHT:
                mTvRight.setSelected(true);
                break;
            default:
                break;
        }
        if (isShow) {
            mLinearLayout.setVisibility(View.VISIBLE);
            mTvLeft.setText(textLeft);
            mTvRight.setText(textRight);

        } else {
            mLinearLayout.setVisibility(View.GONE);
        }
        //释放资源
        typedArray.recycle();
    }

    private void initEvent() {
        mLayoutLeft.setOnClickListener(this);
        mLayoutRight.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_left://左边被点击
                if (mFavorTitleBarListener != null) {
                    mFavorTitleBarListener.onFavorTitleBarClick(FavorTitleBar.LEFT);
                }
                mTvLeft.setSelected(true);      //是否选中
                mTvRight.setSelected(false);
//                Toast.makeText(getContext(), "左边被点击", Toast.LENGTH_SHORT).addShow();
                mIndicatorLeft.setVisibility(View.VISIBLE);
                mIndicatorRight.setVisibility(View.INVISIBLE);

                break;
            case R.id.layout_right://右边被点击
                if (mFavorTitleBarListener != null) {
                    mFavorTitleBarListener.onFavorTitleBarClick(FavorTitleBar.RIGHT);
//                    Toast.makeText(getContext(), "右边被点击", Toast.LENGTH_SHORT).addShow();
                }
                mTvLeft.setSelected(false);
                mTvRight.setSelected(true);
                mIndicatorLeft.setVisibility(View.INVISIBLE);
                mIndicatorRight.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public interface FavorTitleBarListener {
        void onFavorTitleBarClick(String oriention);
    }

    /**
     * 设置点击事件的监听器
     *
     * @param listener
     */
    public void setFavorTitleBarListener(FavorTitleBarListener listener) {
        mFavorTitleBarListener = listener;
    }

    public void setTvLeft(String text) {
        mTvLeft.setText(text);
    }

    public void setTvRight(String text) {
        mTvRight.setText(text);
    }

}
