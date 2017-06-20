package com.cn.kvn.mock.local.test.annotation;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.test.Foo;
import com.cn.kvn.mock.local.test.ServiceA;
import com.cn.kvn.mock.local.test.ServiceB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/application.xml")
public class MockAnnotationTest {
	@Resource
	ServiceA serviceA;
	@Resource
	ServiceB serviceB;

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


	@Test
	public void testMockBy() {
		for(int i=0; i<3; i++){
			System.out.println("serviceA.method_4() ----> " + serviceA.method_4());
		}
	}
	@Test
	public void testMockByWithPassParameter() {
		for(int i=0; i<3; i++){			
			Foo foo = new Foo();
			foo.setId(12);
			foo.setName("foo");
			foo.setNo("123");
			System.out.println("serviceA.method_11() ----> " + serviceA.method_11("xxxx", foo));
		}
	}

	@Test
	public void testNestingMock(){
		serviceB.doSth();
	}

}
