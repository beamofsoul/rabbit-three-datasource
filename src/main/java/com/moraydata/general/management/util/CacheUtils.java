package com.moraydata.general.management.util;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

public final class CacheUtils {

	private static CacheManager manager;
	static {
		manager = SpringUtils.getBean(CacheManager.class);
	}
	
	//不应被实例化的缓存工具类
	private CacheUtils() {
		//避免不小心在类内部调用构造器 Effective Java Edition 2 p.16
		throw new AssertionError();
	}
	
	public static Cache getCache(String cacheName) {
		return manager.getCache(cacheName);
	}
	
    public static Object get(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            ValueWrapper wrapper = cache.get(key);
            return wrapper == null ? null : wrapper.get();
        }
        return null;
    }
  
    public static void put(String cacheName, Object key, Object value) {
        Cache cache = getCache(cacheName);
        if (cache != null) cache.put(key, value);
    }
  
    public static void remove(String cacheName, Object key) {
        Cache cache = getCache(cacheName); 
        if (cache != null) cache.evict(key);
    }
}