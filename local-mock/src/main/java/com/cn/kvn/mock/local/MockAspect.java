package com.cn.kvn.mock.local;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.annotation_mock.MockBy;
import com.cn.kvn.mock.local.annotation_mock.MockReturn;
import com.cn.kvn.mock.local.domain.MockConfig;
import com.cn.kvn.mock.local.processor.MockProcessor;
import com.cn.kvn.mock.local.processor.MockProcessorFactory;

/**
 * Mock服务切面，用于提供Mock服务。
 * 
 * @see MockReturn
 * @see MockBy
 * @author wzy on 2017/6/1.
 */
public class MockAspect implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(MockAspect.class);
	/**
	 * 使用 @MockReturn("RANDOM_EXCEPTION") 时，出现异常的频率
	 * 
	 * @see #transformReturnValue(MockReturn, Class, Method)
	 */
	private int randomExpRate = 3;

	@Resource
	private MockProcessorFactory mockProcessorFactory;

	/**
	 * 是否开启mock
	 */
	protected boolean openMock = false;
	/**
	 * 对于@mockBy的接口是否需要缓存
	 */
	private boolean needCache = false;

	public void setNeedCache(boolean needCache) {
		this.needCache = needCache;
	}

	public boolean isNeedCache() {
		return needCache;
	}

	public void setRandomExpRate(int randomExpRate) {
		this.randomExpRate = randomExpRate;
	}

	public int getRandomExpRate() {
		return randomExpRate;
	}

	public void setOpenMock(boolean openMock) {
		this.openMock = openMock;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("run into mock aspect.....start.....mock开关：openMock={}", this.openMock);

		if (!openMock) { // 不开启mock
			logger.info("mock开关关闭，不开启mock.....");
			return pjp.proceed();
		}

		MethodSignature ms = (MethodSignature) pjp.getSignature();
		Method method = ms.getMethod();

		MockProcessor mockProcessor = mockProcessorFactory.getMatchedProcessor(method);
		if (mockProcessor == null) {
			return pjp.proceed();
		}

		logger.info("mock开启.....被mock的方法-->{}, needCache-->{}", method.getDeclaringClass() + "#" + method.getName(), this.needCache);
		Object response = mockProcessor.process(MockConfig.getMockItem(method), ms.getReturnType(), method, pjp.getArgs());
		logger.info("mock的返回结果 ===> {}", JSON.toJSONString(response));
		return response;
	}

	public static void main(String[] args) {
		// System.out.println(Void.class.isPrimitive());
		// System.out.println(Void.class.getName());
		// System.out.println("args = [" + int.class.getName() + "]");
		// System.out.println("args = [" + Long.class.getName() + "]");
		// System.out.println("args = [" + isWrapClass(Long.class) + "]");
		// System.out.println("args = [" + isWrapClass(long.class) + "]");
	}

	/**
	 * 留给子类去更改mock配置。比如：根据环境来设置 mock 开关
	 */
	@Override
	public void afterPropertiesSet() throws Exception {}

}
