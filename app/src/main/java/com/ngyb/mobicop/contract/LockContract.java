package com.ngyb.mobicop.contract;

import com.ngyb.mobicop.activity.LockActivity;
import com.ngyb.mobicop.adapter.LockAdapter;
import com.ngyb.mvpbase.BaseView;
import com.ngyb.utils.bean.AppInfoBean;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 17:30
 */
public interface LockContract {
    interface Model {
    }

    interface View extends BaseView {
        void setAdapter(boolean isLock, LockAdapter adapter);

        void setTitle(boolean isLock, String str);
    }

    interface Presenter {
        void initApp(LockActivity lockActivity);

        void changelist(boolean isLock, AppInfoBean appInfoBean);

        void setTitle(boolean isLock, String str);

        void getSize();
    }
}
