package com.ngyb.mobicop.contract;

import android.graphics.Bitmap;

import com.ngyb.mobicop.adapter.ScanAdapter;
import com.ngyb.mvpbase.BaseView;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 16:27
 */
public interface AnitVirusContract {
    interface Model {
    }

    interface View extends BaseView {
        void initVisible(ScanAdapter scanAdapter);

        void setPackageName(String packagename);

        void scrollToPosition(int position);

        void setProgress(int currentPosition, int maxSize);

        void setPostExecteOne(int count);
    }

    interface Presenter {
        void init();

        Bitmap getLeftBitmap(Bitmap bitmap);

        Bitmap getRightBitmap(Bitmap bitmap);

        void onDestroy();
    }
}
