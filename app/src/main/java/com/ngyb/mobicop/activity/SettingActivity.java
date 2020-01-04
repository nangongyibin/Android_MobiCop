package com.ngyb.mobicop.activity;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

import com.ngyb.dialogsummary.CustomerDialog;
import com.ngyb.mobicop.R;
import com.ngyb.mobicop.constant.Constant;
import com.ngyb.mobicop.contract.SettingContract;
import com.ngyb.mobicop.presenter.SettingPresenter;
import com.ngyb.mobicop.service.AddressService;
import com.ngyb.mvpbase.BaseMvpActivity;
import com.ngyb.settingsummary.SettingItemView;
import com.ngyb.utils.ServiceUtils;
import com.ngyb.utils.SharedPreferencesUtils;


/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/3 17:03
 */
public class SettingActivity extends BaseMvpActivity<SettingPresenter> implements SettingContract.View, View.OnClickListener {

    private SettingItemView sivUpdateAuto;
    private SettingItemView sivAddress;
    private SettingItemView sivAddressStyle;
    private SharedPreferencesUtils sp;
    private boolean isUpdateAuto;
    private ServiceUtils serviceUtils;
    private boolean openState;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        initView();
        initClass();
        initState();
        initListener();
    }

    private void initListener() {
        sivAddress.setOnClickListener(this);
        sivAddressStyle.setOnClickListener(this);
        sivUpdateAuto.setOnClickListener(this);
    }

    private void initState() {
        isUpdateAuto = sp.getBoolean(Constant.UPDATE_AUTO, true);
        sivUpdateAuto.setOpenState(isUpdateAuto);
        String serviceName = AddressService.class.getName();
        boolean serviceRunning = serviceUtils.isServiceRunning(this, serviceName);
        sivAddress.setOpenState(serviceRunning);
    }

    private void initClass() {
        sp = new SharedPreferencesUtils(this);
        serviceUtils = new ServiceUtils();
    }

    private void initView() {
        sivUpdateAuto = findViewById(R.id.siv_update_auto);
        sivAddress = findViewById(R.id.siv_address);
        sivAddressStyle = findViewById(R.id.siv_address_style);
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
            case R.id.siv_update_auto:
                sivUpdateAuto.reverseState();
                sp.setBoolean(Constant.UPDATE_AUTO, sivUpdateAuto.getOpenState());
                break;
            case R.id.siv_address:
                sivAddress.reverseState();
                openState = sivAddress.getOpenState();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        startActivityForResult(intent, 1);
                    } else {
                        startService();
                    }
                } else {
                    startService();
                }
                break;
            case R.id.siv_address_style:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        CustomerDialog customerDialog = new CustomerDialog(this);
        customerDialog.show();
    }

    private void startService() {
        if (openState) {
            startService(new Intent(this, AddressService.class));
        } else {
            stopService(new Intent(this, AddressService.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            startService();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
