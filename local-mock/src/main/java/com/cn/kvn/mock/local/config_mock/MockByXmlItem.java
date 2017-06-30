package com.cn.kvn.mock.local.config_mock;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import com.cn.kvn.mock.local.domain.MockByItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;
import com.google.common.base.Strings;

/**
 * 使用xml的形式配置MockBy时使用
 * 
 * @author wzy
 * @date 2017年6月19日 下午5:44:02
 */
public class MockByXmlItem extends MockByItem implements IMockXmlConfigSupport {
	private String delegateMethodFullPath;
	private MockXmlItem mxi = new MockXmlItem(this);

	@PostConstruct
	public void init() {
		// 解析mocked信息
		mxi.resolveMockItem();
		// 解析delegate信息
		initDelegateInfo();
	}

	private void initDelegateInfo() {
		// 默认值
		if(Strings.isNullOrEmpty(delegateMethodFullPath)){
			Class<?> defaultDelegateClass = null;
			Method defaultDelegateMethod = null;
			String targetClassName = this.getMockedClass().getName();
			int lastPointIndex = targetClassName.lastIndexOf(".");
			// 默认指定[mock类全路径 = "mock." + 真实类的包名 + ".Mock" + 真实类的类名]
			String defaultClassName = "mock." + targetClassName.substring(0, lastPointIndex + 1) + "Mock" + targetClassName.substring(lastPointIndex + 1);
			try {
				defaultDelegateClass = Class.forName(defaultClassName);
			} catch (ClassNotFoundException e) {
				throw LocalMockErrorCode.CLASS_NOT_FOUND.exp(e, defaultClassName);
			}
			try {
				defaultDelegateMethod = defaultDelegateClass.getMethod(this.getMockedMethod().getName(), this.getMockedMethod().getParameterTypes());
			} catch (NoSuchMethodException|SecurityException e) {
				throw LocalMockErrorCode.METHOD_NOT_EXIST.exp(e, defaultClassName, this.getMockedMethod().getName());
			}
			this.setDelegateClass(defaultDelegateClass);
			this.setDelegateMethod(defaultDelegateMethod);
			return;
		}
		
		// 指定值
		MockXmlItem.ClassAndMethodResolver classAndMethod = new MockXmlItem.ClassAndMethodResolver() {
		};
		classAndMethod.resolveClassAndMethod(delegateMethodFullPath);
		this.setDelegateClass(classAndMethod.getResolvedClass());
		this.setDelegateMethod(classAndMethod.getResolvedMethod());
	}

	@Override
	public void setMockedMethodFullPath(String mockedMethodFullPath) {
		mxi.setMockedMethodFullPath(mockedMethodFullPath);
	}

	public void setDelegateMethodFullPath(String delegateMethodFullPath) {
		this.delegateMethodFullPath = delegateMethodFullPath;
	}

}
