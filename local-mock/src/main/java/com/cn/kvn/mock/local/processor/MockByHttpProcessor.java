package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;

import com.cn.kvn.mock.local.domain.MockByHttpItem;
import com.cn.kvn.mock.local.domain.MockItem;

/**
* @author wzy
* @date 2017年6月20日 下午6:15:13
*/
public class MockByHttpProcessor implements MockProcessor<MockByHttpItem> {

	@Override
	public boolean support(MockItem mockItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean support(Class<?> mockItemClass) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object process(MockByHttpItem mockItem, Class<?> returnType, Method method, Object[] args) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
