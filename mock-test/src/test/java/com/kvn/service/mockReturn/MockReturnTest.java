package com.kvn.service.mockReturn;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.kvn.SpringBaseTest;
import com.kvn.service.ServiceA;
import com.kvn.service.ServiceB;

/**
* @author wzy
* @date 2017年6月22日 上午10:14:09
*/
public class MockReturnTest extends SpringBaseTest {
	@Resource
	ServiceA serviceA;
	@Resource
	ServiceB serviceB;
	
	/***********************************@MockReturn************************************/
	@Test
	public void testMockReturnInt() {
		System.out.println("serviceA.method_3() ----> " + serviceA.method_3());
	}

	@Test
	public void testMockReturnInteger() {
		System.out.println("serviceA.method_7() ----> " + serviceA.method_7());
	}

	@Test
	public void testMockReturnComplexObject() {
		System.out.println("serviceA.method_6() ----> " + JSON.toJSONString(serviceA.method_6()));
	}

	@Test
	public void testMockReturnThrow() {
		System.out.println("serviceA.method_8() ----> " + JSON.toJSONString(serviceA.method_8()));
	}

	@Test
	public void testMockReturnRandom() {
		System.out.println("serviceA.method_9() ----> " + JSON.toJSONString(serviceA.method_9()));
	}

	@Test
	public void testMockReturnRandomExt() {
		for(int i=0; i<5; i++){
			System.out.println("serviceA.method_10() ----> " + JSON.toJSONString(serviceA.method_10()));
		}
	}
	
	
	/***********************************xml mock************************************/
	@Test
	public void testConfigMockReturnInt() {
		System.out.println("serviceA.method_12() ----> " + serviceA.method_12());
	}
	
	@Test
	public void testOverloadMethodMock() {
		System.out.println("serviceA.method_13() ----> " + serviceA.method_13());
		System.out.println("serviceA.method_13() ----> " + serviceA.method_13("xxx"));
	}
	
	
	
	/***********************************嵌套mock测试************************************/
	@Test
	public void testNestingMock(){
		serviceB.doSth();
	}
}
