package com.ngyb.mobicop.view.base;

import android.content.Context;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/4/15 10:20
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {
    protected T mPresenter;
    public Context mContext;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}