package com.example.bpp.task;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
public class LinkedHashSetMultimap {
    private final Map map = Maps.newLinkedHashMap();


    public void put(Object key, Object val) {
        Set values = (Set) map.get(key);
        if (values == null) {
            values = Sets.newLinkedHashSet();
            map.put(key, values);
        }
        if (val != null) {
            values.add(val);
        }
    }


    public Set get(Object key) {
        Set values = (Set) map.get(key);
        return values == null ? Collections.EMPTY_SET : values;
    }

    public Set keySet() {
        return map.keySet();
    }


    public Set removeAll(Object key) {
        Set values = (Set) map.remove(key);
        return values == null ? Collections.EMPTY_SET : values;
    }


    public void remove(Object key, Object val) {
        Set values = (Set) map.get(key);
        if (values != null) {
            values.remove(val);
        }
    }


    public String toString() {
        return map.toString();
    }
}
