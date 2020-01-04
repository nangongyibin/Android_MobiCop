package com.ngyb.mobicop.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ngyb.mobicop.activity.InterceptetActivity;
import com.ngyb.mobicop.constant.Constant;
import com.ngyb.mobicop.contentobserver.DogContentObserver;
import com.ngyb.mobicop.dao.AppLockDao;
import com.ngyb.mobicop.receiver.DogReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 16:12
 */
public class DogService extends Service {
    private static final String TAG = "DogService";
    private boolean isRunning;
    private AppLockDao appLockDao;
    private List<String> packageNameList;
    private DogReceiver dogReceiver;
    private ContentResolver contentResolver;
    private List<String> unLockPackageNameList = new ArrayList<>();
    private DogContentObserver dogContentObserver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: 看门狗服务开启");
        isRunning = true;
        dogReceiver = new DogReceiver(DogService.this);
        IntentFilter intentFilter = new IntentFilter("Intent.action.UNLOCK");
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(dogReceiver, intentFilter);
        startRun();
        contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://ngyb.mobicop.change");
        dogContentObserver = new DogContentObserver(null, DogService.this);
        contentResolver.registerContentObserver(uri, true, dogContentObserver);
        super.onCreate();
    }

    private void startRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appLockDao = new AppLockDao(DogService.this);
                packageNameList = appLockDao.queryAll();
                while (isRunning) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String packageName = getTopApp(DogService.this);
                    Log.e(TAG, "run: topactivity = " + packageName);
                    if (unLockPackageNameList.contains(packageName)) {
                        continue;
                    }
                    if (packageNameList.contains(packageName)) {
                        Intent intent = new Intent(getApplicationContext(), InterceptetActivity.class);
                        intent.putExtra("packagename", packageName);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }
        }).start();
    }

    private String getTopApp(Context context) {
        String topActivity = "";
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (m != null) {
                long now = System.currentTimeMillis();
                List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 24 * 60 * 60 * 1000, now);
                if (stats != null && stats.size() > 0) {
                    int j = 0;
                    for (int i = 0; i < stats.size(); i++) {
                        if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                            j = i;
                        }
                    }
                    topActivity = stats.get(j).getPackageName();
                }
            }
        } else {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> appTasks = activityManager.getRunningTasks(1);
            if (null != appTasks && !appTasks.isEmpty()) {
                topActivity = appTasks.get(0).topActivity.getPackageName();
            } else {
                topActivity = "";
            }
        }
        return topActivity;
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE );
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            List<ActivityManager.RunningTaskInfo> appTasks = activityManager.getRunningTasks(1);
//            if (null != appTasks && !appTasks.isEmpty()) {
//                return appTasks.get(0).topActivity.getPackageName();
//            }
//        } else {
//            //5.0以后需要用这方法
//            UsageStatsManager sUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//            long endTime = System.currentTimeMillis();
//            long beginTime = endTime - 10000;
//            String result = "";
//            UsageEvents.Event event = new UsageEvents.Event();
//            UsageEvents usageEvents = sUsageStatsManager.queryEvents(beginTime, endTime);
//            while (usageEvents.hasNextEvent()) {
//                usageEvents.getNextEvent(event);
//                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
//                    result = event.getPackageName();
//                }
//            }
//            if (!android.text.TextUtils.isEmpty(result)) {
//                return result;
//            }
//        }
//        return "";
    }

    public void receiver(Intent intent) {
        if (intent.getAction().equals("Intent.action.UNLOCK")) {
            String packagename = intent.getStringExtra("packagename");
            Log.e(TAG, "receiver: " + packagename);
            unLockPackageNameList.add(packagename);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            isRunning = false;
            unLockPackageNameList.clear();
        } else {
            isRunning = true;
            startRun();
        }
    }

    public void change() {
        packageNameList = appLockDao.queryAll();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onCreate: 看门狗服务关闭");
        super.onDestroy();
        isRunning = false;
        if (dogReceiver != null) {
            unregisterReceiver(dogReceiver);
        }
    }
}
