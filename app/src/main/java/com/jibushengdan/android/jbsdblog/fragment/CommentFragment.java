package com.jibushengdan.android.jbsdblog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jibushengdan.android.jbsdblog.MainApplication;
import com.jibushengdan.android.jbsdblog.R;
import com.jibushengdan.android.jbsdblog.model.duoshuo.BaseModel;
import com.jibushengdan.android.jbsdblog.model.duoshuo.ListPosts;
import com.jibushengdan.android.jbsdblog.model.duoshuo.ListPostsParam;
import com.litesuits.http.listener.HttpListener;
import com.litesuits.http.request.JsonRequest;
import com.litesuits.http.request.StringRequest;
import com.litesuits.http.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huhu on 2017/2/19.
 */

public class CommentFragment extends Fragment {
    private RecyclerView recyclerView;
    private PagerAdapter recyclerAdapter;
    private String fanme;
    private String title;
    private List<JSONObject> list=new ArrayList<>();
    //Parser uaParser;

    public CommentFragment(String fanme, String title) {
        super();
        this.fanme=fanme;
        this.title=title;
        /*try {
            uaParser=new Parser();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        recyclerView = new RecyclerView(getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new PagerAdapter(getActivity(), list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), LinearLayoutManager.VERTICAL));

        init();

        return recyclerView;
    }

    private void init() {
        if(!list.isEmpty())return;
        ListPostsParam listPostsParam=new ListPostsParam(fanme,title);
        MainApplication.dsLiteHttp.executeAsync(new StringRequest(listPostsParam).setHttpListener(new HttpListener<String>() {
            @Override
            public void onSuccess(String s, Response<String> response) {
                super.onSuccess(s, response);
                JSONObject data= JSON.parseObject(s);
                if(data.getIntValue("code")==0){
                    String[] responses=data.getObject("response", String[].class);
                    if(responses.length!=0) {
                        JSONObject parentPosts=data.getJSONObject("parentPosts");
                        for (String key :responses) {
                            list.add(parentPosts.getJSONObject(key));
                        }
                        recyclerAdapter.notifyItemRangeInserted(0,list.size());
                        Log.d("list","size:"+list.size()+"responses.length"+responses.length);
                    }
                }
            }
        }));
    }

    public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.ItemViewHolder> {
        private List<JSONObject> datas;
        private LayoutInflater inflater;

        public PagerAdapter(Context context, List<JSONObject> datas) {
            this.datas = datas;
            inflater = LayoutInflater.from(context);
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.avatar)
            ImageView avatar;
            @BindView(R.id.name)
            TextView name;
            @BindView(R.id.os)
            TextView os;
            @BindView(R.id.browser)
            TextView browser;
            @BindView(R.id.message)
            TextView message;
            @BindView(R.id.created_at)
            TextView createdAt;
            @BindView(R.id.commentBtn)
            TextView commentBtn;
            @BindView(R.id.likeBtn)
            TextView likeBtn;

            public ItemViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.rv_item_comment, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, final int position) {
            JSONObject data = datas.get(position);
            /*BaseModel.Exceptions.Visitor author=data.getAuthor();
            holder.name.setText(author.getName());*/
            JSONObject author=data.getJSONObject("author");
            holder.name.setText(author.getString("name"));
            /*Client c = uaParser.parse(data.getAgent());
            holder.os.setText(c.os.toMiniString());
            holder.browser.setText(c.userAgent.toMiniString());*/
            holder.message.setText(Html.fromHtml(data.getString("message")));
            Log.d("message",data.getString("message"));
            holder.createdAt.setText(data.getString("created_at"));

            bindItemViewClickListener(holder, position);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void bindItemViewClickListener(final ItemViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //recyclerOnItemClick(view,holder,position);
                }
            });
        }
    }
}
