package com.cn.kvn.mock.local.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * @author wzy  2016年7月20日上午10:19:55
 */
@Service
public class ServiceB {
	@Resource
	private ServiceA serviceA;
	public String doB(String msg){
		String doA = serviceA.doA("aaaa");
		return "B:" + msg + "###【B-A.doA】:" + doA;
	}
	
	public void doSth(){
		System.out.println(serviceA.doA("xxx"));
		serviceA.method_5("555555");
	}
	
}
