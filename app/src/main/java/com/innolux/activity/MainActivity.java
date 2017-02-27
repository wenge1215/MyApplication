package com.innolux.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.ui.AbnormalActivity;
import com.innolux.ui.BInLocationActivity;
import com.innolux.ui.ClampingActivity;
import com.innolux.ui.DetailsActivity;
import com.innolux.activity.activityImpl.SignForActivity;
import com.innolux.activity.activityImpl.RollbackActivity;
import com.innolux.ui.SeparateActivity;
import com.innolux.ui.SettingActivity;
import com.innolux.ui.TakeStockActivity;
import com.innolux.ui.TallyActivity;
import com.innolux.widget.NavigationBar;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.gtid_view)
    GridView mGtidView;
    @BindView(R.id.navigation_bar)
    NavigationBar mNavigationBar;

    private List<Map<String, Object>> data_list;

    // 图片封装为一个数组
    private int[] icon = {R.mipmap.data_syn, R.mipmap.backlog, R.mipmap.a,
            R.mipmap.b, R.mipmap.setting, R.mipmap.log_record,
            R.mipmap.operation_recode, R.mipmap.production, R.mipmap.purchase_manager,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String[] iconName = {"收料作业", "上架作业", "理货作业",
            "装夹作业", "拆夹作业", "回退绑定", "储位调整",
            "盘点作业", "工位签收",
            "异常作业", "物料查询", "设置"};

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initNavigationBar();
        //配置适配器
        mGtidView.setAdapter(new GridAdapter());
        mGtidView.setOnItemClickListener(mOnItemClickListener);
    }

    private void initNavigationBar() {
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

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case 0:
                    //                    toast(getResources().getString(R.string.repertory_search));
                    goTo(ConsigneesActivityUP.class);
                    break;
                case 1:
                    //                    toast(getResources().getString(R.string.goods_search));
                    goTo(PutawayActivityUP.class);
                    break;
                case 2:
                    toast("理货作业");
                    goTo(TallyActivity.class);
                    break;
                case 3:
                    toast("装夹作业");
                    goTo(ClampingActivity.class);

                    break;
                case 4:
                    toast("拆夹作业");
                    goTo(SeparateActivity.class);

                    break;
                case 5:
                    toast("回退绑定");
                    goTo(RollbackActivity.class);

                    break;
                case 6:
                    toast("储位调整");
                    goTo(BInLocationActivity.class);
                    break;
                case 7:
                    toast("盘点作业");
                    goTo(TakeStockActivity.class);
                    break;
                case 8:
                    toast("工位签收");
                    goTo(SignForActivity.class);
                    break;
                case 9:
                    toast("异常作业");
                    goTo(AbnormalActivity.class);
                    break;
                case 10:
                    toast("物料查询");
                    goTo(DetailsActivity.class);
                    break;
                case 11:
                    toast("设置");
                    goTo(SettingActivity.class);
                    break;
                default:
                    break;
            }
        }
    };

    public class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = View.inflate(MainActivity.this, R.layout.item_main_grid, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mItemMainGridIcon.setImageResource(icon[i]);
            holder.mItemMainGridDesc.setText(iconName[i]);
            return view;
        }

        class ViewHolder {
            @BindView(R.id.item_main_grid_icon)
            ImageView mItemMainGridIcon;
            @BindView(R.id.item_main_grid_desc)
            TextView mItemMainGridDesc;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
