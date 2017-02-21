package com.innolux.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.innolux.R;
import com.innolux.bean.BeanClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BOOMDetialRightAdapter extends BaseAdapter {
    private Context context;
    List<BeanClass> list;
    private int mNowNum;

    public BOOMDetialRightAdapter(Context context, List<BeanClass> models) {
        super();
        this.context = context;
        this.list = models;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.boom_detial_layout_right_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BeanClass beanClass = list.get(position);
        final String name = beanClass.name;
        viewHolder.mRightItemTextviewName.setText(name);
        viewHolder.mRightItemTextviewCodeInfo.setText(beanClass.codeInfo);
        viewHolder.mRightItemTextviewBuyNum.setText(beanClass.goodNum);
        viewHolder.mRightItemTextviewExistingNum.setText(beanClass.ExistingNum);
        viewHolder.mRightItemTextviewNowNum.setText(beanClass.nowNum);

        viewHolder.mRightItemTextviewNowNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView v1 = (TextView) v;
                Toast.makeText(context, v1.getText(), Toast.LENGTH_SHORT).show();
                //弹出dialog修改数量
                showAlterNowNum(position, name);

            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.right_item_textview_name)
        TextView mRightItemTextviewName;
        @BindView(R.id.right_item_textview_code_info)
        TextView mRightItemTextviewCodeInfo;
        @BindView(R.id.right_item_textview_buy_num)
        TextView mRightItemTextviewBuyNum;
        @BindView(R.id.right_item_textview_existing_num)
        TextView mRightItemTextviewExistingNum;
        @BindView(R.id.right_item_textview_now_num)
        TextView mRightItemTextviewNowNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void showAlterNowNum(final int position, String name) {

        final EditText inputServer = new EditText(context);
                inputServer.setGravity(Gravity.CENTER);

                inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
                builder.setTitle("实收");
                builder.setMessage("请更改序号为：" + position + "，" + "品名为：" + name + "的实际收货数量！");
                builder.setView(inputServer);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String nowNum = inputServer.getText().toString().trim();
                        if (TextUtils.isEmpty(nowNum)) {
                            Toast.makeText(BOOMDetialRightAdapter.this.context, "您的输入为空，如要修改实收数量，请重新输入", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (mOnNowNumClickListener != null) {
                            mOnNowNumClickListener.onNowNumClick(nowNum, position);
                        }
                        Toast.makeText(BOOMDetialRightAdapter.this.context, nowNum, Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

    }

    private onNowNumClickListener mOnNowNumClickListener;

    public interface onNowNumClickListener {
        void onNowNumClick(String nowNum, int position);
    }

    public void setOnNowNumClickListener(onNowNumClickListener onNowNumClickListener) {
        mOnNowNumClickListener = onNowNumClickListener;
    }
}
