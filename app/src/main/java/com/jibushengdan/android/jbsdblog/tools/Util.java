package com.jibushengdan.android.jbsdblog.tools;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jibushengdan.android.jbsdblog.MainApplication;


/**
 * Created by about on 2017/1/18.
 */

public class Util {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getRColor(Context context,@ColorRes int id) {
        return context.getResources().getColor(id);
    }

    public static void fixBarHeight(View... views){
        if (Build.VERSION.SDK_INT >= 21&& MainApplication.statusBarHeight>-1) {
            for (View v : views) {
                ViewGroup.LayoutParams lp=v.getLayoutParams();
                lp.height+= MainApplication.statusBarHeight;
                v.setLayoutParams(lp);
                v.setPadding(v.getPaddingLeft(),v.getPaddingTop()+MainApplication.statusBarHeight,v.getPaddingRight(),v.getPaddingBottom());
            }
        }
    }
    public static void fixNavBarHeight(View... views){
        if (Build.VERSION.SDK_INT >= 21&&MainApplication.navigationBarHeight>-1) {
            for (View v : views) {
                ViewGroup.LayoutParams lp=v.getLayoutParams();
                lp.height+= MainApplication.navigationBarHeight;
                v.setLayoutParams(lp);
                v.setPadding(v.getPaddingLeft(),v.getPaddingTop(),v.getPaddingRight(),v.getPaddingBottom()+MainApplication.navigationBarHeight);
            }
        }
    }
    public static void fixAllBarHeight(View... views){
        if (Build.VERSION.SDK_INT >= 21&&MainApplication.navigationBarHeight>-1) {
            for (View v : views) {
                ViewGroup.LayoutParams lp=v.getLayoutParams();
                lp.height+= MainApplication.statusBarHeight+MainApplication.navigationBarHeight;
                v.setLayoutParams(lp);
                v.setPadding(v.getPaddingLeft(),v.getPaddingTop()+MainApplication.statusBarHeight,v.getPaddingRight(),v.getPaddingBottom()+MainApplication.navigationBarHeight);
            }
        }
    }

    /**
     * 颜色转换
     * @param drawable 黑色图标资源
     * @param colors 指定颜色
     * @return
     */
    public static Drawable tintDrawable(@Nullable Drawable drawable, ColorStateList colors) {
        if(drawable==null||colors==null)return drawable;
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 设置顶部图片点击效果 API>=23（5）后不需要这么做
     * @param context
     * @param colorSelector
     * @param abs
     */
    public static void setAnyButtonDrawableTop(Context context, @ColorRes int colorSelector,TextView...abs){
        ColorStateList rbCSL=context.getResources().getColorStateList(colorSelector);
        for(TextView ab:abs){
            ab.setCompoundDrawables(null,Util.tintDrawable(ab.getCompoundDrawables()[1],rbCSL),null,null);
        }
    }
    public static void setIcoDrawable(Context context, @ColorRes int colorSelector,ImageView...ivs){
        ColorStateList rbCSL=context.getResources().getColorStateList(colorSelector);
        for(ImageView iv:ivs){
            iv.setImageDrawable(Util.tintDrawable(iv.getDrawable(),rbCSL));
        }
    }
    /**
     * 设置顶部图片点击效果（按textColor设置） API>=23（5）后不需要这么做
     * @param context
     * @param abs
     */
    public static void setAnyButtonDrawableTop(Context context, TextView...abs){
        for(TextView ab:abs){
            ColorStateList rbCSL=ab.getTextColors();
            ab.setCompoundDrawables(null,Util.tintDrawable(ab.getCompoundDrawables()[1],rbCSL),null,null);
        }
    }

    /**
     * 共享元素效果切换 Activity
     * @param activity 源Activity
     * @param cls 目的Activity
     * @param name 传值名称
     * @param value 传值
     * @param views 共享元素组
     */
    public static void startActivityWhitSharedElement(Activity activity, Class<?> cls, String name, JSONObject value, View[] views){
        Bundle bundle=null;
        //这个部分的代码拷贝自ActivityOptionsCompat.makeSceneTransitionAnimation
        if (Build.VERSION.SDK_INT >= 21&&views!=null) {
            Pair[] pairs = new Pair[views.length];
            for (int i = 0; i < views.length; i++) {
                pairs[i] = Pair.create(views[i],views[i].getTransitionName());
            }
            bundle=ActivityOptions.makeSceneTransitionAnimation( activity,pairs).toBundle();
        }
        Intent intent = new Intent(activity, cls);

        if(value!=null)intent.putExtra(SAWSE, value.toJSONString());
        ActivityCompat.startActivity(activity, intent, bundle);
    }
    public static final String SAWSE="startActivityWhitSharedElement";
    public static void startActivityWhitSharedElement(Activity activity, Class<?> cls, JSONObject value, View[] views){
        startActivityWhitSharedElement(activity,cls,SAWSE,value,views);
    }

    public static void setTextViewWhitSharedElement(Activity activity,String[]strs,TextView[] views){
        Intent intent =activity.getIntent();
        JSONObject value=JSON.parseObject(intent.getStringExtra(SAWSE));
        setTextView(value,strs,views);
    }

    /**
     * 设置TextView内容
     * @param value
     * @param strs
     * @param views
     */
    public static void setTextView(JSONObject value, String[]strs, TextView[] views) {
        if(strs.length!=views.length){
            //Log.e("Util.endActivityWhitSharedElement","strs和views长度不相等");
            return;
        }
        int l=strs.length;
        for (int i=0;i<l;i++){
            if(value.containsKey(strs[i]))views[i].setText(value.getString(strs[i]));
        }
    }

    /**
     * 设置单选框组和ViewPager联动
     * @param radioGroup
     * @param pager
     */
    public static void tabRadioGrouWithViewPager(final RadioGroup radioGroup, final ViewPager pager){
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                ((RadioButton)radioGroup.getChildAt(position)).setChecked(true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                pager.setCurrentItem(group.indexOfChild(group.findViewById(checkedId)));
            }
        });
    }

    public static Bitmap blurBitmap(Context context,Bitmap bitmap){


        if (Build.VERSION.SDK_INT >= 17) {
            //Let's create an empty bitmap with the same size of the bitmap we want to blur
            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

            //Instantiate a new Renderscript
            RenderScript rs = RenderScript.create(context);

            //Create an Intrinsic Blur Script using the Renderscript
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

            //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

            //Set the radius of the blur
            blurScript.setRadius(25.f);

            //Perform the Renderscript
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);

            //Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmap);

            //recycle the original bitmap
            bitmap.recycle();

            //After finishing everything, we destroy the Renderscript.
            rs.destroy();

            return outBitmap;

        }
        return bitmap;

    }
}
