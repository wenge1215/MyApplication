package com.innolux.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.innolux.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建者： WENGE .
 * 创建日期： 2017/1/17  23:07.
 * 描述：
 */

public class Test extends BaseActivity {
    @BindView(R.id.text_list_view)
    ListView textListView;
    private List<String> mData;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_test;
    }

    @Override
    protected void init() {
        initData();
        View listHeard = View.inflate(this,R.layout.list_heard, null);
        textListView.addHeaderView(listHeard);
        textListView.setAdapter(new MyAdapter());
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add("10086" + i);
        }
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mData.size();
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

            if (convertView == null) {
                convertView = LayoutInflater.from(Test.this).inflate(R.layout.test_list_view_item, null);
            }

            return convertView;
        }
    }

}
