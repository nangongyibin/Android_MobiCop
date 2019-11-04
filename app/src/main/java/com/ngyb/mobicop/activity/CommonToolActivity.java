package com.ngyb.mobicop.activity;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.contract.CommonToolContract;
import com.ngyb.mobicop.presenter.CommonToolPresenter;
import com.ngyb.mvpbase.BaseMvpActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 15:36
 */
public class CommonToolActivity extends BaseMvpActivity<CommonToolPresenter> implements CommonToolContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_tool_common;
    }

    @Override
    public void init() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onSuccess() {

    }
}
