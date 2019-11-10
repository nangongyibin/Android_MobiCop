package com.ngyb.mobicop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ngyb.utils.AppUtils;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 16:32
 */
public class LockCleanReceiver extends BroadcastReceiver {
    private static final String TAG = "LockCleanReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: 锁屏清理" );
        AppUtils appUtils = new AppUtils();
        appUtils.killAllProcess(context);
    }
}
