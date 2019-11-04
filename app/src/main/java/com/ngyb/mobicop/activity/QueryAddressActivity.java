package com.ngyb.mobicop.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.contract.QueryAddressContract;
import com.ngyb.mobicop.presenter.QueryAddressPresenter;
import com.ngyb.mvpbase.BaseMvpActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 16:38
 */
public class QueryAddressActivity extends BaseMvpActivity<QueryAddressPresenter> implements QueryAddressContract.View, View.OnClickListener {

    private EditText etPhone;
    private Button btnQuery;
    private TextView tvResult;
    private QueryAddressPresenter queryAddressPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_query;
    }

    @Override
    public void init() {
        initClass();
        initView();
        initListener();
    }

    private void initClass() {
        queryAddressPresenter = new QueryAddressPresenter(this);
        queryAddressPresenter.attachView(this);
    }

    private void initListener() {
        btnQuery.setOnClickListener(this);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = s.toString();
                if (!TextUtils.isEmpty(phone)) {
                    queryAddressPresenter.queryPhoneAddress(phone);
                }
            }
        });
    }

    private void initView() {
        etPhone = findViewById(R.id.et_phone);
        btnQuery = findViewById(R.id.btn_query);
        tvResult = findViewById(R.id.tv_result);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                String phone = etPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone)) {
                    queryAddressPresenter.queryPhoneAddress(phone);
                } else {
                    tvResult.setText("");
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.shake);
                    etPhone.startAnimation(animation);
                }
                break;
        }
    }

    @Override
    public void setAddress(String address) {
        tvResult.setText(address);
    }
}
