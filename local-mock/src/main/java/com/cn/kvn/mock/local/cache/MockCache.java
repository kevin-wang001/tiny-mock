package com.cn.kvn.mock.local.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * mock缓存，使用guava缓存
 * 
 * @author wzy
 * @date 2017年6月12日 上午9:21:36
 */
public class MockCache {
	public static class CacheHolder {
		 // 最多缓存100个key
		public static Cache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(100).build();
	}

}
