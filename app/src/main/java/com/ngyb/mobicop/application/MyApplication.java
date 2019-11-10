package com.ngyb.mobicop.application;

import android.app.Application;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 10:46
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                e.printStackTrace();
                try {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Mobicop.log";
                    File file = new File(path);
                    if (!file.exists()){
                        file.mkdir();
                    }
                    PrintWriter printWriter = new PrintWriter(file);
                    e.printStackTrace(printWriter);
                    printWriter.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
