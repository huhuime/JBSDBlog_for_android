package com.jibushengdan.android.jbsdblog.tools;

import android.content.Context;
import android.graphics.Path;
import android.view.ViewGroup;

import com.jibushengdan.android.jbsdblog.widget.PathView;

/**
 * Created by about on 2017/1/24.
 */

public class LogoPath {
    public static void setPath(Context context, PathView pathView){
        setPath(context,pathView,6.5f,5f);
    }
    public static void setPath(Context context, PathView pathView, float dp, float dpPadding){
        ViewGroup.LayoutParams lp=pathView.getLayoutParams();

        final Path path = new Path();
        float b=Util.dip2px(context,dp),o=Util.dip2px(context,dpPadding);
        lp.width= (int) (5*b+o*2);
        lp.height= (int) (7*b+o*2);
        path.moveTo(1.6f*b+o, 6.6f*b+o);
        path.lineTo(1f*b+o,7f*b+o);
        path.lineTo(o,5f*b+o);
        path.lineTo(o,3f*b+o);
        path.lineTo(3f*b+o,o);
        path.lineTo(5.1f*b+o,2f*b+o);
        path.lineTo(3f*b+o,4f*b+o);
        path.lineTo(b+o,4f*b+o);
        path.lineTo(b+o,6f*b+o);
        path.lineTo(1.6f*b+o,6.6f*b+o);
        path.close();
        pathView.setPercentage(0.0f);
        pathView.setStepsMax(1f);
        pathView.setPath(path);//headerPathView.startPathAnimator(4,2000,2000);
    }
}
