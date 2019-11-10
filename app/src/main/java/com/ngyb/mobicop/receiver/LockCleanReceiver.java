package com.ngyb.mobicop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ngyb.utils.AppUtils;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 16:32
 */
public class LockCleanReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AppUtils appUtils = new AppUtils();
        appUtils.killAllProcess(context);
    }
}
