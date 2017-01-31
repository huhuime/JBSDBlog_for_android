package com.jibushengdan.android.jbsdblog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jibushengdan.android.jbsdblog.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    @Override
    public void onBackPressed() {
        if (!mBackKeyPressed) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                @Override
                public void run() {
                    mBackKeyPressed = false;
                 }
            }, 2000);
        }
        else{//退出程序
            this.finish();
            //System.exit(0);
            }
    }
}
