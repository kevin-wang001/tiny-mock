package com.cn.kvn.mock.local.domain;

import java.util.List;

import com.cn.kvn.mock.local.processor.MockByHttpProcessor;

/**
 * 单个mock配置项，配置时，必须 scope = prototype，各个mock配置互不影响
 * 
 * @author wzy
 * @date 2017年6月19日 上午11:32:23
 */
@Constraint(processBy = MockByHttpProcessor.class)
public class MockByHttpItem extends MockItem {
	/**
	 * mock 服务的 http url
	 */
	private String serverPath;
	
	/**
	 * mock 服务的 http url 参数集
	 */
	private List<MockHttpParam> paramList;

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public List<MockHttpParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<MockHttpParam> paramList) {
		this.paramList = paramList;
	}
	
}
