package com.ngyb.mobicop.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.adapter.GridAdapter;
import com.ngyb.mobicop.contract.HomeContract;
import com.ngyb.mobicop.presenter.HomePresenter;
import com.ngyb.mvpbase.BaseMvpActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/1 11:01
 */
public class HomeActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View, View.OnClickListener {

    private ImageView ivLogo;
    private GridView gv;
    private ImageView ivSetting;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void init() {
        initView();
        initAdapter();
        initAnimation();
        initListener();
    }

    private void initAdapter() {
        GridAdapter gridAdapter = new GridAdapter(this);
        gv.setAdapter(gridAdapter);
    }

    private void initListener() {
        ivSetting.setOnClickListener(this);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(HomeActivity.this, CommonToolActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(HomeActivity.this, ProcessManagerActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(HomeActivity.this, AnitVirusActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        break;
                }
            }
        });
    }

    private void initView() {
        ivLogo = findViewById(R.id.iv_logo);
        gv = findViewById(R.id.gv);
        ivSetting = findViewById(R.id.iv_setting);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        ObjectAnimator rotationY = ObjectAnimator.ofFloat(ivLogo, "rotationY", 0f, 360f);
        rotationY.setRepeatCount(ObjectAnimator.INFINITE);
        rotationY.setRepeatMode(ValueAnimator.REVERSE);
        rotationY.setDuration(2000);
        rotationY.start();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting:
                //跳转到设置页面
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
