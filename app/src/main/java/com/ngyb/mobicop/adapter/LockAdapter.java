package com.ngyb.mobicop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.presenter.LockPresenter;
import com.ngyb.utils.bean.AppInfoBean;

import java.sql.SQLTransactionRollbackException;
import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/5 19:41
 */
public class LockAdapter extends BaseAdapter {
    private LockPresenter lockPresenter;
    private List<AppInfoBean> list;
    private boolean isLock = false;
    private Context context;
    private TranslateAnimation leftToRightAnimation;
    private TranslateAnimation rightToLeftAnimation;

    public LockAdapter(LockPresenter lockPresenter, List<AppInfoBean> list, boolean isLock, Context context) {
        this.lockPresenter = lockPresenter;
        this.list = list;
        this.isLock = isLock;
        this.context = context;
        initAnimation();
    }

    private void initAnimation() {
        leftToRightAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        leftToRightAnimation.setDuration(1000);
        rightToLeftAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        rightToLeftAnimation.setDuration(1000);
    }

    @Override
    public int getCount() {
        String str = "";
        if (list != null && list.size() > 0) {
            if (isLock) {
                str = "已加锁（" + list.size() + "）";
            } else {
                str = "未加锁（" + list.size() + "）";
            }
            lockPresenter.setTitle(isLock,str);
            return list.size();
        } else {
            if (isLock) {
                str = "已加锁（0）";
            } else {
                str = "未加锁（0）";
            }
            lockPresenter.setTitle(isLock,str);
            return 0;
        }
    }

    @Override
    public AppInfoBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_lock, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AppInfoBean appInfoBean = getItem(position);
        viewHolder.ivIcon.setBackgroundDrawable(appInfoBean.getDrawable());
        viewHolder.tvName.setText(appInfoBean.getPackageName());
        viewHolder.tvName.setTextColor(Color.BLACK);
        if (isLock) {
            viewHolder.ivLock.setImageResource(R.drawable.selector_iv_lock_bg);
        } else {
            viewHolder.ivLock.setImageResource(R.drawable.selector_iv_unlock_bg);
        }
        final View viewAnimation = convertView;
        viewHolder.ivLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLock) {
                    rightToLeftAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            lockPresenter.changelist(isLock, appInfoBean);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    viewAnimation.startAnimation(rightToLeftAnimation);
                } else {
                    leftToRightAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            lockPresenter.changelist(isLock, appInfoBean);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    viewAnimation.startAnimation(leftToRightAnimation);
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private final ImageView ivIcon;
        private final TextView tvName;
        private final ImageView ivLock;

        public ViewHolder(View convertView) {
            ivIcon = convertView.findViewById(R.id.iv_icon);
            tvName = convertView.findViewById(R.id.tv_name);
            ivLock = convertView.findViewById(R.id.iv_lock);
        }
    }
}
