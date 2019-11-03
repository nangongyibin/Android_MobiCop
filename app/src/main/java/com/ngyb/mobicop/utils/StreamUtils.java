package com.ngyb.mobicop.utils;

import android.os.Build;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/3 11:48
 */
public class StreamUtils {
    public void close(AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                autoCloseable.close();
            }
        }
    }
}
