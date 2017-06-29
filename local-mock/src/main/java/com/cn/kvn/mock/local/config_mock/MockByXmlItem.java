package com.cn.kvn.mock.local.config_mock;

import javax.annotation.PostConstruct;

import com.cn.kvn.mock.local.domain.MockByItem;

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
