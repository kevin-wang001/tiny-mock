package com.cn.kvn.mock.local.test.config;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.kvn.mock.local.test.Foo;
import com.cn.kvn.mock.local.test.ServiceA;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application.xml")
public class ConfigMockTest {
	@Resource
	ServiceA serviceA;
	
	@Test
	public void testConfigMockReturnInt() {
		System.out.println("serviceA.method_3() ----> " + serviceA.method_12());
	}
	
	@Test
	public void testOverloadMethodMock() {
		System.out.println("serviceA.method_3() ----> " + serviceA.method_13());
		System.out.println("serviceA.method_3() ----> " + serviceA.method_13("xxx"));
	}
	
	@Test
	public void testConfigMockByMethodMock() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("MockName");
		foo.setNo("this is mock no");
		serviceA.method_14("xxx", foo);
	}
	
}
