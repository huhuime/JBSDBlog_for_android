package com.jibushengdan.android.jbsdblog.model.duoshuo;

import java.util.Date;
import java.util.Map;

/**
 * Created by huhu on 2017/2/19.
 */

public class ListPosts extends BaseModel{
    private Exceptions exceptions;

    @Override
    public Exceptions getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exceptions exceptions) {
        this.exceptions = exceptions;
        super.setExceptions(exceptions);
    }

    public class Exceptions extends BaseModel.Exceptions{
        //Map<Integer,Visitor> users;
        Thread thread;
        Cursor cursor;
        String[] hotPosts;
        String[] response;
        Map<String,Post> parentPosts;

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public Cursor getCursor() {
            return cursor;
        }

        public void setCursor(Cursor cursor) {
            this.cursor = cursor;
        }

        public String[] getHotPosts() {
            return hotPosts;
        }

        public void setHotPosts(String[] hotPosts) {
            this.hotPosts = hotPosts;
        }

        public String[] getResponse() {
            return response;
        }

        public void setResponse(String[] response) {
            this.response = response;
        }

        public Map<String, Post> getParentPosts() {
            return parentPosts;
        }

        public void setParentPosts(Map<String, Post> parentPosts) {
            this.parentPosts = parentPosts;
        }

        public class Post{
            String post_id;
            Visitor author;
            String agent;
            String childrenArray;
            String message;
            int comments;
            int likes;
            Date created_at;
            String source;

            /*public Date getCreated_at() {
                return created_at;
            }

            public void setCreated_at(Date created_at) {
                this.created_at = created_at;
            }*/

            public String getPost_id() {
                return post_id;
            }

            public void setPost_id(String post_id) {
                this.post_id = post_id;
            }

            /*public Visitor getAuthor() {
                return author;
            }

            public void setAuthor(Visitor author) {
                this.author = author;
            }*/

            public String getAgent() {
                return agent;
            }

            public void setAgent(String agent) {
                this.agent = agent;
            }

            public String getChildrenArray() {
                return childrenArray;
            }

            public void setChildrenArray(String childrenArray) {
                this.childrenArray = childrenArray;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }

        public class Cursor{
            int total;
            int pages;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
            }
        }
        public class Thread{
            String thread_id;
            int comments;
            int likes;
            int weibo_reposts;
            int qqt_reposts;

            public String getThread_id() {
                return thread_id;
            }

            public void setThread_id(String thread_id) {
                this.thread_id = thread_id;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public int getWeibo_reposts() {
                return weibo_reposts;
            }

            public void setWeibo_reposts(int weibo_reposts) {
                this.weibo_reposts = weibo_reposts;
            }

            public int getQqt_reposts() {
                return qqt_reposts;
            }

            public void setQqt_reposts(int qqt_reposts) {
                this.qqt_reposts = qqt_reposts;
            }
        }
    }
}
