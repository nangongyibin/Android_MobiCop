package com.ngyb.mobicop.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/10/31 17:57
 */
public class SharedPreferencesUtils {
    private final Context ctx;
    private SharedPreferences sp = null;

    public SharedPreferencesUtils(Context ctx) {
        this.ctx = ctx;
        if (sp == null) {
            sp = ctx.getSharedPreferences("MobiCop.xml", Context.MODE_PRIVATE);
        }
    }

    /**
     * @param key  键
     * @param value 值
     */
    public void setBoolean(String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("MobiCop.xml", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * @param key 键
     * @param defValue 默认值
     * @return
     */
    public boolean getBoolean(String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("MobiCop.xml", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }
}
