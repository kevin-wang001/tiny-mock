package com.cn.kvn.mock.local.cache;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.domain.MockByItem;

/**
 * mock缓存的key。只针对 @mockBy 做缓存。<br/>
 * 通过缓存开关 {@link com.cn.kvn.mock.local.framework.testsupport.mock.MockAspect#setNeedCache(boolean)} 来控制是否缓存
* @author wzy
* @date 2017年6月12日 上午9:25:21
*/
public class CacheKey {
	/**
	 * 被mock的方法上的注解
	 */
	private MockByItem mockByItem;
	
	/**
	 * 被mock的方法的参数值
	 */
	private Object[] methodArgs;
	
	public CacheKey(MockByItem mockByItem, Object[] methodArgs) {
		super();
		this.mockByItem = mockByItem;
		this.methodArgs = methodArgs;
	}

	public void setMockBy(MockByItem mockByItem) {
		this.mockByItem = mockByItem;
	}

	public void setMethodArgs(Object[] methodArgs) {
		this.methodArgs = methodArgs;
	}

	/**
	 * 获取key:class#method+参数值
	 * @return
	 */
	public String getKey(){
		StringBuffer sb = new StringBuffer(mockByItem.getDelegateClass().getName());
		sb.append("#").append(mockByItem.getDelegateMethodName()).append(JSON.toJSONString(this.methodArgs));
		return sb.toString();
	}
	
}
