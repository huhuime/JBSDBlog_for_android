package com.jibushengdan.android.jbsdblog.model.duoshuo;

import com.jibushengdan.android.jbsdblog.MainApplication;
import com.litesuits.http.request.param.HttpRichParamModel;

/**
 * Created by huhu on 2017/2/2.
 */

public class BaseParamModel<T> extends HttpRichParamModel<T> {
    protected String require;
    private long site_ims=MainApplication.site_ims;
    private static String v="16.6.16";

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public long getSite_ims() {
        return site_ims;
    }

    public void setSite_ims(long site_ims) {
        this.site_ims = site_ims;
    }

    public static String getV() {
        return v;
    }

    public static void setV(String v) {
        BaseParamModel.v = v;
    }
}
