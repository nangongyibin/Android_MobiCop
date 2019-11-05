package com.ngyb.mobicop.presenter;

import android.content.Context;

import com.ngyb.mobicop.activity.LockActivity;
import com.ngyb.mobicop.adapter.LockAdapter;
import com.ngyb.mobicop.contract.LockContract;
import com.ngyb.mobicop.dao.AppLockDao;
import com.ngyb.mvpbase.BasePresenter;
import com.ngyb.utils.AppUtils;
import com.ngyb.utils.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 17:30
 */
public class LockPresenter extends BasePresenter<LockContract.View> implements LockContract.Presenter {
    private final Context context;
    private final AppLockDao appLockDao;
    private List<String> lockPackageNameList;
    private final AppUtils appUtils;
    private List<AppInfoBean> appInfoList;
    private List<AppInfoBean> lockList = new ArrayList<>();
    private List<AppInfoBean> unlockList = new ArrayList<>();
    private LockAdapter unLockAdapter;
    private LockAdapter lockAdapter;

    public LockPresenter(Context context) {
        this.context = context;
        appLockDao = new AppLockDao(context);
        appUtils = new AppUtils();
    }

    @Override
    public void initApp(final LockActivity lockActivity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                lockPackageNameList = appLockDao.queryAll();
                appInfoList = appUtils.getAppInfoList(context);
                for (int i = 0; i < appInfoList.size(); i++) {
                    AppInfoBean appInfoBean = appInfoList.get(i);
                    if (lockPackageNameList.contains(appInfoBean.getPackageName())) {
                        lockList.add(appInfoBean);
                    } else {
                        unlockList.add(appInfoBean);
                    }
                }
                lockActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        unLockAdapter = new LockAdapter(LockPresenter.this, unlockList, false, context);
                        mView.setAdapter(false, unLockAdapter);
                        lockAdapter = new LockAdapter(LockPresenter.this, lockList, true, context);
                        mView.setAdapter(true, lockAdapter);
                    }
                });
            }
        }).start();
    }

    @Override
    public void changelist(boolean isLock, AppInfoBean appInfoBean) {
        if (isLock) {
            lockList.remove(appInfoBean);
            unlockList.add(0, appInfoBean);
            unLockAdapter.notifyDataSetChanged();
            lockAdapter.notifyDataSetChanged();
            appLockDao.delete(appInfoBean.getPackageName());
        } else {
            unlockList.remove(appInfoBean);
            lockList.add(0, appInfoBean);
            unLockAdapter.notifyDataSetChanged();
            lockAdapter.notifyDataSetChanged();
            appLockDao.insert(appInfoBean.getPackageName());
        }
    }

    @Override
    public void setTitle(boolean isLock, String str) {
        mView.setTitle(isLock, str);
    }

    @Override
    public void getSize() {
        mView.setTitle(false, "未加锁（" + unlockList.size() + "）");
        mView.setTitle(true, "已加锁（" + lockList.size() + "）");

    }
}
