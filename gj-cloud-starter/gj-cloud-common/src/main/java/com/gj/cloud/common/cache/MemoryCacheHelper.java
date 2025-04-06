package com.gj.cloud.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public class MemoryCacheHelper {
    private static final ConcurrentMap<String, Object> CACHE = new ConcurrentHashMap<>();

    public static void put(String key, Object value) {
        CACHE.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) CACHE.get(key);
    }

    public static <T> T get(String key, Supplier<T> supplier) {
        T cache = get(key);

        if (cache == null) {
            synchronized(MemoryCacheHelper.class) {
                cache = get(key);

                if (cache == null) {
                    cache = supplier.get();

                    if (cache != null) {
                        CACHE.put(key, cache);
                    }
                }
            }
        }

        return cache;
    }

    public static void remove(String key) {
        CACHE.remove(key);
    }
}
