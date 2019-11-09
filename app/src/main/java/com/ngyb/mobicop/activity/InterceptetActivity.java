package com.ngyb.mobicop.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.contract.InterceptetContract;
import com.ngyb.mobicop.presenter.InterceptetPresenter;
import com.ngyb.mvpbase.BaseMvpActivity;
import com.ngyb.utils.AppUtils;

import es.dmoral.toasty.Toasty;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 21:14
 */
public class InterceptetActivity extends BaseMvpActivity<InterceptetPresenter> implements InterceptetContract.View, View.OnClickListener {

    private ImageView ivIcon;
    private TextView tvName;
    private EditText etPsd;
    private Button btnOk;
    private InterceptetPresenter interceptetPresenter;
    private String packagename;
    private AppUtils appUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_interceptet;
    }

    @Override
    public void init() {
        initView();
        initClass();
        initIntent();
        initListener();
    }

    private void initListener() {
        btnOk.setOnClickListener(this);
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            packagename = intent.getStringExtra("packagename");
        }
        String name = appUtils.getAppName(this, packagename);
        Drawable drawable = appUtils.getAppDrawable(this, packagename);
        ivIcon.setBackgroundDrawable(drawable);
        tvName.setText(name);
    }

    private void initClass() {
        interceptetPresenter = new InterceptetPresenter();
        interceptetPresenter.attachView(this);
        appUtils = new AppUtils();
    }

    private void initView() {
        ivIcon = findViewById(R.id.iv_icon);
        tvName = findViewById(R.id.tv_name);
        etPsd = findViewById(R.id.et_psd);
        btnOk = findViewById(R.id.btn_ok);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent("android.intent.acion.MAIN");
        intent.addCategory("android.intent.category.HOME");
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String psd = etPsd.getText().toString().trim();
                if (!TextUtils.isEmpty(psd) && psd.equals("123")) {
                    Intent intent = new Intent("Intent.action.UNLOCK");
                    intent.putExtra("packagename", packagename);
                    sendBroadcast(intent);
                    finish();
                } else {
                    Toasty.info(this, "密码不正确", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
