package com.gj.cloud.autoconfigure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisManager {
    private static final String KEY_LINK_STRS = ":";

    private final RedisTemplate<String, Object> redisTemplate;

    public Long increment(String name, String key) {
        return redisTemplate.opsForValue().increment(getRedisKey(name, key));
    }

    public Long decrement(String name, String key) {
        return redisTemplate.opsForValue().decrement(getRedisKey(name, key));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name, String key) {
        return (T) redisTemplate.opsForValue().get(getRedisKey(name, key));
    }

    public <T> T pop(String name, String key) {
        T item = get(name, key);

        evict(name, key);

        return item;
    }

    public <T> void put(String name, String key, T value) {
        redisTemplate.opsForValue().set(getRedisKey(name, key), value);
    }

    public <T> boolean putIfAbsent(String name, String key, T value, int seconds) {
        return !Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(getRedisKey(name, key) , value, seconds, TimeUnit.SECONDS));
    }

    public <T> boolean putIfAbsent(String name, String key, T value) {
        return !Boolean.FALSE.equals(redisTemplate.opsForValue().setIfAbsent(getRedisKey(name, key) , value));
    }

    public <T> void put(String name, String key, T value, int seconds) {
        redisTemplate.opsForValue().set(getRedisKey(name, key), value, seconds, TimeUnit.SECONDS);
    }


    @SuppressWarnings("unchecked")
    public <T> T getAndPut(String name, String key, T value) {
        return (T) redisTemplate.opsForValue().getAndSet(getRedisKey(name, key), value);
    }

    public void evict(String name, String key) {
        redisTemplate.opsForValue().getOperations().delete(getRedisKey(name, key));
    }

    public void evict(String name) {
        evictCacheByPattern(name + KEY_LINK_STRS + "*");
    }

    public void clear() {
        evictCacheByPattern("*");
    }

    public void expire(String name, String key, int seconds) {
        redisTemplate.opsForValue().getOperations().expire(getRedisKey(name, key), seconds, TimeUnit.SECONDS);
    }

    public boolean isExists(String name, String key) {
        return redisTemplate.hasKey(getRedisKey(name, key));
    }

    public boolean isExpire(String name, String key) {
        return redisTemplate.opsForValue().getOperations().getExpire(getRedisKey(name, key)) > 0 ? Boolean.FALSE.booleanValue() : Boolean.TRUE.booleanValue();
    }

    //-----------------------------------------------------------------
    // 私有方法
    //-----------------------------------------------------------------
    private String getRedisKey(String cacheName, String cacheKey) {
        return cacheName + KEY_LINK_STRS + cacheKey;
    }

    private void evictCacheByPattern(String pattern) {
        redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keySet = new HashSet<>();

            Cursor<byte[]> cursor = connection.keyCommands().scan(ScanOptions.scanOptions().match(pattern).build());
            while (cursor.hasNext()) {
                keySet.add(new String(cursor.next()));

                if (keySet.size() > 100) {
                    redisTemplate.delete(keySet);
                    keySet.clear();
                }
            }

            if (!keySet.isEmpty()) {
                redisTemplate.delete(keySet);
            }

            return null;
        });
    }
}
