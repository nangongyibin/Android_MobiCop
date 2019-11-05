package com.ngyb.mobicop.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.adapter.LockAdapter;
import com.ngyb.mobicop.contract.LockContract;
import com.ngyb.mobicop.presenter.LockPresenter;
import com.ngyb.mvpbase.BaseMvpActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 17:29
 */
public class LockActivity extends BaseMvpActivity<LockPresenter> implements LockContract.View, View.OnClickListener {

    private LockPresenter lockPresenter;
    private ListView lvLock;
    private ListView lvUnLock;
    private TextView tvLock;
    private TextView tvUnLock;
    private Button btnLock;
    private Button btnUnLock;
    private LinearLayout llLock;
    private LinearLayout llUnLock;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lock;
    }

    @Override
    public void init() {
        initView();
        initClass();
        initApp();
        initListener();
    }

    private void initListener() {
        btnLock.setOnClickListener(this);
        btnUnLock.setOnClickListener(this);
    }

    private void initApp() {
        if (lockPresenter != null && lockPresenter.isViewAttached()) {
            lockPresenter.initApp(this);
        }
    }

    private void initClass() {
        lockPresenter = new LockPresenter(this);
        lockPresenter.attachView(this);
    }

    private void initView() {
        lvLock = findViewById(R.id.lv_lock);
        lvUnLock = findViewById(R.id.lv_unlock);
        tvLock = findViewById(R.id.tv_lock);
        tvUnLock = findViewById(R.id.tv_unlock);
        btnLock = findViewById(R.id.btn_lock);
        btnUnLock = findViewById(R.id.btn_unlock);
        llLock = findViewById(R.id.ll_lock);
        llUnLock = findViewById(R.id.ll_unlock);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void setAdapter(boolean isLock, LockAdapter adapter) {
        if (isLock) {
            lvLock.setAdapter(adapter);
        } else {
            lvUnLock.setAdapter(adapter);
        }
    }

    @Override
    public void setTitle(boolean isLock, String str) {
        if (isLock) {
            tvLock.setText(str);
        } else {
            tvUnLock.setText(str);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lock:
                change(true);
                break;
            case R.id.btn_unlock:
                change(false);
                break;
        }
    }

    private void change(boolean b) {
        if (b){
            btnUnLock.setBackgroundResource(R.drawable.shape_unlock_blue_bg);
            btnLock.setBackgroundResource(R.drawable.shape_lock_white_bg);
            btnUnLock.setTextColor(Color.WHITE);
            btnLock.setTextColor(Color.BLUE);
            llLock.setVisibility(View.GONE);
            llUnLock.setVisibility(View.VISIBLE);
        }else{
            btnUnLock.setBackgroundResource(R.drawable.shape_unlock_white_bg);
            btnLock.setBackgroundResource(R.drawable.shape_lock_blue_bg);
            btnUnLock.setTextColor(Color.BLUE);
            btnLock.setTextColor(Color.WHITE);
            llLock.setVisibility(View.VISIBLE);
            llUnLock.setVisibility(View.GONE);
        }
        if (lockPresenter!=null &&lockPresenter.isViewAttached()){
            lockPresenter.getSize();
        }
    }
}
