package com.cn.kvn.mock.local.processor;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.MockAspect;
import com.cn.kvn.mock.local.annotation_mock.MockReturn;
import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.domain.MockReturnItem;
import com.cn.kvn.mock.local.random.RandomCounterFactory;
import com.cn.kvn.mock.tool.MockUtil;

/**
* @author wzy
* @date 2017年6月19日 下午2:28:31
*/
public class MockReturnProcessor implements MockProcessor<MockReturnItem> {
	@Resource
	private MockAspect mockAspect;

	@Override
	public Object process(MockReturnItem mockItem, Class<?> returnType, Method method, Object[] args) throws Exception {
		return transformReturnValue(mockItem, returnType, method);
	}
	
	private Object transformReturnValue(MockReturnItem mri, Class<?> returnType, Method method) throws Exception {
		String returnValue = mri.getReturnValue().trim();

		/** THROW()处理 */
		if (MockReturn.THROW.equals(returnValue)) {
			throw new RuntimeException("mock 模拟抛出异常");
		}

		/** RANDOM()处理 */
		if (MockReturn.RANDOM.equals(returnValue)) {
			return MockUtil.randomMethodReturnTypeInstance(method);
		}

		/** RANDOM_EXCEPTION()处理 */
		if (MockReturn.RANDOM_EXCEPTION.equals(returnValue)) {
			AtomicLong counter = RandomCounterFactory.getCounter(mri.getMockKey());
			if (counter.getAndIncrement() % mockAspect.getRandomExpRate() != 0) { // 3次当中1次抛异常
				return MockUtil.randomMethodReturnTypeInstance(method);
			} else {
				throw new RuntimeException("mock 模拟抛出异常");
			}
		}

		/** 返回值为void */
		if (returnType.isAssignableFrom(Void.class)) {
			return null;
		}

		/** 返回值为String */
		if (returnType.isAssignableFrom(String.class)) {
			return returnValue;
		}

		/**
		 * 返回值为BigDecimal
		 */
		if (returnType.isAssignableFrom(BigDecimal.class)) {
			return new BigDecimal(returnValue);
		}

		/** 简单类型 或者 简单类型的包装类型 直接返回 */
		if (isPrimitiveOrWrapClass(returnType)) {
			String primitiveType = returnType.getName(); // 原始类型（boolean、char、byte、short、int、long、float、double）
			switch (primitiveType) {
			case "java.lang.Boolean":
			case "boolean":
				return Boolean.valueOf(returnValue);
			case "java.lang.Character":
			case "char":
				return returnValue.charAt(0);
			case "java.lang.Byte":
			case "byte":
				return Byte.valueOf(returnValue);
			case "java.lang.Short":
			case "short":
				return Short.valueOf(returnValue);
			case "java.lang.Integer":
			case "int":
				return Integer.valueOf(returnValue);
			case "java.lang.Long":
			case "long":
				return Long.valueOf(returnValue);
			case "java.lang.Float":
			case "float":
				return Float.valueOf(returnValue);
			case "java.lang.Double":
			case "double":
				return Double.valueOf(returnValue);
			default:
				throw new RuntimeException(primitiveType + "不支持，bug");

			}
		}

		/** 复杂对象通过json来转 */
		return JSON.parseObject(returnValue, returnType);
	}

	public static boolean isPrimitiveOrWrapClass(Class<?> clz) {
		if (clz.isPrimitive()) {
			return true;
		}

		return isWrapClass(clz);
	}

	public static boolean isWrapClass(Class<?> clz) {
		try {
			return ((Class<?>) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public boolean support(MockItem mockItem) {
		return mockItem instanceof MockReturnItem;
	}

	@Override
	public boolean support(Class<?> mockItemClass) {
		return MockReturnItem.class.isAssignableFrom(mockItemClass);
	}

}
