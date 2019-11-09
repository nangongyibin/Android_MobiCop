package com.ngyb.mobicop.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ngyb.mobicop.service.DogService;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 20:53
 */
public class DogReceiver extends BroadcastReceiver {
    private DogService dogService;

    public DogReceiver(DogService dogService) {
        this.dogService = dogService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        dogService.receiver(intent);
    }
}
