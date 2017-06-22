package com.cn.kvn.mock.local.test.mockByHttp;

import javax.annotation.Resource;

import org.junit.Test;

import com.cn.kvn.mock.local.SpringBaseTest;
import com.cn.kvn.mock.local.test.ServiceA;

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
		serviceA.method_15();
	}
	
	
	
	/***********************************xml config************************************/
	@Test
	public void testMockByHttpXmlConfigReturnString() {
		serviceA.method_16();
	}
	
}
