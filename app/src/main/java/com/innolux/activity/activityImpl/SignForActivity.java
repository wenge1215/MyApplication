package com.innolux.activity.activityImpl;

import android.view.View;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.activity.BaseActivity;
import com.innolux.widget.NavigationBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/20  9:36.
 * 描述：工位签收
 */
public class SignForActivity extends BaseActivity {
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;
    @BindView(R.id.station_sign_for)
    TextView mStationSignFor;
    @BindView(R.id.manage_sign_for)
    TextView mManageSignFor;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_sign_for;
    }

    @Override
    protected void init() {
        initNBlistener();
    }

    private void initNBlistener() {
        mNavigationBar.setTitle("签收作业");
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

    @OnClick({R.id.station_sign_for, R.id.manage_sign_for})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.station_sign_for:
                //工位签收
                toast("工位签收");

                break;
            case R.id.manage_sign_for:
                //组立签收
                toast("组立签收");

                break;
        }
    }
}
