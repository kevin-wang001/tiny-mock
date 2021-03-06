package com.kvn.service.mockBy;

import javax.annotation.Resource;

import org.junit.Test;

import com.kvn.SpringBaseTest;
import com.kvn.domain.Foo;
import com.kvn.service.ServiceA;
import com.kvn.service.ServiceB;

/**
* @author wzy
* @date 2017年6月22日 上午10:10:56
*/
public class MockByTest extends SpringBaseTest {
	@Resource
	ServiceA serviceA;
	@Resource
	ServiceB serviceB;
	
	/***********************************@MockBy************************************/
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
	
	
	/***********************************xml config************************************/
	@Test
	public void testConfigMockByMethodMock() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("MockName");
		foo.setNo("this is mock no");
		serviceA.method_14("xxx", foo);
	}
	
	@Test
	public void testDefaultConfigByXml(){
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("MockName");
		foo.setNo("this is mock no");
		serviceA.method_22("xxx", foo);
	}
	
	@Test
	public void testDefaultConfigByAnno(){
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("MockName");
		foo.setNo("this is mock no");
		serviceA.method_23("xxx", foo);
	}
	
}
