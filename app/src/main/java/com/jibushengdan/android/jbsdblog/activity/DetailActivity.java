package com.jibushengdan.android.jbsdblog.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jibushengdan.android.jbsdblog.MainApplication;
import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.model.Te;
import com.jibushengdan.android.jbsdblog.model.TeParam;
import com.jibushengdan.android.jbsdblog.tools.ListMenu;
import com.jibushengdan.android.jbsdblog.tools.Util;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.response.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.backBtn)
    ImageButton backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.likeBtn)
    LinearLayout likeBtn;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.moreBtn)
    LinearLayout moreBtn;
    @BindView(R.id.moreTextView)
    TextView moreTextView;
    @BindView(R.id.activity_detail)
    RelativeLayout activityDetail;
    @BindView(R.id.moreImageView)
    ImageView moreImageView;

    private JSONObject data;
    private String fname = null;
    private String[] popups = {"最新", "最早", "最热"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        init();
    }

    private boolean isddd = true;

    private void init() {
        data = Util.setTextViewWhitSharedElement(this, new TextView[]{title});
        fname = data.getString("fname");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ViewTreeObserver vto = title.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                title.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                title.getHeight();
                final double w0 = title.getWidth();//控件宽度
                double w1 = title.getPaint().measureText(title.getText().toString());//文本宽度
                if (w1 > w0) {
                    /*ValueAnimator va=ValueAnimator.ofFloat(title.getTextSize(),title.getTextSize()/2.0f);
                    va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if(!isddd)return;
                            float cVal = (Float) valueAnimator.getAnimatedValue();
                            title.setTextSize(TypedValue.COMPLEX_UNIT_PX,cVal);
                            if(title.getPaint().measureText(title.getText().toString())<=w0){
                                isddd=false;
                            }
                        }
                    });
                    va.start();*/
                }
            }
        });


        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                loadData();
            }
        });

        ListMenu listMenu = new ListMenu(this, moreBtn, popups);
        listMenu.setListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listMenu.setTextView(moreTextView);
        listMenu.setImageView(moreImageView,-90);
    }

    private void loadData() {
        final int p = Util.dip2px(this, 6);
        TeParam teParam = new TeParam();
        teParam.setTe(fname);
        MainApplication.liteHttp.executeAsync(new JsonRequest<Te>(teParam, Te.class) {
        }
                .setHttpListener(new HttpListener<Te>() {
                    @Override
                    public void onSuccess(Te te, Response<Te> response) {
                        String[] dets = te.getDet().split("\\{@\\}");
                        contentLayout.removeAllViews();
                        for (String det : dets) {
                            TextView content = new TextView(DetailActivity.this);
                            content.setPadding(p, p, p, p);
                            content.setMovementMethod(LinkMovementMethod.getInstance());
                            content.setText(Html.fromHtml(det.replaceAll("\\{@\\}", "<hr/>")));
                            contentLayout.addView(content);
                        }
                        refreshLayout.setRefreshing(false);
                    }
                }));
    }
}
