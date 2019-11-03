package com.ngyb.mobicop.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/4/15 10:17
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        init(view);
        return view;
    }

    /**
     * 初始化视图
     *
     * @param view
     */
    protected abstract void init(View view);

    protected abstract int getLayoutId();

    @Override
    public void onResume() {
        super.onResume();
    }
}
