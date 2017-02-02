package com.jibushengdan.android.jbsdblog.model;

/**
 * Created by huhu on 2017/2/2.
 */

public class BaseModel {

    private Integer error;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }
    public boolean isDone(){
        return error==null||error==0;
    }
}
