package com.kvn.mock.local.exception;

import com.kvn.mock.exception.TinyMockException;

/**
* @author wzy
* @date 2017年6月20日 上午11:59:32
*/
public class LocalMockException extends TinyMockException {
	private static final long serialVersionUID = 1L;

	public LocalMockException(int code, String message) {
		super(code, message);
	}

}
