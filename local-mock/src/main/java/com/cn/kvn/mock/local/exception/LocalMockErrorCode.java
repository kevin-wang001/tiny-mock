package com.cn.kvn.mock.local.exception;

import java.text.MessageFormat;

import com.cn.kvn.mock.exception.IErrors;
import com.cn.kvn.mock.exception.TinyMockException;

/**
 * 异常码区段：[1001-1999]
* @author wzy
* @date 2017年6月20日 上午11:59:32
*/
public enum LocalMockErrorCode implements IErrors {
	METHOD_NOT_MATCH(1001, "{0}中找不到方法参数个数为[{1}]的方法[{2}]"),
	METHOD_NOT_EXIST(1002, "{0}中不存在方法[{1}]"),
	MOCKBY_PROCESSOR_NOT_FIND(1003, "没有[@MockBy]对应的Processor"),
	MOCKRETURN_PROCESSOR_NOT_FIND(1004, "没有[@MockReturn]对应的Processor"),
	MOCKRITEM_NOT_SUPPORT(1005, "不支持的MockItem类型 : {0}"),
	MISSING_CONSTRAINT(1006, "MockItem[{0}]没有设置@Constraint"),
	HTTP_POST_ERROR(1007, "发送post请求异常"),
	/**-----------------COMMON ERROR--------------------*/
	ILLEGAL_PARAM(1999, "参数异常:%s");
	
	
	private int code;
	private String msg;
	
	private LocalMockErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	@Override
	public TinyMockException exp() {
		return new TinyMockException(code, msg);
	}

	@Override
	public TinyMockException exp(Object... args) {
		return new TinyMockException(code, MessageFormat.format(msg, args));
	}

	@Override
	public TinyMockException exp(Throwable cause, Object... args) {
		return new TinyMockException(code, MessageFormat.format(msg, args), cause);
	}

	@Override
	public TinyMockException expMsg(String message, Object... args) {
		return new TinyMockException(code, MessageFormat.format(message, args));
	}

	@Override
	public TinyMockException expMsg(String message, Throwable cause, Object... args) {
		return new TinyMockException(code, MessageFormat.format(message, args), cause);
	}
	
}
