package com.ngyb.mobicop.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ngyb.mobicop.receiver.LockCleanReceiver;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 16:13
 */
public class LockCleanService extends Service {

    private LockCleanReceiver lockCleanReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        lockCleanReceiver = new LockCleanReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(lockCleanReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        if (lockCleanReceiver != null) {
            unregisterReceiver(lockCleanReceiver);
        }
        super.onDestroy();
    }
}
