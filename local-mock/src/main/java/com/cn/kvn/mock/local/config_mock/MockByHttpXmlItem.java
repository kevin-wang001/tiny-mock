package com.cn.kvn.mock.local.config_mock;

import javax.annotation.PostConstruct;

import com.cn.kvn.mock.local.domain.MockByHttpItem;

/**
 * 使用xml的形式配置MockByHttp时使用
 * @author wzy
 * @date 2017年6月21日 下午5:44:02
 */
public class MockByHttpXmlItem extends MockByHttpItem implements IMockXmlConfigSupport {
	private MockXmlItem mxi = new MockXmlItem(this);
	
	@PostConstruct
	public void init(){
		initMockedMethod();
	}

	@Override
	public void initMockedMethod() {
		mxi.initMockedMethod();
	}

	@Override
	public void setMockedMethodName(String mockedMethodName) {
		mxi.setMockedMethodName(mockedMethodName);
	}

	@Override
	public void setMockedMethodParameterCount(Integer mockedMethodParameterCount) {
		mxi.setMockedMethodParameterCount(mockedMethodParameterCount);
	}
	
}
