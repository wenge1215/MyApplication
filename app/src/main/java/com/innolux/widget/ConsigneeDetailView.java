package com.innolux.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innolux.R;
import com.innolux.bean.BeanClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.innolux.utils.WarningToneUtil.context;


/**
 * 创建者： WENGE
 * 创建日期：2017/2/16.
 * 描述：有问题，无法使用
 */

public class ConsigneeDetailView extends RelativeLayout {
    @BindView(R.id.item_scroll_title)
    CHScrollView2 mItemScrollTitle;
    @BindView(R.id.hlistview_scroll_list)
    ListView mHlistviewScrollList;

    private Context mContext;
    ArrayList<BeanClass>  mDatas;

    //方便测试，直接写的public
    public HorizontalScrollView mTouchView;
    //装入所有的HScrollView
    protected List<CHScrollView2> mHScrollViewsList =new ArrayList<CHScrollView2>();
    private ScrollAdapter mAdapter;



    public ConsigneeDetailView(Context context) {
        this(context, null);
    }

    public ConsigneeDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.activity_hlistview, this);
        ButterKnife.bind(this);
        mContext = context;
        initViews();

    }
    private void initViews() {

        CHScrollView2 headerScroll = (CHScrollView2) findViewById(R.id.item_scroll_title);
        //添加头滑动事件
        mHScrollViewsList.add(mItemScrollTitle);
//        mListView = (ListView) findViewById(R.id.hlistview_scroll_list);


        if (mDatas != null) {
            mAdapter = new ScrollAdapter(mDatas);
            mHlistviewScrollList.setAdapter(mAdapter);
        }

    }
    public void addHViews(final CHScrollView2 hScrollView) {
        if(!mHScrollViewsList.isEmpty()) {
            int size = mHScrollViewsList.size();
            CHScrollView2 scrollView = mHScrollViewsList.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if(scrollX != 0) {
                mHlistviewScrollList.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViewsList.add(hScrollView);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt){
        for(CHScrollView2 scrollView : mHScrollViewsList) {
            //防止重复滑动
            if(mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }

    class ScrollAdapter extends BaseAdapter {

        //		private List<? extends Map<String, ?>> datas;
        private List<BeanClass> datas;

//        private int res;

//        private Context context;
        public ScrollAdapter(List<BeanClass> data) {
//            this.context = context;
            this.datas = data;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
           ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.common_item_hlistview, null);
                //第一次初始化的时候装进来
                //				addHViews((CHScrollView2) convertView.findViewById(R.id.item_chscroll_scroll));
                holder.id = (TextView) convertView.findViewById(R.id.tv_item_titlev);
                holder.name = (TextView) convertView.findViewById(R.id.item_datav1);
                holder.codeInfo = (TextView) convertView.findViewById(R.id.item_datav2);
                holder.goodNum = (TextView) convertView.findViewById(R.id.item_datav3);
                holder.goodsReceiptNum = (TextView) convertView.findViewById(R.id.item_datav4);
                holder.realityNum = (TextView) convertView.findViewById(R.id.item_datav5);
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.id.setText(datas.get(position).ID);
            holder.name.setText(datas.get(position).name);
            holder.codeInfo.setText(datas.get(position).codeInfo);
            holder.goodNum.setText(datas.get(position).goodNum);
            holder.goodsReceiptNum.setText(datas.get(position).ExistingNum);
            holder.realityNum.setText(datas.get(position).nowNum);
            holder.realityNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,datas.get(position).nowNum,Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;

        }

        @Override
        public int getCount() {
            if (datas != null) {
                return datas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (datas != null) {
                return datas.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        class ViewHolder{
            TextView id;
            TextView name;
            TextView codeInfo;
            TextView goodNum;
            TextView goodsReceiptNum;
            TextView realityNum;

        }
    }

//    public ArrayList<BeanClass> getListData() {
//        ArrayList<BeanClass> Beandata = new ArrayList<BeanClass>();
//        for (int i = 0; i < 80; i++) {
//            BeanClass bean = new BeanClass();
//            bean.ID = i + "";
//            bean.name = "螺丝" + i;
//            bean.codeInfo = "123476" + i;
//            bean.goodNum = (20 + i) + "";
//            bean.ExistingNum = (18 + i) + "";
//            bean.nowNum = 2 + "";
//            Beandata.add(bean);
//        }
//        return Beandata;
//    }

    public void getListData(ArrayList<BeanClass>  datas){
        mDatas = datas;
    }

}
