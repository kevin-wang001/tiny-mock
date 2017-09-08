package com.cn.kvn.mock.local.config_mock;

import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

import java.lang.reflect.Method;

/**
 * @author wzy
 * @date 2017年6月22日 上午9:28:03
 */
public class MockXmlItem {
	private MockItem mockItem;

	/**
	 * 类似 com.cn.kvn.mock.local.test.ServiceA#method_13(String) 的配置
	 */
	private String mockedMethodFullPath;

	public MockXmlItem(MockItem mockItem) {
		this.mockItem = mockItem;
	}

	/**
	 * 
	 */
	protected void resolveMockItem() {
		MockXmlItem.ClassAndMethodResolver classAndMethod = new MockXmlItem.ClassAndMethodResolver() {
		};
		classAndMethod.resolveClassAndMethod(mockedMethodFullPath);
		mockItem.setMockedClass(classAndMethod.getResolvedClass());
		mockItem.setMockedMethod(classAndMethod.getResolvedMethod());
	}

	public void setMockedMethodFullPath(String mockedMethodFullPath) {
		this.mockedMethodFullPath = mockedMethodFullPath;
	}

	public MockItem getMockItem() {
		return mockItem;
	}

	public void setMockItem(MockItem mockItem) {
		this.mockItem = mockItem;
	}

	public static abstract class ClassAndMethodResolver {
		private Class<?> resolvedClass;
		private Method resolvedMethod;

		/**
		 * 按照格式解析出class和method
		 * 
		 * @param pattern
		 *            形如：
		 *            "com.cn.kvn.mock.local.test.MockServiceA#mockMethod_4()"
		 *            "com.cn.kvn.mock.local.test.MockServiceA#mockMethod_11(java.lang.String,com.cn.kvn.mock.local.test.Foo)"
		 */
		public void resolveClassAndMethod(String pattern) {
			String[] items = pattern.replaceAll("\\s", "").split("#");
			if (items.length != 2) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[?MethodFullPath=" + pattern + "]配置错误，含有多个'#'");
			}
			// 校验方法串
			if (items[1].matches("[0-9a-zA-Z_]+\\(([0-9a-zA-Z_.]*,)?[0-9a-zA-Z_.]*?\\)") == false) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[?MethodFullPath=" + pattern + "]配置错误，方法格式错误->" + items[1]);
			}

			try {
				this.resolvedClass = Class.forName(items[0]);
			} catch (ClassNotFoundException e) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[?MethodFullPath=" + pattern + "]配置错误，找不到被代理的类", e);
			}

			String[] methodItems = items[1].replaceAll("[()]", ",").split(",");

			Class<?>[] parameterTypes = methodItems.length >= 2 ? new Class<?>[methodItems.length - 1] : null;
			for (int i = 1; i < methodItems.length; i++) {

				try {
					Class<?> clazz = getClassIfPrimitive(methodItems[i]); // 基本类型解析
					clazz = clazz != null ? clazz : Class.forName(methodItems[i]);
					parameterTypes[i - 1] = clazz;
				} catch (ClassNotFoundException e) {
					throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[?MethodFullPath=" + pattern + "]配置错误，找不到参数的类->" + methodItems[i], e);
				}

			}
			try {
				this.resolvedMethod = resolvedClass.getMethod(methodItems[0], parameterTypes);
			} catch (NoSuchMethodException | SecurityException e) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + pattern + "]配置错误，找不到被代理的方法", e);
			}
		}

		/**
		 * 如果是基本类型，就获取相应的class
		 * 
		 * @param className
		 * @return
		 */
		private Class<?> getClassIfPrimitive(String className) {
			switch (className) {
			case "byte":
				return byte.class;
			case "short":
				return short.class;
			case "int":
				return int.class;
			case "long":
				return long.class;
			case "float":
				return float.class;
			case "double":
				return double.class;
			case "boolean":
				return boolean.class;
			case "char":
				return char.class;
			default:
				return null;
			}
		}

		public Class<?> getResolvedClass() {
			return resolvedClass;
		}

		public void setResolvedClass(Class<?> resolvedClass) {
			this.resolvedClass = resolvedClass;
		}

		public Method getResolvedMethod() {
			return resolvedMethod;
		}

		public void setResolvedMethod(Method resolvedMethod) {
			this.resolvedMethod = resolvedMethod;
		}

	}

}