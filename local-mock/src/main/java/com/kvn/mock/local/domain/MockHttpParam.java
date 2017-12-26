package com.kvn.mock.local.domain;
/**
* @author wzy
* @date 2017年6月21日 下午12:03:44
*/
public class MockHttpParam {

	/**
	 * 参数名称：默认取方法上的方法名
	 */
	private String paramName;
	
	/**
	 * 参数值
	 */
	private String paramValue;
	
	/**
	 * 参数的class类型
	 */
	private Class<?> paramClass;


	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Class<?> getParamClass() {
		return paramClass;
	}

	public void setParamClass(Class<?> paramClass) {
		this.paramClass = paramClass;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

}
