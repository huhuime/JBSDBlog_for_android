package com.jibushengdan.android.jbsdblog.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.jibushengdan.android.jbsdblog.MainApplication;
import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.model.TeType;
import com.jibushengdan.android.jbsdblog.model.TeTypeParam;
import com.jibushengdan.android.jbsdblog.tools.ListMenu;
import com.jibushengdan.android.jbsdblog.tools.Util;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.activity_home)
    RelativeLayout activityHome;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.menuLayout)
    LinearLayout menuLayout;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.listView)
    RecyclerView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.logoLayout)
    LinearLayout logoLayout;

    private PagerAdapter recyclerAdapter;
    private int type = 0;
    private List<TeType.Get> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        init();
    }

    private boolean flat = true;

    private void init() {
        initMenu();
        initData();

    }

    private void initData() {
        listView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new PagerAdapter(this, list);
        listView.setAdapter(recyclerAdapter);
        /*listView.addItemDecoration(new DividerItemDecoration(
                this, LinearLayoutManager.VERTICAL));*/
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TeTypeParam teParam = new TeTypeParam();
                teParam.setType(type);
                MainApplication.liteHttp.executeAsync(new JsonRequest<TeType>(teParam, TeType.class).setHttpListener(new HttpListener<TeType>() {
                    @Override
                    public void onSuccess(TeType te, Response<TeType> response) {

                        list.clear();
                        list.addAll(te.getGet());
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                            recyclerAdapter.notifyItemRangeChanged(0, list.size());
                        }
                    }
                }));
            }
        });
        if (!list.isEmpty()) return;
        TeTypeParam teParam = new TeTypeParam();
        teParam.setType(type);
        refreshLayout.setRefreshing(true);
        MainApplication.liteHttp.executeAsync(new JsonRequest<TeType>(teParam, TeType.class).setHttpListener(new HttpListener<TeType>() {
            @Override
            public void onSuccess(TeType te, Response<TeType> response) {
                list.addAll(te.getGet());
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                    recyclerAdapter.notifyItemRangeInserted(0, list.size());
                }
            }
        }));
        final int d1 = Util.dip2px(this, 50), d2 = Util.dip2px(this, 143),d3 = Util.dip2px(this, 130),d4=Util.dip2px(this, 150),dd=d2-d1,d32=d3*d1/d2,dxx=Util.dip2px(this, 30);
        final int f2=d3-d32,f1=d4-dxx,fx=Util.dip2px(this, 170),f3=fx-d1;
        final ViewGroup.LayoutParams lp1 = imageView.getLayoutParams();
        final ViewGroup.LayoutParams lp2 = logoLayout.getLayoutParams();
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(recyclerView.getChildAt(0)==null)return;
                int d = recyclerView.getChildAt(0).getTop();
                if (d <= d1) {
                    setWH(dxx, lp1);
                    setWH(d1,d32, lp2);
                    subtitle.setAlpha(0);
                } else if (d <= fx) {
                    float f=(d-d1)*1.00f/f3;
                    setWH((int) (dxx+f*f1), lp1);
                    setWH((int) (d1+f*dd), (int) (d32+f*f2), lp2);
                    subtitle.setAlpha(0.5f*f);
                } else {
                    setWH(d4, lp1);
                    setWH(d2,d3, lp2);
                    subtitle.setAlpha(0.5f);
                }

            }
        });
    }
    private void setWH(int h,int w, ViewGroup.LayoutParams lp) {
        if (h == lp.height) return;
        lp.height=h;
        lp.width = w;
        logoLayout.setLayoutParams(lp);
    }
    private void setWH(int wh, ViewGroup.LayoutParams lp) {
        if (wh == lp.height) return;
        lp.height = lp.width = wh;
        imageView.setLayoutParams(lp);
    }

    private void initMenu() {
        final Drawable a = getDrawable(R.drawable.animated_vector_menu_in);
        final Drawable b = getDrawable(R.drawable.animated_vector_menu_out);
        int m = Util.dip2px(this, 78), n = Util.dip2px(this, 50);
        final ViewGroup.LayoutParams lp = menuBtn.getLayoutParams();
        final ValueAnimator v1 = ValueAnimator.ofInt(m, n, m);
        v1.setDuration(1000);
        v1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int i = (int) animation.getAnimatedValue();
                lp.height = lp.width = i;
                menuBtn.setLayoutParams(lp);
            }
        });

        /*menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn.setImageDrawable(flat ? a : b);
                flat = !flat;
                Animatable animatable = (Animatable) menuBtn.getDrawable();
                animatable.start();
                v1.start();
            }
        });*/
        ListMenu menu=new ListMenu(this,menuLayout,new String[]{"home","see the sun","music","就这样吧...","存档","杂物间"});
        menu.setTextView(title);
        menu.setListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type=position;
                if(position==0) ViewCompat.animate(title).alpha(0);
                else ViewCompat.animate(title).alpha(1);
                TeTypeParam teParam = new TeTypeParam();
                teParam.setType(type);
                refreshLayout.setRefreshing(true);

                MainApplication.liteHttp.executeAsync(new JsonRequest<TeType>(teParam, TeType.class).setHttpListener(new HttpListener<TeType>() {
                    @Override
                    public void onSuccess(TeType te, Response<TeType> response) {

                        list.clear();
                        list.addAll(te.getGet());
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                            recyclerAdapter.notifyDataSetChanged();
                        }
                    }
                }));
            }
        });
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
        } else {//退出程序
            this.finish();
            //System.exit(0);
        }
    }

    public void recyclerOnItemClick(View view, PagerAdapter.TeViewHolder holder, int position) {
        TeType.Get data = list.get(position);
        String type = data.getType();
        if (type.equals("3") || type.equals("5")) {
            PagerAdapter.Te3ViewHolder t3vh = (PagerAdapter.Te3ViewHolder) holder;
            Util.startActivityWhitSharedElement(this, DetailActivity.class, (JSONObject) JSONObject.toJSON(data), new View[]{t3vh.name});
        }
    }

    public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.TeViewHolder> {
        private final int[] layouts = new int[]{R.layout.rv_item_un, R.layout.rv_item_1, R.layout.rv_item_un, R.layout.rv_item_3, R.layout.rv_item_un, R.layout.rv_item_3};
        private List<TeType.Get> datas;
        private LayoutInflater inflater;

        public PagerAdapter(Context context, List<TeType.Get> datas) {
            this.datas = datas;
            inflater = LayoutInflater.from(context);
        }

        public class TeViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.abs)
            TextView abs;
            @BindView(R.id.year)
            TextView year;
            @BindView(R.id.date)
            TextView date;

            public TeViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        public class Te1ViewHolder extends TeViewHolder {

            public Te1ViewHolder(View view) {
                super(view);
            }
        }

        public class Te3ViewHolder extends TeViewHolder {
            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.tip)
            TextView tip;
            @BindView(R.id.comment)
            TextView comment;

            public Te3ViewHolder(View view, String baseTip) {
                super(view);
                tip.setText(baseTip);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return Integer.parseInt(datas.get(position).getType());
        }

        @Override
        public TeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            TeViewHolder holder = null;
            switch (viewType) {
                case 1:
                    view = inflater.inflate(R.layout.rv_item_1, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                        }
                    });
                    return new Te1ViewHolder(view);
                case 3:
                    view = inflater.inflate(R.layout.rv_item_3, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                        }
                    });
                    return new Te3ViewHolder(view, "就这样吧...");
                case 5:
                    view = inflater.inflate(R.layout.rv_item_3, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                        }
                    });
                    return new Te3ViewHolder(view, "杂物间");
                default:
                    view = inflater.inflate(R.layout.rv_item_un, parent, false);
                    ViewGroup.LayoutParams lp=view.getLayoutParams();
                    lp.height=0;
                    view.setLayoutParams(lp);
                    holder = new TeViewHolder(view);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(final TeViewHolder holder, final int position) {
            TeType.Get data = datas.get(position);
            holder.year.setText(data.getDat().substring(0,4));
            holder.date.setText(data.getDat().substring(5));
            switch (data.getType()) {
                case "1":
                    Te1ViewHolder holder1 = (Te1ViewHolder) holder;
                    holder1.abs.setText(Html.fromHtml("　　" + data.getAbs()));
                    holder1.abs.setMovementMethod(LinkMovementMethod.getInstance());
                    break;
                case "3":
                case "5":
                    Te3ViewHolder holder3 = (Te3ViewHolder) holder;
                    holder3.name.setText(data.getName());
                    holder3.abs.setText(Html.fromHtml(data.getAbs()));
                    if (data.getAbs().indexOf("</a>") > -1) {
                        holder3.abs.setMovementMethod(LinkMovementMethod.getInstance());
                        holder3.abs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recyclerOnItemClick(view, holder, position);
                            }
                        });
                    }
                    break;
            }
            bindItemViewClickListener(holder, position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void bindItemViewClickListener(final TeViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerOnItemClick(view, holder, position);
                }
            });
        }
    }
}
