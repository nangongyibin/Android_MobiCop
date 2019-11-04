package com.ngyb.mobicop.activity;

import android.content.Intent;
import android.view.View;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.contract.CommonToolContract;
import com.ngyb.mobicop.presenter.CommonToolPresenter;
import com.ngyb.mobicop.service.DogService;
import com.ngyb.mvpbase.BaseMvpActivity;
import com.ngyb.settingsummary.SettingItemView;
import com.ngyb.utils.ServiceUtils;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 15:36
 */
public class CommonToolActivity extends BaseMvpActivity<CommonToolPresenter> implements CommonToolContract.View, View.OnClickListener {

    private SettingItemView sivQueryPhoneAddress;
    private SettingItemView sivSmsBackUp;
    private SettingItemView sivSmsRestore;
    private SettingItemView sivAppLock;
    private SettingItemView sivDogService;
    private ServiceUtils serviceUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tool_common;
    }

    @Override
    public void init() {
        initClass();
        initView();
        initOpenState();
        initListener();
    }

    private void initOpenState() {
        boolean serviceRunning = serviceUtils.isServiceRunning(this, DogService.class.getSimpleName());
        sivDogService.setOpenState(serviceRunning);
    }

    private void initClass() {
        serviceUtils = new ServiceUtils();
    }

    private void initListener() {
        sivQueryPhoneAddress.setOnClickListener(this);
        sivSmsBackUp.setOnClickListener(this);
        sivSmsRestore.setOnClickListener(this);
        sivAppLock.setOnClickListener(this);
        sivDogService.setOnClickListener(this);
    }

    private void initView() {
        sivQueryPhoneAddress = findViewById(R.id.siv_query_phone_address);
        sivSmsBackUp = findViewById(R.id.siv_sms_backup);
        sivSmsRestore = findViewById(R.id.siv_sms_restore);
        sivAppLock = findViewById(R.id.siv_app_lock);
        sivDogService = findViewById(R.id.siv_dog_service);
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
            //归属地查询
            case R.id.siv_query_phone_address:
                Intent intent = new Intent(this, QueryAddressActivity.class);
                startActivity(intent);
                break;
            //短信备份
            case R.id.siv_sms_backup:
                break;
            //短信备份
            case R.id.siv_sms_restore:
                break;
            //程序锁
            case R.id.siv_app_lock:
                break;
            //电子狗
            case R.id.siv_dog_service:
                break;
        }
    }
}
