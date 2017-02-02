package com.jibushengdan.android.jbsdblog.tools;

import com.alibaba.fastjson.JSON;
import com.litesuits.http.data.Json;

import java.lang.reflect.Type;

/**
 * Created by huhu on 2017/2/2.
 */

public class FastJson extends Json {
    @Override
    public String toJson(Object src) {
        return JSON.toJSONString(src);
    }
    @Override
    public <T> T toObject(String json, Class<T> claxx) {
        return JSON.parseObject(json, claxx);
    }
    @Override
    public <T> T toObject(String s, Type type) {
        return JSON.parseObject(s, type);
    }
    @Override
    public <T> T toObject(byte[] bytes, Class<T> claxx) {
        return JSON.parseObject(bytes, claxx);
    }
}