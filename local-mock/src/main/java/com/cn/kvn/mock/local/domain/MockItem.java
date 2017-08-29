package com.cn.kvn.mock.local.domain;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;
import com.cn.kvn.mock.local.processor.MockProcessor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * @author wzy
 * @date 2017年6月19日 下午2:08:07
 */
public abstract class MockItem {
	/** 被 mock 的方法的切点。用ThreadLocal线程隔离，防止并发问题。 */
	protected ThreadLocal<ProceedingJoinPoint> pjp = new ThreadLocal<>();

	/** 被mock的class，即真实的class */
	protected Class<?> mockedClass;

	/** 被mock的class的method，即真实的method */
	protected Method mockedMethod;

	/**
	 * 获取mock的key
	 * 
	 * @return
	 */
	public String getMockKey() {
		return getMockKey(mockedMethod);
	}
	
	/**
	 * 获取mock的key
	 * @param method
	 * @return
	 */
	public static String getMockKey(Method method){
		return method.getDeclaringClass().getName().concat("#").concat(method.getName()).concat(JSON.toJSONString(method.getParameterTypes()));
	}

	/**
	 * 检查配置信息是否完整
	 */
	public void check() {
		if (mockedClass == null) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("mockedClass不能为空");
		}
		if (mockedMethod == null) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("mockedMethod不能为空");
		}
	}

	/**
	 * 获取处理MockItem 的 processor 类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Class<? extends MockProcessor> getProcessorClass() {
		Constraint cons = this.getClass().getAnnotation(Constraint.class);
		if(cons == null){
			throw LocalMockErrorCode.MISSING_CONSTRAINT.exp(this.getClass().getName());
		}
		
		return cons.processBy();
	}
	
	public Class<?> getMockedClass() {
		return mockedClass;
	}

	public void setMockedClass(Class<?> mockedClass) {
		this.mockedClass = mockedClass;
	}

	public Method getMockedMethod() {
		return mockedMethod;
	}

	public void setMockedMethod(Method mockedMethod) {
		this.mockedMethod = mockedMethod;
	}

	public ProceedingJoinPoint getPjp() {
		return pjp.get();
	}

	public void setPjp(ProceedingJoinPoint pjp) {
		if(this.pjp.get() == null){
			synchronized (this.pjp) {
				if(this.pjp.get() == null){
					this.pjp.set(pjp);
				}
			}
		}
	}
}
