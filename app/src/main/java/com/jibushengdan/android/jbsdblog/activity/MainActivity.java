package com.jibushengdan.android.jbsdblog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.tools.Util;
import com.jibushengdan.android.jbsdblog.widget.PathView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.long_svg)
    PathView longSvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        longSvg.startPathAnimator(4,5000,4900);

        new Handler().postDelayed(new Runnable(){
            public void run() {
                longSvg.stopPathAnimator(true, new PathView.PathAnimatorListener() {
                    @Override
                    public void onStop() {
                        Util.startActivityWhitSharedElement(MainActivity.this,HomeActivity.class,null,new View[]{longSvg});
                        //startActivity(new Intent(MainActivity.this,HomeActivity.class));
                        finish();
                    }
                });
            }
        }, 2000);
    }
}
