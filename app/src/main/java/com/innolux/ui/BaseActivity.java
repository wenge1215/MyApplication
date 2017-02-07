package com.innolux.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.innolux.Barcode;
import com.innolux.utils.RFIDUtils;

import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private InputMethodManager mInputMethodManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        ButterKnife.bind(this);
        init();
    }
    public void initActionBar(String title) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
//        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RFIDUtils rfidUtils = new RFIDUtils(this);
        rfidUtils.closeReaner();
    }

}
