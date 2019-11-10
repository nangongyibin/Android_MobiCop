package com.ngyb.mobicop.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import androidx.annotation.Nullable;

import com.ngyb.mobicop.dao.AddressDao;
import com.ngyb.mobicop.receiver.AddressReceiver;
import com.ngyb.toastsummary.CustomerToast;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/3 17:57
 */
public class AddressService extends Service {
    private TelephonyManager tm;
    private CustomerToast customerToast;
    private AddressDao addressDao;
    private MyPhoneStateListener listener;
    private AddressReceiver addressReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        addressDao = new AddressDao();
        listener = new MyPhoneStateListener();
        tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        customerToast = new CustomerToast(this);
        addressReceiver = new AddressReceiver(customerToast);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(addressReceiver, intentFilter);
    }

    class MyPhoneStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    customerToast.hiddenToast();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    String address = addressDao.queryPhoneAddress(AddressService.this, phoneNumber);
                    customerToast.showToast(address);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
            super.onCallStateChanged(state, phoneNumber);
        }
    }

    @Override
    public void onDestroy() {
        if (tm != null && listener != null) {
            tm.listen(listener, PhoneStateListener.LISTEN_NONE);
        }
        if (addressReceiver != null) {
            unregisterReceiver(addressReceiver);
        }
        super.onDestroy();
    }
}
