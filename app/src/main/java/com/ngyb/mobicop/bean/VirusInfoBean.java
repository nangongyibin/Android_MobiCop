package com.ngyb.mobicop.bean;

import android.graphics.drawable.Drawable;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 16:33
 */
public class VirusInfoBean {
    public String packagename;
    public String name;
    public Drawable drawable;
    public boolean isVirus;

    public VirusInfoBean(String packagename, String name, Drawable drawable, boolean isVirus) {
        this.packagename = packagename;
        this.name = name;
        this.drawable = drawable;
        this.isVirus = isVirus;
    }
}
