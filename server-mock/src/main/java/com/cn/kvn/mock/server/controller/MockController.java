package com.cn.kvn.mock.server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.server.framework.param_resolve.FastJson;
import com.kvn.domain.Foo;

/**
* @author wzy
* @date 2017年6月21日 下午4:36:03
*/
@RestController
@RequestMapping("mock")
public class MockController {
	
	@RequestMapping("com-kvn-service-ServiceA/method_15")
	public String mockMethod(){
		System.out.println("------执行服务端mock方法---------");
		return "mock return from mock server";
	}
	
	@RequestMapping("com-kvn-service-ServiceA/method_16")
	public String mockMethod_16(){
		System.out.println("------执行服务端mock方法:mockMethod_16---------");
		return "mock return from mock server: finish executing mockMethod_16";
	}
	
	
	@RequestMapping("com-kvn-service-ServiceA/method_18")
	public void mockMethod_18(@RequestParam String msg){
		System.out.println("------执行服务端mock方法:method_18---------msg:" + msg);
	}
	
	@RequestMapping("com-kvn-service-ServiceA/method_19")
	public String mockMethod_19(@FastJson Foo foo){
		System.out.println("------执行服务端mock方法:method_18---------foo:" + JSON.toJSONString(foo));
		return "hello client!";
	}
	
	@RequestMapping("com-kvn-service-ServiceA/method_20")
	public String mockMethod_20(String msg, @FastJson Foo foo){
		System.out.println("------执行服务端mock方法:method_20---------msg:" + msg + "---foo:" + JSON.toJSONString(foo));
		return "hello client!";
	}
	
	
	@RequestMapping("com-kvn-service-ServiceA/method_21")
	public List<Foo> mockMethod_21(String msg, @FastJson List<Foo> ls){
		System.out.println("------执行服务端mock方法:method_21---------msg:" + msg + "---ls:" + JSON.toJSONString(ls));
		Foo foo = new Foo();
		foo.setId(3);
		foo.setName("name3FromServer");
		foo.setNo("noFromServer");
		ls.add(foo);
		return ls;
	}

}
