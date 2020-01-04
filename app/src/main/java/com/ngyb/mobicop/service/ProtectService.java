package com.ngyb.mobicop.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import com.ngyb.mobicop.R;
import com.ngyb.mobicop.activity.SplashActivity;

/**
 * 作者：南宫燚滨
 * 描述： 通知
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/1 10:20
 */
public class ProtectService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Notification notification = new Notification();
        notification.icon = R.drawable.mobicopicon;
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.layot_notification);
        notification.contentView = remoteView;
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ComponentName componentName = new ComponentName(this, SplashActivity.class);
        intent.setComponent(componentName);
        notification.contentIntent = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        startForeground(1000, notification);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
