package com.ngyb.mobicop.presenter;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

import com.ngyb.mobicop.constant.Constant;
import com.ngyb.mobicop.contract.SplashContract;
import com.ngyb.mvpbase.BasePresenter;
import com.ngyb.utils.StreamUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/10/31 17:28
 */
public class SplashPresenter extends BasePresenter<SplashContract.View> implements SplashContract.Presenter {
    private static final String TAG = "SplashPresenter";
    private String downloadUrl;
    private final StreamUtils streamUtils;

    public SplashPresenter() {
        streamUtils = new StreamUtils();
    }

    /**
     * @param isUpdate 是否更新
     */
    @Override
    public void compareVersionCode(boolean isUpdate) {
        if (isUpdate) {
            mView.checkPermission();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mView.enterHome();
                }
            }).start();
        }
    }

    @Override
    public void checkVersion(final int versionCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.SECONDS)
                .connectTimeout(3000, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url(Constant.url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mView.enterHome();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    ResponseBody responseBody = response.body();
                    String json = responseBody.string();
                    JSONObject jsonObject = new JSONObject(json);
                    Log.e(TAG, "onResponse: " + json);
                    int serverVersionCode = jsonObject.getInt("versionCode");
                    if (versionCode < serverVersionCode) {
                        downloadUrl = jsonObject.getString("downloadUrl");
                        String desc = jsonObject.getString("desc");
                        mView.showDialog(desc);
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mView.enterHome();
                    }
                } catch (JSONException e) {
                    mView.enterHome();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void downloadApk(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //创建进度条对话框
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.e(TAG, "onCancel: 0" );
                    mView.enterHome();
                }
            });
            progressDialog.show();
            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mobicop.apk";
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(3000, TimeUnit.SECONDS)
                    .readTimeout(3000, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .get()
                    .url(downloadUrl)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "onFailure: 1" );
                    mView.enterHome();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    FileOutputStream fos = null;
                    InputStream is = null;
                    try {
                        ResponseBody body = response.body();
                        progressDialog.setMax((int) body.contentLength());
                        is = body.byteStream();
                        File file = new File(path);
                        fos = new FileOutputStream(file);
                        byte[] buf = new byte[1024];
                        int len = 0;
                        int temp = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            temp += len;
                            progressDialog.setProgress(temp);
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                progressDialog.dismiss();
                                Log.e(TAG, "onResponse: 2" );
                                mView.enterHome();
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();
                        mView.installApk(file);
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: "+e.getLocalizedMessage() );
                        progressDialog.dismiss();
                        Log.e(TAG, "onResponse: 3" );
                        mView.enterHome();
                        e.printStackTrace();
                    } finally {
                        try {
                            streamUtils.close(is);
                            streamUtils.close(fos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            Log.e(TAG, "downloadApk: 4" );
            mView.enterHome();
        }
    }
}
