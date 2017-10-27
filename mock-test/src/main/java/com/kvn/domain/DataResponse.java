package com.kvn.domain;
/**
* @author wzy
* @date 2017年10月27日 下午4:18:01
*/
public class DataResponse<T> {
	 //业务流水号
    private String busiNo;
    //返回代码
    private int retCode;
    //返回消息
    private String retMsg;
    
    private T data;

	public String getBusiNo() {
		return busiNo;
	}

	public void setBusiNo(String busiNo) {
		this.busiNo = busiNo;
	}

	public int getRetCode() {
		return retCode;
	}

	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
