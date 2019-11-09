package com.ngyb.mobicop.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.ngyb.mobicop.R;
import com.ngyb.mobicop.adapter.ScanAdapter;
import com.ngyb.mobicop.contract.AnitVirusContract;
import com.ngyb.mobicop.presenter.AnitVirusPresenter;
import com.ngyb.mvpbase.BaseMvpActivity;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 15:50
 */
public class AnitVirusActivity extends BaseMvpActivity<AnitVirusPresenter> implements AnitVirusContract.View, View.OnClickListener {

    private ArcProgress arcProgress;
    private TextView tvPackagename;
    private RelativeLayout rlScan;
    private ImageView ivLeft;
    private ImageView ivRight;
    private LinearLayout llImage;
    private TextView tvSafe;
    private Button btnScanAgain;
    private RelativeLayout rlScanAgain;
    private ListView lvAppVirus;
    private AnitVirusPresenter anitVirusPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_virus_anit;
    }

    @Override
    public void init() {
        initView();
        initClass();
        initListener();
    }

    private void initListener() {
        btnScanAgain.setOnClickListener(this);
    }

    private void initClass() {
        anitVirusPresenter = new AnitVirusPresenter(this);
        anitVirusPresenter.attachView(this);
        anitVirusPresenter.init();
    }

    private void initView() {
        arcProgress = findViewById(R.id.arc_progress);
        tvPackagename = findViewById(R.id.tv_packagename);
        rlScan = findViewById(R.id.rl_scan);
        ivLeft = findViewById(R.id.iv_left);
        ivRight = findViewById(R.id.iv_right);
        llImage = findViewById(R.id.ll_image);
        tvSafe = findViewById(R.id.tv_safe);
        btnScanAgain = findViewById(R.id.btn_scan_again);
        rlScanAgain = findViewById(R.id.rl_scan_again);
        lvAppVirus = findViewById(R.id.lv_app_virus);
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
    public void initVisible(ScanAdapter scanAdapter) {
        rlScan.setVisibility(View.VISIBLE);
        llImage.setVisibility(View.GONE);
        rlScanAgain.setVisibility(View.GONE);
        lvAppVirus.setAdapter(scanAdapter);
    }

    @Override
    public void setPackageName(String packagename) {
        tvPackagename.setText(packagename);
    }

    @Override
    public void scrollToPosition(int position) {
        lvAppVirus.smoothScrollToPosition(position);
    }

    @Override
    public void setProgress(int currentPosition, int maxSize) {
        arcProgress.setProgress(currentPosition * 100 / maxSize);
    }

    @Override
    public void setPostExecteOne(int count) {
        rlScan.setDrawingCacheEnabled(true);
        rlScan.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap drawingCache = rlScan.getDrawingCache();
        Bitmap leftBitmap = anitVirusPresenter.getLeftBitmap(drawingCache);
        Bitmap rightBitmap = anitVirusPresenter.getRightBitmap(drawingCache);
        ivLeft.setImageBitmap(leftBitmap);
        ivRight.setImageBitmap(rightBitmap);
        rlScan.setVisibility(View.GONE);
        llImage.setVisibility(View.VISIBLE);
        rlScanAgain.setVisibility(View.VISIBLE);
        if (count > 0) {
            tvSafe.setText("您的手机很不安全");
        } else {
            tvSafe.setText("您的手机很安全");
        }
        openAnimation();
    }

    private void openAnimation() {
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(ivLeft, "translationX", 0, -anitVirusPresenter.width);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(ivLeft, "alpha", 1.0f, 0.0f);
        ObjectAnimator ob3 = ObjectAnimator.ofFloat(ivRight, "translationX", 0, anitVirusPresenter.width);
        ObjectAnimator ob4 = ObjectAnimator.ofFloat(ivRight, "alpha", 1.0f, 0.0f);
        ObjectAnimator ob5 = ObjectAnimator.ofFloat(rlScanAgain, "alpha", 0.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ob1, ob2, ob3, ob4, ob5);
        animatorSet.setDuration(2000);
        btnScanAgain.setEnabled(false);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnScanAgain.setEnabled(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_scan_again:
                closeAnimation();
                break;
        }
    }

    private void closeAnimation() {
        ObjectAnimator ob1 = ObjectAnimator.ofFloat(ivLeft, "translationX",  -anitVirusPresenter.width,0);
        ObjectAnimator ob2 = ObjectAnimator.ofFloat(ivLeft, "alpha", 0.0f, 1.0f);
        ObjectAnimator ob3 = ObjectAnimator.ofFloat(ivRight, "translationX",  anitVirusPresenter.width,0);
        ObjectAnimator ob4 = ObjectAnimator.ofFloat(ivRight, "alpha", 0.0f, 1.0f);
        ObjectAnimator ob5 = ObjectAnimator.ofFloat(rlScanAgain, "alpha", 1.0f, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ob1, ob2, ob3, ob4, ob5);
        animatorSet.setDuration(2000);
        btnScanAgain.setEnabled(false);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnScanAgain.setEnabled(true);
                anitVirusPresenter.init();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
        });
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        if (anitVirusPresenter!=null){
            anitVirusPresenter.onDestroy();
        }
        super.onDestroy();
    }
}
