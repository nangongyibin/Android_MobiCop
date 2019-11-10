package com.ngyb.mobicop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ngyb.mobicop.dao.AddressDao;
import com.ngyb.toastsummary.CustomerToast;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 17:13
 */
public class AddressReceiver extends BroadcastReceiver {
    private CustomerToast customerToast;
    private final AddressDao addressDao;

    public AddressReceiver(CustomerToast customerToast) {
        this.customerToast = customerToast;
        addressDao = new AddressDao();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String outGoingCall = getResultData();
        String address = addressDao.queryPhoneAddress(context, outGoingCall);
        customerToast.showToast(address);
    }
}
