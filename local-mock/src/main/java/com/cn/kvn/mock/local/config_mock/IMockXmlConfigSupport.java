package com.cn.kvn.mock.local.config_mock;

/**
* @author wzy
* @date 2017年6月22日 上午9:22:25
*/
public interface IMockXmlConfigSupport {
	/**
	 * 将MockedMethod赋值
	 */
	void initMockedMethod();
	
	void setMockedMethodName(String mockedMethodName);
	
	void setMockedMethodParameterCount(Integer mockedMethodParameterCount);
}
