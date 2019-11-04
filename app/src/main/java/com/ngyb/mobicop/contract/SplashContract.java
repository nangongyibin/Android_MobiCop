package com.ngyb.mobicop.contract;

import android.content.Context;


import com.ngyb.mvpbase.BaseView;

import java.io.File;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/10/31 17:28
 */
public interface SplashContract {
    interface Model {
    }

    interface View extends BaseView {
        void enterHome();

        void checkPermission();

        void showDialog(String desc);

        void installApk(File file);
    }

    interface Presenter {
        void compareVersionCode(boolean isUpdate);

        void checkVersion(int versionCode);

        void downloadApk(Context context);
    }
}
