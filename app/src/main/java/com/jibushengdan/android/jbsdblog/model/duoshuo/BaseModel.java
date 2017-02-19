package com.jibushengdan.android.jbsdblog.model.duoshuo;

import java.util.Map;

/**
 * Created by huhu on 2017/2/2.
 */

public class BaseModel {
    private Exceptions exceptions;
    public boolean isDone(){
        return getExceptions()!=null&&getExceptions().getCode()!=null&&getExceptions().getCode()==0;
    }

    public Exceptions getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exceptions exceptions) {
        this.exceptions = exceptions;
    }

    public class Exceptions{
        Integer code;
        Visitor visitor;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Visitor getVisitor() {
            return visitor;
        }

        public void setVisitor(Visitor visitor) {
            this.visitor = visitor;
        }

        public class Visitor{
            String user_id;
            String name;
            String url;
            String email;
            String avatar_url;
            int comments;
            Map<String,String> social_uid;
            Map<String,Boolean> repostOptions;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAvatar_url() {
                return avatar_url;
            }

            public void setAvatar_url(String avatar_url) {
                this.avatar_url = avatar_url;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }
            public void setComments(String comments) {
                try {
                    this.comments = Integer.parseInt(comments);
                }catch (Exception e){
                    e.printStackTrace();
                    this.comments=-1;
                }
            }

            public Map<String, String> getSocial_uid() {
                return social_uid;
            }

            public void setSocial_uid(Map<String, String> social_uid) {
                this.social_uid = social_uid;
            }

            public Map<String, Boolean> getRepostOptions() {
                return repostOptions;
            }

            public void setRepostOptions(Map<String, Boolean> repostOptions) {
                this.repostOptions = repostOptions;
            }
        }
    }
}
