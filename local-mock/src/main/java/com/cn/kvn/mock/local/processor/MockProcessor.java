package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;

import com.cn.kvn.mock.local.domain.MockItem;

/**
 * Mock方法处理接口
* @author wzy
* @date 2017年6月19日 下午1:59:27
*/
public interface MockProcessor<T extends MockItem> {
	boolean support(MockItem mockItem);
	boolean support(Class<?> mockItemClass);
	
	/**
	 * 
	 * @param mockItem
	 * @param returnType 真实方法的返回值类型
	 * @param method 真实方法
	 * @param args 真实方法的参数
	 * @return 返回mock结果
	 * @throws Exception 
	 */
	Object process(T mockItem, Class<?> returnType, Method method, Object[] args) throws Exception;
}
