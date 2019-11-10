package com.ngyb.mobicop.contract;

import android.view.View;

import com.ngyb.mobicop.adapter.ProcessManagerAdapter;
import com.ngyb.mvpbase.BaseView;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 11:51
 */
public interface ProcessManagerContract {
    interface Model {
    }

    interface View extends BaseView {
        void setProcess(int runningProcess, int allProcess, int progress);

        void setMemoryInfo(String strUsedMem, String strAvailMemory, long memoryProgress);

        void setAdapter(ProcessManagerAdapter adapter);

        void showPopupWindow(android.view.View view);

        void setProcess(int runningProcess, int progressMem);

        void setOpenState(boolean showSys, boolean serviceRunning);
    }

    interface Presenter {
        void initMemoryInfo();

        void itemClick(int position);

        void itemLongClick(android.view.View view, int position);

        void selectAll();

        void selectReverse();

        void killProcess();

        void initOpen();

        void setShowSys(boolean showSys);
    }
}
