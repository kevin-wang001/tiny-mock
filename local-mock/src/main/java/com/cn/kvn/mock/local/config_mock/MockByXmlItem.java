package com.cn.kvn.mock.local.config_mock;

import javax.annotation.PostConstruct;

import com.cn.kvn.mock.local.domain.MockByItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

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
		mxi.initMockItem();
		initMockDelegateInfo();
	}

	private void initMockDelegateInfo() {
		String _delegateMethodFullPath = delegateMethodFullPath.replaceAll("\\s", "");
		String[] items = _delegateMethodFullPath.split("#");
		if(items.length != 2){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + delegateMethodFullPath + "]配置错误，含有多个'#'");
		}
		// 校验方法串
		if(items[1].matches("[0-9a-zA-Z_]+\\(([0-9a-zA-Z_.]*,)?[0-9a-zA-Z_.]*?\\)") == false){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + delegateMethodFullPath + "]配置错误，方法格式错误->" + items[1]);
		}
		
		try {
			this.setDelegateClass(Class.forName(items[0]));
		} catch (ClassNotFoundException e) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + delegateMethodFullPath + "]配置错误，找不到被代理的类", e);
		}
		
		String[] methodItems = items[1].replaceAll("[()]", ",").split(",");
		
		Class<?>[] parameterTypes = methodItems.length >= 2 ? new Class<?>[methodItems.length - 1] : null;
		for(int i=1; i<methodItems.length; i++){
			
			try {
				Class<?> clazz = Class.forName(methodItems[i]);
				parameterTypes[i-1] = clazz;
			} catch (ClassNotFoundException e) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + delegateMethodFullPath + "]配置错误，找不到参数的类->" + methodItems[i], e);
			}
			
		}
		try {
			this.setDelegateMethod(this.getDelegateClass().getMethod(methodItems[0], parameterTypes));
		} catch (NoSuchMethodException|SecurityException e) {
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("[mockedMethodFullPath=" + delegateMethodFullPath + "]配置错误，找不到被代理的方法", e);
		}
	}

	@Override
	public void setMockedMethodFullPath(String mockedMethodFullPath) {
		mxi.setMockedMethodFullPath(mockedMethodFullPath);
	}

	public void setDelegateMethodFullPath(String delegateMethodFullPath) {
		this.delegateMethodFullPath = delegateMethodFullPath;
	}

}
