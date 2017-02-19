package com.jibushengdan.android.jbsdblog.model;

import com.jibushengdan.android.jbsdblog.MainApplication;
import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.request.param.HttpRichParamModel;

/**
 * Created by huhu on 2017/2/2.
 */
@HttpUri("te.php")
public class TeParam extends HttpRichParamModel<Te> {
    private String te;

    public String getTe() {
        return te;
    }

    public void setTe(String te) {
        this.te = te;
    }
}
