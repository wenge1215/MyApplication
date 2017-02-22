package com.innolux.activity;

import android.view.View;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.activity.activityImpl.BuyPutawayActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/17  10:41.
 * 描述：上架
 */
public class PutawayActivityUP extends BaseActivity {

    @BindView(R.id.putaway_tv_buy)
    TextView mPutawayTvBuy;
    @BindView(R.id.putaway_tv_dismantle)
    TextView mPutawayTvDismantle;
    @BindView(R.id.putaway_tv_pole)
    TextView mPutawayTvPole;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_putaway_up;
    }
    @OnClick({R.id.putaway_tv_buy, R.id.putaway_tv_dismantle, R.id.putaway_tv_pole})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.putaway_tv_buy:
                goTo(BuyPutawayActivity.class);
                break;
            case R.id.putaway_tv_dismantle:
                break;
            case R.id.putaway_tv_pole:
                break;
        }
    }
}
