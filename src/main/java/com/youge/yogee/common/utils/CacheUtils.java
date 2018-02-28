/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.youge.yogee.common.utils;

import com.youge.yogee.common.utils.SpringContextHolder;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Cache工具类
 */
public class CacheUtils {

	private static CacheManager cacheManager = ((CacheManager) SpringContextHolder.getBean("cacheManager"));

	private static final String SYS_CACHE = "sysCache";

	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}

	//获取cache中key的element对象
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element==null?null:element.getObjectValue();
	}

	//获取cache并存入element对象
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	//移除cache为key的element对象
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}

	//强制写入磁盘，包括在内存中的element
	public static void flush(String cacheName, String key) {
		getCache(cacheName).flush();
	}

	//获取对该key对象的读操作锁
	public static void lockRead(String cacheName, String key) {
		getCache(cacheName).acquireReadLockOnKey(key);
	}

	//获取该key对象的写操作锁，此时可读，但不可写
	public static void lockWrite(String cacheName, String key) {
		getCache(cacheName).acquireWriteLockOnKey(key);
	}

	//释放该key对象的读操作锁
	public static void unlockRead(String cacheName, String key) {
		getCache(cacheName).releaseReadLockOnKey(key);
	}

	//释放该key对象的写操作锁，此时可读，但不可写
	public static void unlockWrite(String cacheName, String key) {
		getCache(cacheName).releaseWriteLockOnKey(key);
	}

	//该key对象是否在内存中
	public static void inMemory(String cacheName, String key) {
		getCache(cacheName).isElementInMemory(key);
	}

	//该key对象是否在磁盘中
	public static void onDisk(String cacheName, String key) {
		getCache(cacheName).isElementOnDisk(key);
	}

	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			cacheManager.addCache(cacheName);
			cache = cacheManager.getCache(cacheName);
			cache.getCacheConfiguration().setEternal(true);
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}

}
