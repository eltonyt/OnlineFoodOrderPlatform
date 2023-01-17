package com.elton.foodorder.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// RESPONSE OBJECT
@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map map = new HashMap<>();

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
