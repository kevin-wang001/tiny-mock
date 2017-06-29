package com.cn.kvn.mock.local.config_mock;

import javax.annotation.PostConstruct;

import com.cn.kvn.mock.local.domain.MockByItem;

/**
 * 使用xml的形式配置MockBy时使用
 * @author wzy
 * @date 2017年6月19日 下午5:44:02
 */
public class MockByXmlItem extends MockByItem implements IMockXmlConfigSupport {
private MockXmlItem mxi = new MockXmlItem(this);
	
	@PostConstruct
	public void init(){
		mxi.initMockItem();
	}

	@Override
	public void setMockedMethodFullPath(String mockedMethodFullPath) {
		mxi.setMockedMethodFullPath(mockedMethodFullPath);
	}

	
	
}
