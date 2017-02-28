package com.innolux.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.innolux.Barcode;
import com.innolux.R;
import com.innolux.utils.RFIDUtils;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        initKeyReceiver();
        init();
    }

    private void initKeyReceiver() {
        //添加监听
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");
        registerReceiver(keyReceiver, filter);
    }

    //    public void initActionBar(String title) {
    //        ActionBar actionBar = getSupportActionBar();
    //        actionBar.setTitle(title);
    //        //        actionBar.setIcon(R.mipmap.ic_launcher);
    //        //        actionBar.setDisplayShowHomeEnabled(false);
    //        actionBar.setDisplayHomeAsUpEnabled(true);
    //
    //
    //    }
    //
    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case android.R.id.home:
    //                finish();
    //                break;
    //            default:
    //                break;
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }


    /**
     * 
     * @param leftStr
     * @param rightStr
     */
    public void initDialog(String leftStr, String rightStr) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.alter_dialog_now_num_view, null);
        final AlertDialog alertDialog = builder.setView(view).create();
        alertDialog.show();

        final Button leftBtn = (Button) view.findViewById(R.id.now_num_dialog_boom);
        Button rightBtn = (Button) view.findViewById(R.id.now_num_dialog_no_boom);
        leftBtn.setText(leftStr);
        leftBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.comm_text_size_22));
        rightBtn.setText(rightStr);
        rightBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.comm_text_size_22));
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveBtnClick(alertDialog);
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNegativeBtnClick(alertDialog);
            }
        });
    }

    /**
     *
     * @param alertDialog
     */
    public void onNegativeBtnClick(AlertDialog alertDialog) {

    }

    public void onPositiveBtnClick(AlertDialog alertDialog) {

    }

    protected void init() {

    }

    public abstract int getLayoutResID();

    protected void goTo(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected void showProgress(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    protected void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void longToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void hideKeyboard() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    int id = 1;

    /**
     * 将条码保存到集合中
     *
     * @param list
     * @param barcode
     * @return
     */
    public List<Barcode> sortAndadd(List<Barcode> list, String barcode) {

        Barcode goods = new Barcode();
        goods.setBarcode(barcode);
        goods.setBarcodeid(id);
        int temp = 1;        //记录条码出现的次数

        //集合为空，直接添加并返回
        if (list == null || list.size() == 0) {
            goods.setCount(temp);
            list.add(goods);
            return list;
        }
        //集合不为空
        for (int i = 0; i < list.size(); i++) {
            //获取的二维码已存在
            if (barcode.equals(list.get(i).getBarcode())) {
                temp = list.get(i).getCount() + temp;
                goods.setCount(temp);
                for (int j = i; j > 0; j--) {
                    list.set(j, list.get(j - 1));
                }
                list.set(0, goods);
                return list;
            }
        }
        //
        Barcode lastgoods = list.get(list.size() - 1);
        for (int j = list.size() - 1; j >= 0; j--) {

            if (j == 0) {
                goods.setCount(temp);
                list.set(j, goods);
            } else {
                list.set(j, list.get(j - 1));
            }
        }
        list.add(lastgoods);
        id++;
        return list;
    }

    /**
     * 对物理按键的监听
     */
    //TODO
    private long exitSytemTime;
    //监听按键消息
    private BroadcastReceiver keyReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            int keyCode = intent.getIntExtra("keyCode", 0);
            if (keyCode == 0) {//兼容H941
                keyCode = intent.getIntExtra("keycode", 0);
            }
            boolean keyDown = intent.getBooleanExtra("keydown", false);
            Log.e("", "KEYcODE = " + keyCode + ", Down = " + keyDown);
            if (keyDown) {
                //减少频繁触发

                if (System.currentTimeMillis() - exitSytemTime < 100) {
                    return;
                }
                exitSytemTime = System.currentTimeMillis();
                switch (keyCode) {
                    case 131:
                        onClickF1();
                        break;
                    case 132:
                        onClickF2();
                        break;
                    case 133:
                        onClickLifeBtn();
                        break;
                    case 134:
                        //                        toast("134");
                        break;
                    case 135:
                        onClickRightBtn();
                        break;
                    default:
                        break;

                }
            }
        }
    };

    /**
     * 点击右侧按钮
     */
    public void onClickRightBtn() {

    }

    /**
     * 点击左侧按钮
     */
    public void onClickLifeBtn() {
    }

    /**
     * 点击F2
     */
    public void onClickF2() {
    }

    /**
     * 点击F1
     */
    public void onClickF1() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭RFID读写器
        RFIDUtils.getInstance().closeReaner();

    }

    /**
     * 返回键的监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onClickBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 当返回键被点击时调用，子类需要对返回键做处理时调用该方法
     */
    public void onClickBack() {
        showOnBackDialog();
    }

    public void showOnBackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("是否退出当前操作");
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.create().show();
    }

}
