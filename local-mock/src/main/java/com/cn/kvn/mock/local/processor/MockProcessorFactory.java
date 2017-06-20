package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import com.cn.kvn.mock.local.annotation_mock.Constraint;
import com.cn.kvn.mock.local.annotation_mock.MockBy;
import com.cn.kvn.mock.local.annotation_mock.MockReturn;
import com.cn.kvn.mock.local.domain.MockByItem;
import com.cn.kvn.mock.local.domain.MockConfig;
import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.domain.MockReturnItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

/**
 * @author wzy
 * @date 2017年6月19日 下午2:00:58
 */
public final class MockProcessorFactory implements InitializingBean {
	@Resource
	private ApplicationContext applicationContext;

	private List<MockProcessor<?>> mockProcessorList;

	public void setMockProcessorList(List<MockProcessor<?>> mockProcessorList) {
		this.mockProcessorList = mockProcessorList;
	}

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
		// 1. 对于注解类型的Mock，通过方法上的注解取MockProcessor
		MockReturn mr = method.getAnnotation(MockReturn.class);
		if (mr != null) {
			Class processorClass = mr.annotationType().getAnnotation(Constraint.class).processBy();
			MockReturnItem mri = new MockReturnItem();
			mri.setMockedClass(method.getDeclaringClass());
			mri.setMockedMethod(method);
			mri.setReturnValue(mr.value());
			// putIfAbsent : 加入mockConfig
			MockConfig.putIfAbsent(mri);
			return applicationContext.getBean(processorClass);
		}
		
		MockBy mb = method.getAnnotation(MockBy.class);
		if (mb != null) {
			Class processorClass = mb.annotationType().getAnnotation(Constraint.class).processBy();
			MockByItem mbi = new MockByItem();
			mbi.setMockedClass(method.getDeclaringClass());
			mbi.setMockedMethod(method);
			mbi.setDelegateClass(mb.useClass());
			mbi.setDelegateMethodName(mb.useMethod());
			mbi.setPassParameter(mb.passParameter());
			// putIfAbsent : 加入mockConfig
			MockConfig.putIfAbsent(mbi);
			return applicationContext.getBean(processorClass);
		}
		
		// 2. 对于配置类型的Mock，通过配置去取Processor
		MockItem mi = MockConfig.getMockItem(method);
		if(mi == null){
			return null;
		}
		return getMatchedProcessor(mi);
	}
	
	/**
	 * 取被 spring aop 代理的原始对象
	 */
	private Object unwrapProxy(Object bean) {

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

}
