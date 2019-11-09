package com.ngyb.mobicop.contentobserver;

import android.database.ContentObserver;
import android.os.Handler;

import com.ngyb.mobicop.service.DogService;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 20:59
 */
public class DogContentObserver extends ContentObserver {
    private DogService dogService;

    public DogContentObserver(Handler handler, DogService dogService) {
        super(handler);
        this.dogService = dogService;
    }

    @Override
    public void onChange(boolean selfChange) {
        dogService.change();
        super.onChange(selfChange);
    }
}
