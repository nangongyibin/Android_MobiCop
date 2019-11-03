package com.ngyb.mobicop.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.constant.Constant;
import com.ngyb.mobicop.contract.SplashContract;
import com.ngyb.mobicop.presenter.SplashPresenter;
import com.ngyb.mobicop.service.ProtectService;
import com.ngyb.mobicop.utils.AppUtils;
import com.ngyb.mobicop.utils.CopyDBUtils;
import com.ngyb.mobicop.utils.SharedPreferencesUtils;
import com.ngyb.mobicop.view.base.BaseMvpActivity;

import java.io.File;

import es.dmoral.toasty.Toasty;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/10/31 17:27
 */
public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.View {
    private static final String TAG = "SplashActivity";
    private SharedPreferencesUtils sp;
    private SplashPresenter splashPresenter;
    private AppUtils appUtils;
    private TextView tvVersion;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private CopyDBUtils copyDBUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        initClass();
        initView();
        initService();
        initVersion();
        initDB();
        initShortCut();
    }

    /**
     * 初始化快捷方式
     */
    private void initShortCut() {
        boolean shortCut = sp.getBoolean(Constant.SHORT_CUT_KEY, false);
        if (!shortCut) {
            Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "南宫燚滨手机卫士快捷方式");
            //是否允许重复创建
            intent.putExtra("duplicate", true);
            ComponentName componentName = new ComponentName(this.getPackageName(), "." + this.getLocalClassName());
            Intent shortCutIntent = new Intent(Intent.ACTION_MAIN);
            shortCutIntent.putExtra("快捷方式参数", "快捷方式参数值");
            shortCutIntent.setComponent(componentName);
            intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
            Intent.ShortcutIconResource shortcutIconResource = Intent.ShortcutIconResource.fromContext(this, R.drawable.mobicopicon);
            intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, shortcutIconResource);
            this.sendBroadcast(intent);
        }
    }

    private void initDB() {
        copyDBUtils.copyDB(this, "antivirus.db", null);
        copyDBUtils.copyDB(this, "address.db", null);
    }

    private void initView() {
        tvVersion = findViewById(R.id.tvVersion);
    }

    /**
     * 关于版本的相关操作
     */
    private void initVersion() {
        String versionName = appUtils.getVersionName(this);
        tvVersion.setText(versionName);
        if (splashPresenter != null && splashPresenter.isViewAttached()) {
            splashPresenter.compareVersionCode(sp.getBoolean(Constant.UPDATE_AUTO, true));
        }
    }

    /**
     * 开启一些服务
     */
    private void initService() {
        Intent intent = new Intent(this, ProtectService.class);
        startService(intent);
    }

    /**
     * 初始化一些工具类
     */
    private void initClass() {
        splashPresenter = new SplashPresenter();
        splashPresenter.attachView(this);
        sp = new SharedPreferencesUtils(this);
        appUtils = new AppUtils();
        copyDBUtils = new CopyDBUtils();
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
    public void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 检查权限
     */
    @Override
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permissions[permissions.length - 1]) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(permissions[permissions.length - 1])) {
                    Toasty.info(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_LONG).show();
                    enterHome();
                } else {
                    //请求权限
                    requestPermissions(permissions, 7219);
                }
            } else {
                Log.e(TAG, "checkPermission: 1");
                checkVersion();
            }
        } else {
            Log.e(TAG, "checkPermission: 2");
            checkVersion();
        }
    }

    @Override
    public void showDialog(final String desc) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                builder.setTitle("是否下载更新");
                builder.setMessage(desc);
                builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        downloadApk();
                    }
                });
                builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        enterHome();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        enterHome();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 9127);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 9127) {
            enterHome();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 下载最新apk
     */
    private void downloadApk() {
        if (splashPresenter != null && splashPresenter.isViewAttached()) {
            splashPresenter.downloadApk(this);
        }
    }

    private void checkVersion() {
        if (splashPresenter != null && splashPresenter.isViewAttached()) {
            splashPresenter.checkVersion(appUtils.getVersionCode(this));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 7219) {
            if (permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "checkPermission: 3");
                    checkVersion();
                } else {
                    Toasty.info(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_LONG).show();
                    enterHome();
                }
            } else {
                enterHome();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            enterHome();
        }
    }
}
