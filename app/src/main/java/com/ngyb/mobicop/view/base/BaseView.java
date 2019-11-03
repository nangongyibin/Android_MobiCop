package com.ngyb.mobicop.view.base;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/4/15 10:21
 */
public interface BaseView {

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 数据获取失败
     *
     * @param throwable
     */
    void onError(Throwable throwable);

    /**
     * 获取数据成功
     */
    void onSuccess();

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
