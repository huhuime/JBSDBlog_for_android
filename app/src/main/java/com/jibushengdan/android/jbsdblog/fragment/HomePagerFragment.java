package com.jibushengdan.android.jbsdblog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.jibushengdan.android.jbsdblog.MainApplication;
import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.activity.DetailActivity;
import com.jibushengdan.android.jbsdblog.model.TeType;
import com.jibushengdan.android.jbsdblog.model.TeTypeParam;
import com.jibushengdan.android.jbsdblog.tools.Util;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by huhu on 2017/2/2.
 */

public class HomePagerFragment extends Fragment {
    @BindView(R.id.vp_rv_list)
    RecyclerView vpRvList;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    private PagerAdapter recyclerAdapter;
    private int type = 0;
    private List<TeType.Get> list = new ArrayList<>();

    public HomePagerFragment() {
        super();
    }

    public HomePagerFragment(int type) {
        super();
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vp_fragment_item, container, false);
        unbinder = ButterKnife.bind(this, view);

        vpRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new PagerAdapter(getActivity(), list);
        vpRvList.setAdapter(recyclerAdapter);
        vpRvList.addItemDecoration(new DividerItemDecoration(
                getActivity(), LinearLayoutManager.VERTICAL));
        initData();
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TeTypeParam teParam = new TeTypeParam();
                teParam.setType(type);
                MainApplication.liteHttp.executeAsync(new JsonRequest<TeType>(teParam, TeType.class) {
                }
                        .setHttpListener(new HttpListener<TeType>() {
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
        //vpRvList.setON

        return view;
    }

    private void initData() {
        if (!list.isEmpty()) return;
        TeTypeParam teParam = new TeTypeParam();
        teParam.setType(type);
        refreshLayout.setRefreshing(true);
        MainApplication.liteHttp.executeAsync(new JsonRequest<TeType>(teParam, TeType.class) {
        }
                .setHttpListener(new HttpListener<TeType>() {
                    @Override
                    public void onSuccess(TeType te, Response<TeType> response) {
                        list.addAll(te.getGet());
                        if (refreshLayout != null) {
                            refreshLayout.setRefreshing(false);
                            recyclerAdapter.notifyItemRangeInserted(0, list.size());
                        }
                    }
                }));
    }

    public void recyclerOnItemClick(View view, PagerAdapter.TeViewHolder holder,int position){
        TeType.Get data=list.get(position);
        String type=data.getType();
        if(type.equals("3")||type.equals("5")){
            PagerAdapter.Te3ViewHolder t3vh=(PagerAdapter.Te3ViewHolder)holder;
            Util.startActivityWhitSharedElement(getActivity(), DetailActivity.class,(JSONObject)JSONObject.toJSON(data),new View[]{t3vh.name});
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.TeViewHolder> {
        private final  int[] layouts=new int[]{R.layout.rv_item_un,R.layout.rv_item_1,R.layout.rv_item_un,R.layout.rv_item_3,R.layout.rv_item_un,R.layout.rv_item_3};
        private List<TeType.Get> datas;
        private LayoutInflater inflater;

        public PagerAdapter(Context context, List<TeType.Get> datas) {
            this.datas = datas;
            inflater = LayoutInflater.from(context);
        }

        public class TeViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.abs)
            TextView abs;

            public TeViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
        public class Te1ViewHolder extends TeViewHolder{
            @BindView(R.id.dat)
            TextView dat;
            public Te1ViewHolder(View view) {
                super(view);
            }
        }
        public class Te3ViewHolder extends TeViewHolder{
            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.dat)
            TextView dat;
            @BindView(R.id.tip)
            TextView tip;
            @BindView(R.id.comment)
            TextView comment;
            public Te3ViewHolder(View view,String baseTip) {
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
            View view=null;
            TeViewHolder holder=null;
            switch (viewType){
                case 1:
                    view=inflater.inflate(R.layout.rv_item_1, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                        }
                    });
                    return new Te1ViewHolder(view);
                case 3:
                    view=inflater.inflate(R.layout.rv_item_3, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                        }
                    });
                    return new Te3ViewHolder(view,"就这样吧...");
                case 5:
                    view=inflater.inflate(R.layout.rv_item_3, parent, false);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
                        }
                    });
                    return new Te3ViewHolder(view,"杂物间");
                default:
                    view=inflater.inflate(R.layout.rv_item_un, parent, false);
                    holder= new TeViewHolder(view);
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
            TeType.Get data=datas.get(position);
            switch (data.getType()){
                case "1":
                    Te1ViewHolder holder1=(Te1ViewHolder)holder;
                    holder1.abs.setText(Html.fromHtml("　　"+data.getAbs()));
                    holder1.abs.setMovementMethod(LinkMovementMethod.getInstance());
                    holder1.dat.setText(data.getDat());
                    break;
                case "3":
                case "5":
                    Te3ViewHolder holder3=(Te3ViewHolder)holder;
                    holder3.name.setText(data.getName());
                    holder3.abs.setText(Html.fromHtml(data.getAbs()));
                    if(data.getAbs().indexOf("</a>")>-1){
                        holder3.abs.setMovementMethod(LinkMovementMethod.getInstance());
                        holder3.abs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recyclerOnItemClick(view,holder,position);
                            }
                        });
                    }
                    holder3.dat.setText(data.getDat());
                    break;
            }
            bindItemViewClickListener(holder,position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void bindItemViewClickListener(final TeViewHolder holder, final int position){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerOnItemClick(view,holder,position);
                }
            });
        }
    }
}
