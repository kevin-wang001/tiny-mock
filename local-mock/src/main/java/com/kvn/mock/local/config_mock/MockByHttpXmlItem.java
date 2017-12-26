package com.kvn.mock.local.config_mock;

import javax.annotation.PostConstruct;

import com.kvn.mock.local.domain.MockByHttpItem;

/**
 * 使用xml的形式配置MockByHttp时使用
 * @author wzy
 * @date 2017年6月21日 下午5:44:02
 */
public class MockByHttpXmlItem extends MockByHttpItem implements IMockXmlConfigSupport {
	private MockXmlItem mxi = new MockXmlItem(this);
	
	@PostConstruct
	public void init(){
		mxi.resolveMockItem();
	}

	@Override
	public void setMockedMethodFullPath(String mockedMethodFullPath) {
		mxi.setMockedMethodFullPath(mockedMethodFullPath);
	}

	
}
