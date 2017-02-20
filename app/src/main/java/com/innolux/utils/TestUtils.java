package com.innolux.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.bean.BeanClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/15.
 * 描述：模拟数据
 */

public class TestUtils {
    public String ID;
    public String name;
    public String codeInfo;
    public String goodNum;
    public String goodsReceiptNum;
    public String realityNum;
    String[] cols = {
            "ID", "name", "codeInfo", "goodNum",
            "ExistingNum", "nowNum"};
    private List<Map<String, String>> mMapList;

    public TestUtils() {
        getListOrMap();
    }

    public ArrayList<BeanClass> getListData() {
        ArrayList<BeanClass> Beandata = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            BeanClass bean = new BeanClass();
            bean.ID = i + "";
            bean.name = "螺丝" + i;
            bean.codeInfo = "123476" + i;
            bean.goodNum = (20 + i) + "";
            bean.ExistingNum = (18 + i) + "";
            bean.nowNum = 2 + "";
            Beandata.add(bean);
        }
        return Beandata;
    }

    public void getListOrMap() {
        mMapList = new ArrayList<>();
        HashMap<String, String> hashMap = new HashMap<>();
        ArrayList<BeanClass> listData = getListData();

        for (int i = 0; i < listData.size(); i++) {
            int num = 0;
            hashMap.put(cols[0], listData.get(i).ID);
            hashMap.put(cols[1], listData.get(i).name);
            hashMap.put(cols[2], listData.get(i).codeInfo);
            hashMap.put(cols[3], listData.get(i).goodNum);
            hashMap.put(cols[4], listData.get(i).ExistingNum);
            hashMap.put(cols[5], listData.get(i).nowNum);
            mMapList.add(hashMap);
        }
    }

    public TestAdapter getAdapter(Context context) {
        ArrayList<BeanClass> listData = getListData();
        TestAdapter testAdapter = new TestAdapter(context, listData);
        return testAdapter;
    }

    class TestAdapter extends BaseAdapter {
        Context mContext;
        List<BeanClass> mDatas;

        public TestAdapter(Context context, List<BeanClass> datas) {
            mContext = context;
            mDatas = datas;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.test_view, null);
                holder = new ViewHolder();
                holder.mTextView= (TextView) convertView;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTextView.setText(mDatas.get(position).name);
            holder.mTextView.setTextColor(Color.BLACK);
            return convertView;
        }
        class ViewHolder{
            TextView mTextView;
        }
    }
}
