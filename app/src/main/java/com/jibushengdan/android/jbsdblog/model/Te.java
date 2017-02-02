package com.jibushengdan.android.jbsdblog.model;

import java.security.PublicKey;
import java.util.List;

/**
 * Created by huhu on 2017/2/2.
 */

public class Te extends BaseModel{
    private List<Te.Get> get;

    public List<Get> getGet() {
        return get;
    }

    public void setGet(List<Get> get) {
        this.get = get;
    }

    public class Get{
        private String name;
        private String fname;
        private String Type;
        private String dat;
        private String abs;
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
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getDat() {
            return dat;
        }

        public void setDat(String dat) {
            this.dat = dat;
        }

        public String getAbs() {
            return abs;
        }

        public void setAbs(String abs) {
            this.abs = abs;
        }

        public String getMher() {
            return mher;
        }

        public void setMher(String mher) {
            this.mher = mher;
        }
    }
}
