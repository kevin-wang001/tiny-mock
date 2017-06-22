package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.domain.MockByHttpItem;
import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.remote.http_client.HttpClient;

/**
* @author wzy
* @date 2017年6月20日 下午6:15:13
*/
public class MockByHttpProcessor implements MockInnerProcessor<MockByHttpItem> {
	private static final Logger logger = LoggerFactory.getLogger(MockByHttpProcessor.class);

	@Override
	public boolean support(MockItem mockItem) {
		return mockItem instanceof MockByHttpItem;
	}

	@Override
	public boolean support(Class<?> mockItemClass) {
		return MockByHttpItem.class.isAssignableFrom(mockItemClass);
	}

	@Override
	public Object process(MockByHttpItem mockItem, Class<?> returnType, Method method, Object[] args) throws Exception {
		mockItem.setArgs(args);
		mockItem.buildParamList();
		logger.info("MockByHttp ---> remoteUrl:{}, args:{}", mockItem.getServerPath(), mockItem.generateJsonParams());
		return HttpClient.post(mockItem);
	}
	
	public static void main(String[] args) {
		logger.info("MockByHttp ---> remoteUrl:{}{}", "xxx", "00");
	}


}
