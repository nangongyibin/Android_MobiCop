package com.ngyb.mobicop.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.ngyb.mobicop.activity.CommonToolActivity;
import com.ngyb.mobicop.bean.SmsInfoBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 21:15
 */
public class SmsUtils {
    private static final String TAG = "SmsUtils";

    public interface CallBack {
        public void setMax(int maxProgress);

        public void setProgress(int currentProgress);

        public void onSuccessed();

        public void onFail();
    }

    public void backUp(final Context context, final CallBack callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = null;
                FileWriter fileWriter = null;
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sms.json";
                        Log.e(TAG, "run: path" + path);
                        ContentResolver contentResolver = context.getContentResolver();
                        Uri uri = Uri.parse("content://sms/");
                        String[] projection = {"address", "date", "read", "type", "body"};
                        cursor = contentResolver.query(uri, projection, null, null, null);
                        if (callback != null) {
                            callback.setMax(cursor.getCount());
                        }
                        List<SmsInfoBean> list = new ArrayList<>();
                        int count = 0;
                        while (cursor.moveToNext()) {
                            String address = cursor.getString(0);
                            long date = cursor.getLong(1);
                            int read = cursor.getInt(2);
                            int type = cursor.getInt(3);
                            String body = cursor.getString(4);
                            count++;
                            if (callback != null) {
                                callback.setProgress(count);
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            SmsInfoBean smsInfoBean = new SmsInfoBean(address, date, read, type, body);
                            list.add(smsInfoBean);
                        }
                        Gson gson = new Gson();
                        String json = gson.toJson(list);
                        Log.e(TAG, "run: " + json);
                        File file = new File(path);
                        fileWriter = new FileWriter(file);
                        fileWriter.write(json);
                        fileWriter.flush();
                        ((CommonToolActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    callback.onSuccessed();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    ((CommonToolActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onFail();
                            }
                        }
                    });

                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
