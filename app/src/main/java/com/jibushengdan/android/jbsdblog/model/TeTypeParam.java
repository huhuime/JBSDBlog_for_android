package com.jibushengdan.android.jbsdblog.model;

import com.jibushengdan.android.jbsdblog.MainApplication;
import com.litesuits.http.annotation.HttpUri;
import com.litesuits.http.request.param.HttpRichParamModel;

/**
 * Created by huhu on 2017/2/2.
 */
@HttpUri(MainApplication.url+"te.php")
public class TeTypeParam extends HttpRichParamModel<TeType> {
    private int type=0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
