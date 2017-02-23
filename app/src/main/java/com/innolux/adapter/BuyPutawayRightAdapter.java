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

/**
 * 请购上架的adapter
 */
public class BuyPutawayRightAdapter extends BaseAdapter {
    private Context context;
    private List<BeanClass> list;
    private boolean mIsBoom;

    private int mNowNum;

    public BuyPutawayRightAdapter(Context context, List<BeanClass> models/*, boolean b*/) {
        super();
        this.context = context;
        this.list = models;
//        this.mIsBoom = b;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.buy_putaway_fragment_right_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BeanClass beanClass = list.get(position);

        viewHolder.mRightItemTextviewName.setText(beanClass.name);
        viewHolder.mRightItemTextviewBinLocation.setText(beanClass.ExistingNum);
        viewHolder.mRightItemTextviewInStorageNum.setText(beanClass.nowNum);
        viewHolder.mRightItemTextviewNG.setText(beanClass.ExistingNum);
        viewHolder.mRightItemTextviewPutawayNum.setText(beanClass.ExistingNum);
        viewHolder.mRightItemTextviewDetialId.setText(beanClass.ID);
        viewHolder.mRightItemTextviewMbmatNum.setText(beanClass.codeInfo);

        viewHolder.mRightItemTextviewInStorageNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnInStorageNumClickListener != null) {
                    mOnInStorageNumClickListener.onInStorageNumClick(position);
                }
                showAlterNowNum(position, beanClass.name);
            }
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.right_item_textview_name)
        TextView mRightItemTextviewName;
        @BindView(R.id.right_item_textview_bin_location)
        TextView mRightItemTextviewBinLocation;
        @BindView(R.id.right_item_textview_in_storage_num)
        TextView mRightItemTextviewInStorageNum;
        @BindView(R.id.right_item_textview_NG)
        TextView mRightItemTextviewNG;
        @BindView(R.id.right_item_textview_putaway_num)
        TextView mRightItemTextviewPutawayNum;
        @BindView(R.id.right_item_textview_detial_id)
        TextView mRightItemTextviewDetialId;
        @BindView(R.id.right_item_textview_mbmat_num)
        TextView mRightItemTextviewMbmatNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    private void showAlterNowNum(final int position, String name) {

        final EditText inputServer = new EditText(context);
        inputServer.setGravity(Gravity.CENTER);

        inputServer.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle("更改上架数量");
        //        builder.setMessage("请更改序号为：" + position + "，" + "品名为：" + name + "的实际收货数量！");
        builder.setView(inputServer);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String nowNum = inputServer.getText().toString().trim();
                if (TextUtils.isEmpty(nowNum)) {
                    Toast.makeText(BuyPutawayRightAdapter.this.context, "您的输入为空，如要修改实收数量，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }
                if (mOnInStorageNumChangeListener != null) {
                    mOnInStorageNumChangeListener.onInStorageNumChange(nowNum, position);
                }
                Toast.makeText(BuyPutawayRightAdapter.this.context, nowNum, Toast.LENGTH_SHORT).show();

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

    private OnInStorageNumClickListener mOnInStorageNumClickListener;

    public interface OnInStorageNumClickListener {
        //入库数量点击的监听
        void onInStorageNumClick(int position);
    }

    public void setOnInStorageNumClickListener(OnInStorageNumClickListener l) {
        mOnInStorageNumClickListener = l;
    }

    private OnInStorageNumChangeListener mOnInStorageNumChangeListener;

    public interface OnInStorageNumChangeListener {
        //实收数量改变的监听
        void onInStorageNumChange(String inStorageNum, int position);
    }

    public void setOnInStorageNumChangeListener(OnInStorageNumChangeListener l) {
        mOnInStorageNumChangeListener = l;
    }


}
