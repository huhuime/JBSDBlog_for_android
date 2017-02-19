package com.jibushengdan.android.jbsdblog.model.duoshuo;

import com.jibushengdan.android.jbsdblog.MainApplication;
import com.litesuits.http.annotation.HttpUri;

/**
 * Created by huhu on 2017/2/19.
 */

@HttpUri("threads/listPosts.json")
public class ListPostsParam extends BaseParamModel<ListPosts.Exceptions> {
    private String thread_key;
    private String url;
    private String title;

    private String source;
    private String thread_id;
    private int max_depth=2;
    private String order;
    private Integer limit;
    private Integer page;

    public ListPostsParam(String fanme, String title) {
        this.thread_key = fanme;
        this.url = MainApplication.url+fanme+".jbsd";
        this.title = title;
        require="site,visitor,nonce,lang";
    }

    public ListPostsParam(String thread_id, int max_depth, String order, int limit, int page) {
        this.thread_id = thread_id;
        this.max_depth = max_depth;
        this.order = order;
        this.limit = limit;
        this.page = page;
    }

    public String getThread_key() {
        return thread_key;
    }

    public void setThread_key(String thread_key) {
        this.thread_key = thread_key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public int getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(int max_depth) {
        this.max_depth = max_depth;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
