package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

import com.cn.kvn.mock.local.MockAspect;
import com.cn.kvn.mock.local.cache.CacheKey;
import com.cn.kvn.mock.local.cache.MockCache;
import com.cn.kvn.mock.local.domain.MockByItem;
import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

/**
* @author wzy
* @date 2017年6月19日 下午2:28:50
*/
public class MockByProcessor implements MockInnerProcessor<MockByItem> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private MockAspect mockAspect;

	@Override
	public Object process(MockByItem mockItem, Class<?> returnType, Method method, Object[] args) throws Exception {
		return mockBy(mockItem, args);
	}
	
	private Object mockBy(MockByItem mbi, Object[] args) throws Exception {
		// 先从缓存取
		if(mockAspect.isNeedCache()){
			String cacheKey = new CacheKey(mbi, args).getKey();
			if(MockCache.CacheHolder.cache.asMap().containsKey(cacheKey)){
				logger.info("mock缓存命中.....cacheKey=" + cacheKey);
				return MockCache.CacheHolder.cache.getIfPresent(cacheKey);
			}
		}
		
		/** 代理类支持：1. Spring Bean 2. 普通类 **/
		Object bean = applicationContext.getBean(mbi.getDelegateClass());
		Object originInstance = bean == null ? mbi.getDelegateClass().newInstance() : unwrapProxy(bean);
		
		// 以mockByItem中的delegateMethod为准。如果delegateMethod有参数，则传递参数，否则不传递。
		int paramLength = mbi.getDelegateMethod().getParameterTypes().length;
		Class<?>[] clazzArr = null;
		if(paramLength >= 1){
			clazzArr = new Class[paramLength];
			for (int i = 0; i < clazzArr.length; i++) {
				clazzArr[i] = args[i].getClass();
			}
		}
		
		Object rtn = null;
		
		int delegateMethodParamCount = mbi.getDelegateMethod().getParameterTypes().length;
		if(delegateMethodParamCount == 0){ // 代理方法不需要传递参数
			rtn = mbi.getDelegateMethod().invoke(originInstance, null);
		} else if(delegateMethodParamCount == args.length){ // 传递参数
			rtn = mbi.getDelegateMethod().invoke(originInstance, args);
		} else {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("MockBy的delegateMethod方法的参数个数应该为0或者等于真实方法的参数个数");
		}
		
		// 缓存mock
		cacheMock(mbi, args, rtn);
		return rtn;
	}
	
	/**
	 * 取 spring aop 被代理的原生对象
	 */
	protected Object unwrapProxy(Object bean) {

		if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
			Advised advised = (Advised) bean;
			try {
				bean = advised.getTargetSource().getTarget();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	/**
	 * 缓存mock
	 * 
	 * @param mb
	 * @param args
	 * @param rtn
	 */
	private void cacheMock(MockByItem mbi, Object[] args, Object rtn) {
		if (!mockAspect.isNeedCache()) {
			return;
		}

		MockCache.CacheHolder.cache.put(new CacheKey(mbi, args).getKey(), rtn);
	}

	@Override
	public boolean support(MockItem mockItem) {
		return mockItem instanceof MockByItem;
	}

	@Override
	public boolean support(Class<?> mockItemClass) {
		return MockByItem.class.isAssignableFrom(mockItemClass);
	}

	

}
