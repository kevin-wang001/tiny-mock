package com.cn.kvn.mock.local.domain;

import com.cn.kvn.mock.local.exception.LocalMockErrorCode;
import com.cn.kvn.mock.local.processor.MockReturnProcessor;

/**
 * 单个mock配置项，配置时，必须 scope = prototype，各个mock配置互不影响
 * 
 * @author wzy
 * @date 2017年6月19日 上午11:28:51
 */
@Constraint(processBy = MockReturnProcessor.class)
public class MockReturnItem extends MockItem {
	/**
	 * mock的返回值。
	 * 复杂的返回类型的值使用JSON的格式配置
	 */
	protected String returnValue;

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public void check(){
		super.check();
		if(returnValue == null){
			throw LocalMockErrorCode.ILLEGAL_PARAM.exp("returnValue不能为空");
		}
	}
}
