package com.cn.kvn.mock.local.config_mock;

import javax.annotation.PostConstruct;

import com.cn.kvn.mock.local.domain.MockReturnItem;

/**
 * 使用xml的形式配置MockReturn时使用
 * 
 * @author wzy
 * @date 2017年6月19日 下午5:44:02
 */
public class MockReturnXmlItem extends MockReturnItem implements IMockXmlConfigSupport {

	private MockXmlItem mxi = new MockXmlItem(this);

	@PostConstruct
	public void init() {
		mxi.resolveMockItem();
	}

	@Override
	public void setMockedMethodFullPath(String mockedMethodFullPath) {
		mxi.setMockedMethodFullPath(mockedMethodFullPath);
	}


}
