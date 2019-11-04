package com.ngyb.mobicop.presenter;

import android.content.Context;

import com.ngyb.mobicop.contract.QueryAddressContract;
import com.ngyb.mobicop.dao.AddressDao;
import com.ngyb.mvpbase.BasePresenter;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/4 16:48
 */
public class QueryAddressPresenter extends BasePresenter<QueryAddressContract.View> implements QueryAddressContract.Presenter {
    private Context context;
    private final AddressDao addressDao;

    public QueryAddressPresenter(Context context) {
        this.context = context;
        addressDao = new AddressDao();
    }

    @Override
    public void queryPhoneAddress(String phone) {
        String address = addressDao.queryPhoneAddress(context, phone);
        mView.setAddress(address);
    }
}
