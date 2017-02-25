package com.jibushengdan.android.jbsdblog.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.tools.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.layout)
    LinearLayout layout;
    private Handler handler = null;
    private Runnable runnable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //longSvg.startPathAnimator(4,5000,4900);
        //AnimatedVectorDrawable avd = new AnimatedVectorDrawable();
        Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.start();
        AlphaAnimation alphaAnimation=new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(1000);
        layout.setAnimation(alphaAnimation);
        alphaAnimation.start();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                Util.startActivityWhitSharedElement(MainActivity.this, HomeActivity.class, null, new View[]{imageView});
                finish();
            }
        };
        handler.postDelayed(runnable, 1100);
    }

    @Override
    public void onBackPressed() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        super.onBackPressed();
    }
}
