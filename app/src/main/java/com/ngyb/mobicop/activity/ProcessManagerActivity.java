package com.ngyb.mobicop.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.adapter.ProcessManagerAdapter;
import com.ngyb.mobicop.contract.ProcessManagerContract;
import com.ngyb.mobicop.presenter.ProcessManagerPresenter;
import com.ngyb.mobicop.service.LockCleanService;
import com.ngyb.mvpbase.BaseMvpActivity;
import com.ngyb.settingsummary.SettingItemView;
import com.ngyb.utils.bean.ProcessBean;

import ngyb.item.ProcessItemView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 18:06
 */
public class ProcessManagerActivity extends BaseMvpActivity<ProcessManagerPresenter> implements ProcessManagerContract.View, View.OnClickListener {

    private ProcessItemView pivProcess;
    private ImageView ivClean;
    private ProcessItemView pivMemory;
    private ProgressBar progressLoading;
    private StickyListHeadersListView lvProcess;
    private ImageView ivArrow1;
    private ImageView ivArrow2;
    private RelativeLayout handle;
    private SettingItemView sivShowSys;
    private SettingItemView sivLockClean;
    private LinearLayout content;
    private SlidingDrawer sd;
    private Button btnSelectAll;
    private Button btnSelectReverse;
    private ProcessManagerPresenter processManagerPresenter;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_process_manager;
    }

    @Override
    public void init() {
        initView();
        initClass();
        initAnimation();
        initProcess();
        initMemoryInfo();
        initOpen();
        initListener();
    }

    private void initOpen() {
        processManagerPresenter.initOpen();
    }

    private void initMemoryInfo() {
        processManagerPresenter.initMemoryInfo();
    }

    private void initProcess() {
        processManagerPresenter.initProcess();
    }

    private void initListener() {
        sd.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                closeAnimation();
            }
        });
        sd.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                openAnimation();
            }
        });
        lvProcess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                processManagerPresenter.itemClick(position);
            }
        });
        lvProcess.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                processManagerPresenter.itemLongClick(view, position);
                return false;
            }
        });
        btnSelectAll.setOnClickListener(this);
        btnSelectReverse.setOnClickListener(this);
        ivClean.setOnClickListener(this);
        sivShowSys.setOnClickListener(this);
        sivLockClean.setOnClickListener(this);
    }

    private void initAnimation() {
        closeAnimation();
    }

    private void closeAnimation() {
        ivArrow1.clearAnimation();
        ivArrow2.clearAnimation();
        ivArrow1.setImageResource(R.drawable.drawer_arrow_up);
        ivArrow2.setImageResource(R.drawable.drawer_arrow_up);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(200);
        ivArrow1.startAnimation(alphaAnimation);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(1.0f, 0.3f);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        alphaAnimation1.setDuration(200);
        ivArrow2.startAnimation(alphaAnimation1);
    }

    public void openAnimation() {
        ivArrow1.clearAnimation();
        ivArrow2.clearAnimation();
        ivArrow1.setImageResource(R.drawable.drawer_arrow_down);
        ivArrow2.setImageResource(R.drawable.drawer_arrow_down);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setDuration(200);
        ivArrow1.startAnimation(alphaAnimation);
        AlphaAnimation alphaAnimation1 = new AlphaAnimation(1.0f, 0.3f);
        alphaAnimation1.setRepeatCount(Animation.INFINITE);
        alphaAnimation1.setRepeatMode(Animation.REVERSE);
        alphaAnimation1.setDuration(200);
        ivArrow2.startAnimation(alphaAnimation1);
    }

    private void initClass() {
        processManagerPresenter = new ProcessManagerPresenter(this);
        processManagerPresenter.attachView(this);
    }

    private void initView() {
        ivClean = findViewById(R.id.iv_clean);
        pivProcess = findViewById(R.id.piv_process);
        pivMemory = findViewById(R.id.piv_memory);
        progressLoading = findViewById(R.id.progress_loading);
        lvProcess = findViewById(R.id.lv_process);
        ivArrow1 = findViewById(R.id.iv_arrow1);
        ivArrow2 = findViewById(R.id.iv_arrow2);
        handle = findViewById(R.id.handle);
        sivShowSys = findViewById(R.id.siv_show_sys);
        sivLockClean = findViewById(R.id.siv_lock_clean);
        content = findViewById(R.id.content);
        sd = findViewById(R.id.sd);
        btnSelectAll = findViewById(R.id.btn_select_all);
        btnSelectReverse = findViewById(R.id.btn_select_reverse);
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
    public void setProcess(int runningProcess, int allProcess, int progress) {
        pivProcess.setLeftText("进程数:");
        pivProcess.setMiddleText("正在进行进程" + runningProcess + "个");
        pivProcess.setRightText("总进程:" + allProcess);
        pivProcess.setProgressBar(progress);
    }

    @Override
    public void setMemoryInfo(String strUsedMem, String strAvailMemory, long memoryProgress) {
        pivMemory.setLeftText("内存:");
        pivMemory.setMiddleText("占用内存:" + strUsedMem);
        pivMemory.setRightText("可用内存:" + strAvailMemory);
        pivMemory.setProgressBar((int) memoryProgress);
    }

    @Override
    public void setAdapter(final ProcessManagerAdapter adapter) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressLoading.setVisibility(View.GONE);
                lvProcess.setAdapter(adapter);
            }
        });
    }

    @Override
    public void showPopupWindow(View view) {
        View viewPopup = View.inflate(this, R.layout.layout_popup_window, null);
        TextView tvUninstall = viewPopup.findViewById(R.id.tv_uninstall);
        TextView tvOpen = viewPopup.findViewById(R.id.tv_open);
        TextView tvShare = viewPopup.findViewById(R.id.tv_share);
        TextView tvInfo = viewPopup.findViewById(R.id.tv_info);
        tvUninstall.setOnClickListener(this);
        tvOpen.setOnClickListener(this);
        tvShare.setOnClickListener(this);
        tvInfo.setOnClickListener(this);
        popupWindow = new PopupWindow(viewPopup, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimationStyle);
        popupWindow.showAsDropDown(view, 120, -120);
    }

    @Override
    public void setProcess(int runningProcess, int progressMem) {
        pivProcess.setMiddleText("正在运行的进程数" + runningProcess + "个");
        pivProcess.setProgressBar(progressMem);
    }

    @Override
    public void setOpenState(boolean showSys, boolean serviceRunning) {
        sivShowSys.setOpenState(showSys);
        sivLockClean.setOpenState(serviceRunning);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_uninstall:
                Intent intent = new Intent("android.intent.action.DELETE");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("package: " + processManagerPresenter.bean.getPackageName()));
                startActivity(intent);
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.tv_open:
                PackageManager packageManager = getPackageManager();
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(processManagerPresenter.bean.getPackageName());
                if (launchIntentForPackage != null) {
                    startActivity(launchIntentForPackage);
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.tv_share:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 7219);
                } else {
                    Uri uri = Uri.parse("smsto: ");
                    Intent intent1 = new Intent(Intent.ACTION_SENDTO, uri);
                    intent1.putExtra("sms_body", "分享一个应用" + processManagerPresenter.bean.getName());
                    startActivity(intent1);
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.tv_info:
                Intent intent1 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent1.setData(Uri.fromParts("package", processManagerPresenter.bean.getPackageName(), null));
                startActivity(intent1);
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.btn_select_all:
                processManagerPresenter.selectAll();
                break;
            case R.id.btn_select_reverse:
                processManagerPresenter.selectReverse();
                break;
            case R.id.iv_clean:
                processManagerPresenter.killProcess();
                break;
            case R.id.siv_show_sys:
                sivShowSys.reverseState();
                boolean showSys = sivShowSys.getOpenState();
                processManagerPresenter.setShowSys(showSys);
                break;
            case R.id.siv_lock_clean:
                sivLockClean.reverseState();
                boolean openState = sivLockClean.getOpenState();
                if (openState) {
                    startService(new Intent(getApplicationContext(), LockCleanService.class));
                } else {
                    stopService(new Intent(getApplicationContext(), LockCleanService.class));
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 7219) {
            if (permissions[0] == Manifest.permission.SEND_SMS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse("smsto: ");
                Intent intent1 = new Intent(Intent.ACTION_SENDTO, uri);
                intent1.putExtra("sms_body", "分享一个应用" + processManagerPresenter.bean.getName());
                startActivity(intent1);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
