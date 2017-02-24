package com.innolux.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.activity.activityImpl.BuyPutawayActivity;
import com.innolux.activity.activityImpl.DismantlePutawayActivity;
import com.innolux.widget.NavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_putaway_up;
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

            }
        });
    }

    @OnClick({R.id.putaway_tv_buy, R.id.putaway_tv_dismantle, R.id.putaway_tv_pole})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.putaway_tv_buy:
                goTo(BuyPutawayActivity.class);
                break;
            case R.id.putaway_tv_dismantle:
                goTo(DismantlePutawayActivity.class);
                break;
            case R.id.putaway_tv_pole:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
