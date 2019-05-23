package com.unicom.api.cterminal.config.shiro;//AbstractCacheManager

import com.unicom.api.cterminal.util.Const;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * 实现shiro的CacheManager
 */
public class RedisCacheManager implements CacheManager {

    private long globExpire= Const.SESSION_TIMEOUT;

    public RedisCacheManager(){

    }

    public RedisCacheManager(Long globExpire){

    }
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroCache<K, V>(name, redisTemplate,globExpire);
    }
}
