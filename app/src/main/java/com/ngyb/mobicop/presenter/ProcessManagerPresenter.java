package com.ngyb.mobicop.presenter;

import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ngyb.dialogsummary.adapter.MyAdapter;
import com.ngyb.mobicop.adapter.ProcessManagerAdapter;
import com.ngyb.mobicop.constant.Constant;
import com.ngyb.mobicop.contract.ProcessManagerContract;
import com.ngyb.mobicop.service.LockCleanService;
import com.ngyb.mvpbase.BasePresenter;
import com.ngyb.utils.AppUtils;
import com.ngyb.utils.ServiceUtils;
import com.ngyb.utils.SharedPreferencesUtils;
import com.ngyb.utils.bean.ProcessBean;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 11:51
 */
public class ProcessManagerPresenter extends BasePresenter<ProcessManagerContract.View> implements ProcessManagerContract.Presenter {
    private static final String TAG = "ProcessManagerPresenter";
    private final AppUtils appUtils;
    private final SharedPreferencesUtils sharedPreferencesUtils;
    private Context ctx;
    private int runningProcess;
    private int allProcess;
    private int progress;
    private long allMemory;
    private long availMemory;
    private long usedMemory;
    private String strUsedMem;
    private String strAvailMemory;
    private long memoryProgress;
    private List<ProcessBean> processInfoList;
    private List<ProcessBean> customerlist = new ArrayList<>();
    private List<ProcessBean> systemList = new ArrayList<>();
    private ProcessManagerAdapter adapter;
    private boolean showSys;
    public ProcessBean bean;
    private final ServiceUtils serviceUtils;


    public ProcessManagerPresenter(Context ctx) {
        this.ctx = ctx;
        appUtils = new AppUtils();
        sharedPreferencesUtils = new SharedPreferencesUtils(ctx);
        serviceUtils = new ServiceUtils();
    }

    public void initProcess() {
        try {
            Log.e(TAG, "initProcess: 1" );
            runningProcess = appUtils.getRunningProcess2(ctx);
            Log.e(TAG, "initProcess: 2+run"+runningProcess );
            allProcess = appUtils.getAllProcess(ctx);
            Log.e(TAG, "initProcess: 3all"+allProcess );
            progress = runningProcess * 100 / allProcess;
            Log.e(TAG, "initProcess: 4pro"+progress );
            mView.setProcess(runningProcess, allProcess, progress);
            Log.e(TAG, "initProcess: 5" );
            new Thread() {
                @Override
                public void run() {
                    processInfoList = appUtils.getAllRunningProcessInfo(ctx);
                    for (int i = 0; i < processInfoList.size(); i++) {
                        ProcessBean processBean = processInfoList.get(i);
                        if (processBean.isSys()) {
                            systemList.add(processBean);
                        } else {
                            customerlist.add(processBean);
                        }
                    }
                    processInfoList.clear();
                    processInfoList.addAll(customerlist);
                    processInfoList.addAll(systemList);
                    adapter = new ProcessManagerAdapter(ctx, processInfoList, customerlist, systemList);
                    adapter.setShowSys(showSys);
                    mView.setAdapter(adapter);
                    super.run();
                }
            }.start();
        } catch (Exception e) {
            Log.e(TAG, "initProcess: "+e.getLocalizedMessage().toString() );
            e.printStackTrace();
        }
    }

    @Override
    public void initMemoryInfo() {
        allMemory = appUtils.getAllMemory(ctx);
        availMemory = appUtils.getAvailMemory(ctx);
        usedMemory = allMemory - availMemory;
        strUsedMem = Formatter.formatFileSize(ctx, usedMemory);
        strAvailMemory = Formatter.formatFileSize(ctx, availMemory);
        memoryProgress = usedMemory * 100 / availMemory;
        mView.setMemoryInfo(strUsedMem, strAvailMemory, memoryProgress);
    }

    @Override
    public void itemClick(int position) {
        if (showSys) {
            ProcessBean processBean = processInfoList.get(position);
            if (!processBean.getPackageName().equals(ctx.getPackageName())) {
                processBean.setCheck(!processBean.isCheck());
                adapter.notifyDataSetChanged();
            }
        } else {
            ProcessBean processBean = customerlist.get(position);
            if (!processBean.getPackageName().equals(ctx.getPackageName())) {
                processBean.setCheck(!processBean.isCheck());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void itemLongClick(View view, int position) {
        if (showSys) {
            bean = processInfoList.get(position);
        } else {
            bean = customerlist.get(position);
        }
        mView.showPopupWindow(view);
    }

    @Override
    public void selectAll() {
        if (showSys) {
            for (int i = 0; i < processInfoList.size(); i++) {
                ProcessBean processBean = processInfoList.get(i);
                if (processBean.getPackageName().equals(ctx.getPackageName())) {
                    continue;
                }
                processBean.setCheck(true);
            }
            adapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < customerlist.size(); i++) {
                ProcessBean processBean = customerlist.get(i);
                if (processBean.getPackageName().equals(ctx.getPackageName())) {
                    continue;
                }
                processBean.setCheck(true);
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void selectReverse() {
        if (showSys) {
            for (int i = 0; i < processInfoList.size(); i++) {
                ProcessBean processBean = processInfoList.get(i);
                if (processBean.getPackageName().equals(ctx.getPackageName())) {
                    continue;
                }
                processBean.setCheck(!processBean.isCheck());
            }
            adapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < customerlist.size(); i++) {
                ProcessBean processBean = customerlist.get(i);
                if (processBean.getPackageName().equals(ctx.getPackageName())) {
                    continue;
                }
                processBean.setCheck(!processBean.isCheck());
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void killProcess() {
        ArrayList<ProcessBean> tempProcessInfoList = new ArrayList<>();
        if (showSys) {
            tempProcessInfoList.addAll(processInfoList);
        } else {
            tempProcessInfoList.addAll(customerlist);
        }
        int count = 0;
        int releaseMemory = 0;
        for (int i = 0; i < tempProcessInfoList.size(); i++) {
            ProcessBean processBean = tempProcessInfoList.get(i);
            if (processBean.getPackageName().equals(ctx.getPackageName())) {
                continue;
            }
            if (processBean.isCheck()) {
                processInfoList.remove(processBean);
                if (customerlist.contains(processBean)) {
                    customerlist.remove(processBean);
                }
                if (systemList.contains(processBean)) {
                    systemList.remove(processBean);
                }
                appUtils.killProcess(ctx, processBean.getPackageName());
                count++;
                releaseMemory += processBean.getSize();
            }
        }
        String strReleaseMemory = Formatter.formatFileSize(ctx, releaseMemory);
        Toasty.info(ctx,"杀死"+count+"个进程,释放"+strReleaseMemory+"空间", Toast.LENGTH_LONG).show();
        adapter.notifyDataSetChanged();
        runningProcess =runningProcess - count;
        int progressMem = runningProcess * 100 / allProcess;
        mView.setProcess(runningProcess,progressMem);

    }

    @Override
    public void initOpen() {
        showSys = sharedPreferencesUtils.getBoolean(Constant.SHOW_SYS_PROCESS,true);
        boolean serviceRunning = serviceUtils.isServiceRunning(ctx, LockCleanService.class.getName());
        mView.setOpenState(showSys,serviceRunning);
    }

    @Override
    public void setShowSys(boolean showSys) {
        this.showSys = showSys;
        sharedPreferencesUtils.setBoolean(Constant.SHOW_SYS_PROCESS,showSys);
        adapter.setShowSys(showSys);
        adapter.notifyDataSetChanged();
    }
}
