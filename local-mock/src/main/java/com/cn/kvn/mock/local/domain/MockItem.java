package com.cn.kvn.mock.local.domain;

import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

/**
* @author wzy
* @date 2017年6月19日 下午2:08:07
*/
public abstract class MockItem {
	/** 被mock的class，即真实的class */
	protected Class<?> mockedClass;
	
	/** 被mock的class的method，即真实的method */
	protected Method mockedMethod;
	
	/**
	 * 获取mock的key
	 * @return
	 */
	public String getMockKey(){
		return mockedClass.getName().concat("#").concat(mockedMethod.getName()).concat(JSON.toJSONString(mockedMethod.getParameterTypes()));
	}
	
	/**
	 * 检查配置信息是否完整
	 */
	public void check(){
		if(mockedClass == null){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("mockedClass不能为空");
		}
		if(mockedMethod == null){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("mockedMethod不能为空");
		}
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

}
