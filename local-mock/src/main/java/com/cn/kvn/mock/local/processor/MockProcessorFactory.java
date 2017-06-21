package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.cn.kvn.mock.local.domain.MockConfig;
import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

/**
 * @author wzy
 * @date 2017年6月19日 下午2:00:58
 */
public final class MockProcessorFactory implements InitializingBean {
	@Resource
	private ApplicationContext applicationContext;

	@SuppressWarnings("rawtypes")
	private List<MockProcessor> mockProcessorList;

	@SuppressWarnings("rawtypes")
	public void setMockProcessorList(List<MockProcessor> mockProcessorList) {
		this.mockProcessorList = mockProcessorList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, MockOuterProcessor> mockProcessorMap = applicationContext.getBeansOfType(MockOuterProcessor.class);
		// 添加用户自定义扩展的 MockProcessor
		mockProcessorList.addAll(mockProcessorMap.values());
	}

	@SuppressWarnings("rawtypes")
	public MockProcessor getMatchedProcessor(MockItem mockItem) {
		for (MockProcessor processor : mockProcessorList) {
			if (processor.support(mockItem)) {
				return processor;
			}
		}
		
		throw LocalMockErrorCode.MOCKRITEM_NOT_SUPPORT.exp(mockItem.getClass().getName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MockProcessor getMatchedProcessor(Class<?> mockItemClass) {
		for (MockProcessor processor : mockProcessorList) {
			if (processor.support(mockItemClass)) {
				return processor;
			}
		}
		throw LocalMockErrorCode.MOCKRITEM_NOT_SUPPORT.exp(mockItemClass.getName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MockProcessor getMatchedProcessor(Method method) {
		MockItem mi = MockConfig.getAndSetMockItem(method);
		
		if(mi == null){
			return null;
		}
		
		// 通过MockItem获取MockProcessor的class，从而获取MockProcessor
		Class processorClass = mi.getProcessorClass();
		
		return applicationContext.getBean(processorClass);
	}

}
