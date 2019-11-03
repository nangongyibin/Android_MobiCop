package com.ngyb.mobicop.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 作者：南宫燚滨
 * 描述：App应用的工具类
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/1 10:31
 */
public class AppUtils {

    /**
     * @param ctx
     * @return 项目build中的versionName
     */
    public String getVersionName(Context ctx) {
        try {
            PackageManager packageManager = ctx.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.1";
    }

    /**
     * @param ctx
     * @return 项目build中的versionCode
     */
    public int getVersionCode(Context ctx) {
        try {
            PackageManager packageManager = ctx.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
