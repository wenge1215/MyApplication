package com.innolux.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.innolux.R;
import com.innolux.bean.BeanClass;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者： WENGE
 * 创建日期： wenge on 2017/2/16.
 * 描述：消耗品详情
 */

public class ConsumeDetialFragment extends Fragment {

    @BindView(R.id.consume_detial_fragment_list_view)
    ListView mConsumeDetialFragmentListView;

    private Context mContext;
    private List<BeanClass> models;         //rightModel

    public ConsumeDetialFragment(Context context) {
        this.mContext = context;
    }

    public void setModels(List<BeanClass> models) {
        this.models = models;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consume, container, false);
        ButterKnife.bind(this, view);
        HashMap<Integer, HashMap<String, Object>> mapData = null;
        try {
            mapData = getDataDeclaredFields();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ConsumeDetialAdapter consumeDetialAdapter = new ConsumeDetialAdapter(mapData);
                mConsumeDetialFragmentListView.setAdapter(consumeDetialAdapter);
        return view;
    }

    public HashMap<Integer, HashMap<String, Object>> getDataDeclaredFields() throws IllegalAccessException {

        HashMap<Integer, HashMap<String, Object>> mapData = new HashMap<>();
        BeanClass beanClass = models.get(0);
        Field[] declaredFields = beanClass.getClass().getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            HashMap<String, Object> map = new HashMap<>();
            Field field = declaredFields[i];    //得到属性
            field.setAccessible(true);      //设置属性可以访问
            String str = field.getName();       //得到属性名
            Object o = null;    //得到属性值
            o = field.get(beanClass);
            map.put(str,o);
            mapData.put(i, map);

        }


        return mapData;
    }

    class ConsumeDetialAdapter extends BaseAdapter {
        private HashMap<Integer, HashMap<String, Object>>  mMap;

        public ConsumeDetialAdapter(HashMap<Integer, HashMap<String, Object>> dataDeclaredFields) {
            mMap = dataDeclaredFields;
        }

        @Override
        public int getCount() {
            BeanClass beanClass = models.get(0);
            if (mMap != null) {
                return mMap.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mMap != null) {
                mMap.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.consume_detial_fragment_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, Object> stringObjectHashMap = mMap.get(position);
            Set<String> str = stringObjectHashMap.keySet();
            Iterator<String> iterator = str.iterator();
            String next = null;
            if (iterator.hasNext()) {
                next = iterator.next();
            }
            switch (next) {
                case "ID":
                    holder.mConsumeDetialFragmentItemName.setText("ID：");
                    break;
                case "name":
                    holder.mConsumeDetialFragmentItemName.setText("品名：");
                    break;
                case "codeInfo":
                    holder.mConsumeDetialFragmentItemName.setText("条码信息：");
                    break;
                case "goodNum":
                    holder.mConsumeDetialFragmentItemName.setText("请购量：");
                    break;
                case "ExistingNum":
                    holder.mConsumeDetialFragmentItemName.setText("已收：");
                    break;
                case "nowNum":
                    holder.mConsumeDetialFragmentItemName.setText("实收：");
                    break;



            }

//            holder.mConsumeDetialFragmentItemName.setText(next);
            holder.mConsumeDetialFragmentItemDetial.setText(stringObjectHashMap.get(next).toString());


            return convertView;
        }

         class ViewHolder {
            @BindView(R.id.consume_detial_fragment_item_name)
            TextView mConsumeDetialFragmentItemName;
            @BindView(R.id.consume_detial_fragment_item_detial)
            TextView mConsumeDetialFragmentItemDetial;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    //收货详情列表条目点击事件的监听器
    private OnConsignessDetailFragmentListItemListener mItemListener;

    public interface OnConsignessDetailFragmentListItemListener {
        void onListItemClick(int position);

        void onlistItenNowNomChange(int num, int position);
    }

    public void setItemListener(OnConsignessDetailFragmentListItemListener itemListener) {
        mItemListener = itemListener;
    }
}
