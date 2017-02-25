package com.jibushengdan.android.jbsdblog.tools;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jibushengdan.android.jbsdblog.R;

/**
 * Created by huhu on 2017/2/19.
 */

public class ListMenu {
    private Context context;
    private String[] strs;
    private int normalColor;
    private int selectedColor;
    private int bgColor;
    private int selected;
    private View v;

    private PopupWindow popupWindow;
    private ListView listView;
    private final LayoutInflater mInflater;
    private BaseAdapter baseAdapter;

    private AdapterView.OnItemClickListener listener;
    private TextView textView;
    private View imageView;
    private float rotation1;
    private float rotation2;
    private ObjectAnimator objectAnimator;

    public ListMenu(Context context,View v,String[]strs) {
        this.context=context;
        mInflater = LayoutInflater.from(context);
        this.strs = strs;
        Resources res=context.getResources();
        this.normalColor = res.getColor(R.color.colorDofFont);
        this.selectedColor = res.getColor(R.color.colorRed);
        this.bgColor = res.getColor(R.color.bg);
        this.v=v;
        init();
    }

    private void init() {
        listView=new ListView(context);
        listView.setDividerHeight(1);
        listView.setBackgroundColor(bgColor);
        final int l=strs.length;
        final int h=Util.dip2px(context,l*40)+l-1;
        baseAdapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return l;
            }

            @Override
            public String getItem(int i) {
                return strs[i];
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView=(TextView)(view==null? mInflater.inflate(R.layout.list_view, viewGroup, false):view);
                textView.setText(getItem(i));
                if(selected==i){
                    textView.setTextColor(selectedColor);
                }else {
                    textView.setTextColor(normalColor);
                }


                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
        popupWindow=new PopupWindow(listView,300,h);
        popupWindow.setElevation(6);
        popupWindow.setOutsideTouchable(true);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(System.currentTimeMillis()-disTime<300)return;
                if(imageView!=null){
                    if(objectAnimator!=null){
                        objectAnimator.cancel();
                        objectAnimator = ObjectAnimator.ofFloat(imageView,"rotation",imageView.getRotation(),rotation2);
                    }else{
                        objectAnimator= ObjectAnimator.ofFloat(imageView,"rotation",rotation1,rotation2);
                    }
                    objectAnimator.start();
                }
                popupWindow.showAsDropDown(view);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected=position;
                baseAdapter.notifyDataSetChanged();
                popupWindow.dismiss();
                if(listener!=null)listener.onItemClick(parent,view,position,id);
                if(textView!=null)textView.setText(strs[position]);
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                disTime=System.currentTimeMillis();
                if(imageView!=null) {
                    if (objectAnimator != null) {
                        objectAnimator.cancel();
                        objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", imageView.getRotation(), rotation1);
                    } else {
                        objectAnimator = ObjectAnimator.ofFloat(imageView, "rotation", rotation2, rotation1);
                    }
                    objectAnimator.start();
                }
            }
        });
    }

    public void setListener(AdapterView.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setImageView(View imageView,float rotation) {
        this.imageView = imageView;
        this.rotation1 = rotation;
        this.rotation2 = rotation+180;
    }

    private long disTime=0;
}
