package com.ngyb.mobicop.presenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.ngyb.mobicop.adapter.ScanAdapter;
import com.ngyb.mobicop.bean.VirusInfoBean;
import com.ngyb.mobicop.contract.AnitVirusContract;
import com.ngyb.mobicop.dao.VirusDao;
import com.ngyb.mvpbase.BasePresenter;
import com.ngyb.utils.AppUtils;
import com.ngyb.utils.MD5Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 16:27
 */
public class AnitVirusPresenter extends BasePresenter<AnitVirusContract.View> implements AnitVirusContract.Presenter {
    private Context context;
    private MyAsyncTask myAsyncTask;
    private List<VirusInfoBean> appInfoList = new ArrayList<>();
    private ScanAdapter scanAdapter;
    private final AppUtils appUtils;
    private boolean isStop = false;
    private final MD5Utils md5Utils;
    private final VirusDao virusDao;
    public int width;

    public AnitVirusPresenter(Context context) {
        this.context = context;
        appUtils = new AppUtils();
        md5Utils = new MD5Utils();
        virusDao = new VirusDao();
    }

    @Override
    public void init() {
        startAsyncTask();
    }

    @Override
    public Bitmap getLeftBitmap(Bitmap bitmap) {
        width = bitmap.getWidth() / 2;
        int height = bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        Bitmap leftBitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(leftBitmap);
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        return leftBitmap;
    }

    @Override
    public Bitmap getRightBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth() / 2;
        int height = bitmap.getHeight();
        Bitmap.Config config = bitmap.getConfig();
        Bitmap rightBitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(rightBitmap);
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        matrix.setTranslate(-width, 0);
        canvas.drawBitmap(bitmap, matrix, paint);
        return rightBitmap;
    }

    @Override
    public void onDestroy() {
        if (myAsyncTask != null) {
            isStop = true;
        }
    }

    /**
     * 开启异步
     */
    private void startAsyncTask() {
        if (myAsyncTask == null) {
            myAsyncTask = new MyAsyncTask();
        }
        myAsyncTask.execute();
    }

    class MyAsyncTask extends AsyncTask<String, VirusInfoBean, Integer> {
        private int maxSize = 0;
        private int currentPosition = 0;
        private int virusCount = 0;

        @Override
        protected void onPreExecute() {
            scanAdapter = new ScanAdapter(context, appInfoList);
            mView.initVisible(scanAdapter);
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<PackageInfo> installedPackages = appUtils.getPackageInfo(context);
            maxSize = installedPackages.size();
            for (int i = 0; i < installedPackages.size(); i++) {
                if (isStop) {
                    break;
                }
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                PackageManager pm = context.getPackageManager();
                PackageInfo packageInfo = installedPackages.get(i);
                String packageName = packageInfo.packageName;
                Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
                String name = packageInfo.applicationInfo.loadLabel(pm).toString();
                Signature[] signatures = packageInfo.signatures;
                Signature signature = signatures[0];
                String strSignature = signature.toCharsString();
                String md5 = md5Utils.encode(strSignature);
                boolean isVirus = virusDao.isVirus(context, md5);
                VirusInfoBean virusInfoBean = new VirusInfoBean(packageName, name, drawable, isVirus);
                publishProgress(virusInfoBean);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(VirusInfoBean... values) {
            if (isStop) {
                return;
            }
            currentPosition++;
            VirusInfoBean bean = values[0];
            if (bean.isVirus) {
                virusCount++;
                appInfoList.add(0, bean);
            } else {
                appInfoList.add(bean);
            }
            String packagename = bean.packagename;
            mView.setPackageName(packagename);
            scanAdapter.notifyDataSetChanged();
            mView.scrollToPosition(appInfoList.size() - 1);
            mView.setProgress(currentPosition, maxSize);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (isStop) {
                return;
            }
            mView.setPostExecteOne(virusCount);

            super.onPostExecute(integer);
        }
    }
}
