package com.ngyb.mobicop.presenter;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ngyb.mobicop.bean.SmsInfoBean;
import com.ngyb.mobicop.contract.CommonToolContract;
import com.ngyb.mobicop.utils.SmsUtils;
import com.ngyb.mvpbase.BasePresenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 15:49
 */
public class CommonToolPresenter extends BasePresenter<CommonToolContract.View> implements CommonToolContract.Presenter {
    private Context context;
    private final SmsUtils smsUtils;
    private static final String TAG = "CommonToolPresenter";

    public CommonToolPresenter(Context context) {
        this.context = context;
        smsUtils = new SmsUtils();
    }

    @Override
    public void backUp() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        smsUtils.backUp(context, new SmsUtils.CallBack() {
            @Override
            public void setMax(int maxProgress) {
                progressDialog.setMax(maxProgress);
            }

            @Override
            public void setProgress(int currentProgress) {
                progressDialog.setProgress(currentProgress);
            }

            @Override
            public void onSuccessed() {
                Toasty.success(context, "备份成功", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

            @Override
            public void onFail() {
                Toasty.error(context, "备份失败", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void smsRestore() {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sms.json";
            Log.e(TAG, "smsRestore: "+path );
            BufferedReader br = new BufferedReader(new FileReader(new File(path)));
            String readLine = br.readLine();
            Log.e(TAG, "smsRestore: " + readLine);
            Gson gson = new Gson();

            List<SmsInfoBean> list = gson.fromJson(readLine, new TypeToken<List<SmsInfoBean>>() {
            }.getType());
            for (SmsInfoBean smsInfoBean : list) {
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = Uri.parse("content://sms/");
                ContentValues values = new ContentValues();
                values.put("address", smsInfoBean.getAddress());
                values.put("date", smsInfoBean.getDate());
                values.put("type", smsInfoBean.getType());
                values.put("body", smsInfoBean.getBody());
                contentResolver.insert(uri, values);
            }
            Toasty.success(context, "短信还原成功", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toasty.error(context, "短信还原失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
