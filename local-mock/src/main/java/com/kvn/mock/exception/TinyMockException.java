package com.kvn.mock.exception;
/**
* @author wzy
* @date 2017年6月20日 上午11:53:59
*/
public class TinyMockException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    /**
     * 异常错误码
     */
    protected int code;

	public TinyMockException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	public TinyMockException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
}
