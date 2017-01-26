package com.jibushengdan.android.jbsdblog;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

/**
 * Created by about on 2017/1/18.
 */

public class MainApplication extends Application {
    public static int statusBarHeight = -1;
    public static int navigationBarHeight = -1;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }
    private void init() {
        //获取status_bar_height资源的ID
        Resources rs = getResources();
        int resourceId =rs.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = rs.getDimensionPixelSize(resourceId);
            Log.i("MainApplication","statusBarHeight:"+statusBarHeight);
        }
        resourceId = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId > 0) {
           if( rs.getBoolean(resourceId)){
               resourceId =rs.getIdentifier("navigation_bar_height", "dimen", "android");
               if (resourceId > 0) {
                   //根据资源ID获取响应的尺寸值
                   navigationBarHeight =rs.getDimensionPixelSize(resourceId);
                   Log.i("MainApplication","navigationBarHeight:"+navigationBarHeight);
               }
           }
        }

    }
}
