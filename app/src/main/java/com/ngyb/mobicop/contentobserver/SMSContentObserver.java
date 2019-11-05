package com.ngyb.mobicop.contentobserver;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 10:32
 */
public class SMSContentObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SMSContentObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }
}
