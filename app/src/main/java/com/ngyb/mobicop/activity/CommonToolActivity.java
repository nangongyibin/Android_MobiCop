package com.ngyb.mobicop.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.provider.Telephony;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.contract.CommonToolContract;
import com.ngyb.mobicop.presenter.CommonToolPresenter;
import com.ngyb.mobicop.service.DogService;
import com.ngyb.mvpbase.BaseMvpActivity;
import com.ngyb.settingsummary.SettingItemView;
import com.ngyb.utils.ServiceUtils;

import es.dmoral.toasty.Toasty;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 15:36
 */
@SuppressLint("NewApi")
public class CommonToolActivity extends BaseMvpActivity<CommonToolPresenter> implements CommonToolContract.View, View.OnClickListener {

    private SettingItemView sivQueryPhoneAddress;
    private SettingItemView sivSmsBackUp;
    private SettingItemView sivSmsRestore;
    private SettingItemView sivAppLock;
    private SettingItemView sivDogService;
    private ServiceUtils serviceUtils;
    private CommonToolPresenter commonToolPresenter;

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
        commonToolPresenter = new CommonToolPresenter(this);
        commonToolPresenter.attachView(this);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String[] permission = null;
                    if (checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            permission = new String[]{Manifest.permission.READ_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        } else {
                            permission = new String[]{Manifest.permission.READ_SMS};
                        }
                        if (permission.length == 2 && shouldShowRequestPermissionRationale(permission[1])) {
                            Toasty.info(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_LONG).show();
                        } else if (shouldShowRequestPermissionRationale(permission[0])) {
                            Toasty.info(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_LONG).show();
                        } else {
                            requestPermissions(permission, 7219);
                        }
                    } else {
                        backUp();
                    }
                } else {
                    backUp();
                }
                break;
            //短信备份
            case R.id.siv_sms_restore:
                setDefault(sivSmsRestore);
                break;
            //程序锁
            case R.id.siv_app_lock:
                Intent intent1 = new Intent(this, LockActivity.class);
                startActivity(intent1);
                break;
            //电子狗
            case R.id.siv_dog_service:
                if (!hasPermission()) {
                    startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),1101);
                }else{
                    serviceStartAndStop();
                }
                break;
        }
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setDefault(View view) {
        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
        startActivityForResult(intent, 9127);
    }

    private void backUp() {
        if (commonToolPresenter != null && commonToolPresenter.isViewAttached()) {
            commonToolPresenter.backUp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 7219) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toasty.info(this, "权限获取失败，短信备份失败！", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            backUp();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 9127) {
            smsRestore();
        }else if (requestCode ==1101){
            serviceStartAndStop();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void serviceStartAndStop() {
        sivDogService.reverseState();
        if (sivDogService.getOpenState()) {
            startService(new Intent(this, DogService.class));
        } else {
            stopService(new Intent(this, DogService.class));
        }
    }

    private void smsRestore() {
        if (commonToolPresenter != null && commonToolPresenter.isViewAttached()) {
            commonToolPresenter.smsRestore();
        }
    }
}
