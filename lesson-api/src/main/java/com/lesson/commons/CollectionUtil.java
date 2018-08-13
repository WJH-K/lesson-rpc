package com.lesson.commons;

import java.util.*;
import java.util.function.Function;

/**
 * Created by huangguoping on 2017/4/17.
 */
public class CollectionUtil {

    public static <T> List<T> toList(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        List<T> ts = new LinkedList<>();
        iterable.forEach(t1 -> ts.add(t1));
        return ts;
    }

    public static <K, V> Map<K, V> toMap(List<V> list, Function<V, K> keyFunc) {
        if (list == null) {
            return null;
        }
        Map<K, V> map = new HashMap<K, V>();
        list.stream().forEach(v -> map.put(keyFunc.apply(v), v));
        return map;
    }

    public static <K, V> Map<K, V> toMap(V[] arr, Function<V, K> keyFunc) {
        if (arr == null) {
            return null;
        }
        Map<K, V> map = new HashMap<K, V>();
        for (V v : arr) {
            map.put(keyFunc.apply(v), v);
        }
        return map;
    }

    public static <K, V> Map<K, V> toMap(Iterable<V> iterable, Function<V, K> keyFunc) {
        if (iterable == null) {
            return null;
        }
        Map<K, V> map = new HashMap<K, V>();
        iterable.forEach(v -> map.put(keyFunc.apply(v), v));
        return map;
    }

    public static <K, V> Map<K, V> toMap(Iterator<V> iterator, Function<V, K> keyFunc) {
        if (iterator == null) {
            return null;
        }
        Map<K, V> map = new HashMap<K, V>();
        while (iterator.hasNext()) {
            V v = iterator.next();
            map.put(keyFunc.apply(v), v);
        }
        return map;
    }

    public static <R, S> List<R> toList(Iterator<S> iterator, Function<S, R> keyFunc) {
        if (iterator == null) {
            return null;
        }
        List<R> ts = new ArrayList<R>();
        while (iterator.hasNext()) {
            S v = iterator.next();
            ts.add(keyFunc.apply(v));
        }
        return ts;
    }

    /**
     * 数组去重，数组顺序不变
     * @param array
     * @return
     */
    public static String[] deduplicateForArray(String[] array) {
        if (array != null && array.length > 0) {
            List<String> list = new LinkedList<>();
            String str = "";
            String separator = "";
            for (int i=0; i<array.length; i++) {
                if(!list.contains(array[i])) {
                    str = str + separator + array[i];
                    separator = ",";
                }
            }
            String[] newArray = str.split(",");
            return newArray;
        }
        return array;
    }
}
