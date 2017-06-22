package com.cn.kvn.mock.local.test.mockByHttp;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.SpringBaseTest;
import com.cn.kvn.mock.local.test.Foo;
import com.cn.kvn.mock.local.test.ServiceA;
import com.google.common.collect.Lists;

/**
* @author wzy
* @date 2017年6月21日 下午3:44:47
*/
public class MockByHttpTest extends SpringBaseTest {
	@Resource
	ServiceA serviceA;
	
	/***********************************@MockByHttp************************************/
	@Test
	public void testMockByHttpReturnString() {
		System.out.println(serviceA.method_15());
	}
	
	@Test
	public void testMockByHttpReturnVoid() {
		serviceA.method_18("hello!");
	}
	@Test
	public void testMockByHttpWithObjectParam() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("client");
		foo.setNo("clientNo");
		String rlt = serviceA.method_19(foo);
		System.out.println(rlt);
	}
	@Test
	public void testMockByHttpWith2Param() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("client");
		foo.setNo("clientNo");
		String rlt = serviceA.method_20("hello!", foo);
		System.out.println(rlt);
	}
	
	@Test
	public void testMockByHttpWithListParam() {
		Foo foo1 = new Foo();
		foo1.setId(1);
		foo1.setName("client1");
		foo1.setNo("clientNo1");
		Foo foo2 = new Foo();
		foo2.setId(2);
		foo2.setName("client2");
		foo2.setNo("clientNo2");
		List<Foo> list = Lists.newArrayList(foo1, foo2);
		List<Foo> rlt = serviceA.method_21("hello!", list);
		System.out.println(JSON.toJSONString(rlt));
	}
	
	
	
	
	/***********************************xml config************************************/
	@Test
	public void testMockByHttpXmlConfigReturnString() {
		serviceA.method_16();
	}
	
}
