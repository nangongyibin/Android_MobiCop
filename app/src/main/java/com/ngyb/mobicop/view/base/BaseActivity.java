package com.ngyb.mobicop.view.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/4/15 10:16
 */
public abstract class BaseActivity extends AppCompatActivity {
    public Context mContext;
    private static final String BUNDLE_FRAGMENTS_KEY = "android:support:fragments";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && this.clearFragmentsTag()) {
            //重建时清除 fragment的状态
            savedInstanceState.remove(BUNDLE_FRAGMENTS_KEY);
        }
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(this.getLayoutId());
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null && this.clearFragmentsTag()) {
            //销毁时不保存fragment的状态
            outState.remove(BUNDLE_FRAGMENTS_KEY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图
     */
    public abstract void init();

    protected boolean clearFragmentsTag() {
        return true;
    }
}

