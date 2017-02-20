package com.innolux.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.innolux.fragment.ConsignessDetailFragment;

public class CHScrollView2 extends HorizontalScrollView {
    //    public CHScrollView2(Context context) {
    //        super(context);
    //    }
    //
    //    public CHScrollView2(Context context, AttributeSet attrs) {
    //        super(context, attrs);
    //    }
    private static ConsignessDetailFragment mFragment;

    //    HListViewActivity activity;


    public CHScrollView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //        activity = (HListViewActivity) context;
    }


    public CHScrollView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        //        activity = (HListViewActivity) context;
    }

    public CHScrollView2(Context context) {
        super(context);
        //        activity = (HListViewActivity) context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //进行触摸赋值
        //        activity.mTouchView = this;
        //        int startX = 0;
        //        int currentX = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int currentX = getScrollX();

                if (currentX > 0) {
                    mFragment.mTouchView = this;
                    return super.onTouchEvent(ev);
                }
                break;

        }


        return false;

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //当当前的CHSCrollView被触摸时，滑动其它
        if (mFragment.mTouchView == this) {
            //            activity.onScrollChanged(l, t, oldl, oldt);
            mFragment.onScrollChanged(l, t, oldl, oldt);
        } else {
            super.onScrollChanged(l, t, oldl, oldt);
        }

        //        if (activity.mTouchView == this) {
        //            activity.onScrollChanged(l, t, oldl, oldt);
        //            activity.onScrollChanged(l, t, oldl, oldt);
        //        } else {
        //            super.onScrollChanged(l, t, oldl, oldt);
        //        }
    }

    public void setObject(ConsignessDetailFragment o) {
        mFragment = o;
    }
}
