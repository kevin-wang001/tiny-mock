package com.cn.kvn.mock.local.domain;
/**
* @author wzy
* @date 2017年6月21日 下午12:03:44
*/
public class MockHttpParam {
	/**
	 * mock 服务的http url和参数
	 */
	private String serverPath;
	
	/**
	 * 参数值
	 */
	private String param;
	
	/**
	 * 参数的class类型
	 */
	private Class<?> paramClass;

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Class<?> getParamClass() {
		return paramClass;
	}

	public void setParamClass(Class<?> paramClass) {
		this.paramClass = paramClass;
	}

}
