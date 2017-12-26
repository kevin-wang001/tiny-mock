package com.kvn.mock.local.domain;

import java.lang.reflect.Method;

import com.kvn.mock.local.exception.LocalMockErrorCode;
import com.kvn.mock.local.processor.MockByProcessor;

/**
 * 单个mock配置项，配置时，必须 scope = prototype，各个mock配置互不影响
 * 
 * @author wzy
 * @date 2017年6月19日 上午11:32:23
 */
@Constraint(processBy = MockByProcessor.class)
public class MockByItem extends MockItem {
	/**
	 * 用于mock的class。<br/>
	 * Note: 这个class可以是Spring的bean，也可以是一个普通类
	 * 
	 * @see com.kvn.mock.local.processor.MockByProcessor#process(MockItem,
	 *      Class, java.lang.reflect.Method, Object[])
	 */
	private Class<?> delegateClass;
	
	private Method delegateMethod;


	public Class<?> getDelegateClass() {
		return delegateClass;
	}


	public void setDelegateClass(Class<?> delegateClass) {
		this.delegateClass = delegateClass;
	}


	public Method getDelegateMethod() {
		return delegateMethod;
	}


	public void setDelegateMethod(Method delegateMethod) {
		this.delegateMethod = delegateMethod;
	}


	public void check() {
		super.check();
		if (delegateClass == null) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("delegateClass不能为空");
		}
		if (delegateMethod == null) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("delegateMethod不能为空");
		}
	}
}
