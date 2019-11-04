package com.ngyb.mobicop.contract;

import com.ngyb.mvpbase.BaseView;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 16:48
 */
public interface QueryAddressContract {
    interface Model {
    }

    interface View extends BaseView {
        void setAddress(String address);
    }

    interface Presenter {
        void queryPhoneAddress(String phone);
    }
}
