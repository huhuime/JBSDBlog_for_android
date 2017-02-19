package com.jibushengdan.android.jbsdblog.model;

import java.util.List;

/**
 * Created by huhu on 2017/2/2.
 */

public class Te extends BaseModel{
    private String name;
    private String fname;
    private String type;
    private String ztype;
    private String dat;
    private String det;
    private String mher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getMher() {
        return mher;
    }

    public void setMher(String mher) {
        this.mher = mher;
    }

    public String getZtype() {
        return ztype;
    }

    public void setZtype(String ztype) {
        this.ztype = ztype;
    }

    public String getDet() {
        return det;
    }

    public void setDet(String det) {
        this.det = det;
    }
}
