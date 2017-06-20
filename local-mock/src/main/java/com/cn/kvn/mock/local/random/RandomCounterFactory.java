package com.cn.kvn.mock.local.random;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 针对随机返回值或者异常的处理方式，取counter的容器
 * 
 * @author wzy
 * @date 2017年6月19日 下午3:05:43
 */
public abstract class RandomCounterFactory {
	// <KEY: class#method#arguments, VALUE: AtomicLong>
	public static final Map<String, AtomicLong> COUNTER_MAP = new ConcurrentHashMap<>();

	/**
	 * 获取计数器counter。
	 * @param key
	 * @return
	 */
	public static AtomicLong getCounter(String key) {
		if (COUNTER_MAP.get(key) != null) {
			return COUNTER_MAP.get(key);
		}

		// 对并发处理
		synchronized (RandomCounterFactory.class) {
			if (COUNTER_MAP.get(key) != null) {
				return COUNTER_MAP.get(key);
			}
			COUNTER_MAP.put(key, new AtomicLong(1L));
		}
		
		return COUNTER_MAP.get(key);
	}

}
