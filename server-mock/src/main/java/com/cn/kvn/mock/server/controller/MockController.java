package com.cn.kvn.mock.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author wzy
* @date 2017年6月21日 下午4:36:03
*/
@RestController
@RequestMapping("mock")
public class MockController {
	
	@RequestMapping("com_cn_kvn_mock_local_test_ServiceA/method_15")
	public String mockMethod(){
		System.out.println("------执行服务端mock方法---------");
		return "mock return from mock server";
	}

}
