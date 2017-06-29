package com.cn.kvn.mock.local.config_mock;

import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

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
	protected void initMockItem() {
		String _mockedMethodFullPath = mockedMethodFullPath.replaceAll("\\s", "");
		String[] items = _mockedMethodFullPath.split("#");
		if(items.length != 2){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + mockedMethodFullPath + "]配置错误，含有多个'#'");
		}
		// 校验方法串
		if(items[1].matches("[0-9a-zA-Z_]+\\(([0-9a-zA-Z_.]*,)?[0-9a-zA-Z_.]*?\\)") == false){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + mockedMethodFullPath + "]配置错误，方法格式错误->" + items[1]);
		}
		
		try {
			mockItem.setMockedClass(Class.forName(items[0]));
		} catch (ClassNotFoundException e) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + mockedMethodFullPath + "]配置错误，找不到被代理的类", e);
		}
		
		String[] methodItems = items[1].replaceAll("[()]", ",").split(",");
		
		Class<?>[] parameterTypes = methodItems.length >= 2 ? new Class<?>[methodItems.length - 1] : null;
		for(int i=1; i<methodItems.length; i++){
			
			try {
				Class<?> clazz = Class.forName(methodItems[i]);
				parameterTypes[i-1] = clazz;
			} catch (ClassNotFoundException e) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + mockedMethodFullPath + "]配置错误，找不到参数的类->" + methodItems[i], e);
			}
			
		}
		try {
			mockItem.setMockedMethod(mockItem.getMockedClass().getMethod(methodItems[0], parameterTypes));
		} catch (NoSuchMethodException|SecurityException e) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + mockedMethodFullPath + "]配置错误，找不到被代理的方法", e);
		}
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
	
}
