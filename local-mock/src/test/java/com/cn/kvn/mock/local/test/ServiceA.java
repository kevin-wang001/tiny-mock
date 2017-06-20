package com.cn.kvn.mock.local.test;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.annotation_mock.MockBy;
import com.cn.kvn.mock.local.annotation_mock.MockReturn;

/**
 * @author wzy  2016年7月20日上午10:19:55
 */
@Service
public class ServiceA {
	
	
	/** ===============================@MockReturn============================================= */
	public String doA(String msg){
		return "A-" + msg + "[real method doA]";
	}

	@MockReturn("null")
	public void methodA_1(){
		System.out.println("do methodA_1---[real method methodA_1]");
	}

	@MockReturn("mock_123")
	public String method_2() {
		System.out.println("执行真实方法：method_2");
		return "do method_2------[real method methodA_2]";
	}

	@MockReturn("123")
	public int method_3() {
		System.out.println("执行真实方法：method_3");
		return 333;
	}

	@MockReturn("123")
	public Integer method_7() {
		System.out.println("执行真实方法：method_3");
		return 333;
	}

	@MockReturn("{\"id\":2,\"name\":\"Mock Name\",\"no\":\"Mock No\"}")
	public Foo method_6() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("真实Name");
		foo.setNo("真实No");
		System.out.println("执行真实方法：method_6");
		return foo;
	}

	@MockReturn(MockReturn.THROW)
	public Foo method_8() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("真实Name");
		foo.setNo("真实No");
		System.out.println("执行真实方法：method_8");
		return foo;
	}

	@MockReturn(MockReturn.RANDOM)
	public Foo method_9() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("真实Name");
		foo.setNo("真实No");
		System.out.println("执行真实方法：method_9");
		return foo;
	}

	@MockReturn(MockReturn.RANDOM_EXCEPTION)
	public Foo method_10() {
		Foo foo = new Foo();
		foo.setId(1);
		foo.setName("真实Name");
		foo.setNo("真实No");
		System.out.println("执行真实方法：method_10");
		return foo;
	}
	

	/** ===============================xml配置类型的MockReturnItem============================================= */
	public int method_12(){
		System.out.println("执行真实方法：method_12");
		return 1;
	}
	
	/**
	 * 验证对重载的支持
	 * @return
	 */
	public int method_13(){
		System.out.println("执行真实方法：method_13");
		return 1;
	}
	public int method_13(String str){
		System.out.println("执行真实方法：method_13");
		return 1;
	}



	/** ===============================@MockBy============================================= */
	@MockBy(useClass = MockServiceA.class, useMethod = "mockMethod_4")
	public String method_4() {
		System.out.println("执行真实方法：method_4");
		return "do method_4------[real method methodA_4]";
	}

	@MockBy(useClass = MockServiceA.class, useMethod = "mockMethod_5")
	public String method_5(String msg) {
		System.out.println("执行真实方法：method_5 -->msg:" + msg);
		return "do method_5------[real method method_5]";
	}
	
	@MockBy(useClass = MockServiceA.class, useMethod = "mockMethod_11", passParameter = true)
	public String method_11(String msg, Foo foo) {
		System.out.println("执行真实方法：method_11 -->msg:" + msg);
		return "do method_11------[real method method_11]";
	}
	
	
	/** ===============================xml配置类型的MockByItem============================================= */
	public void method_14(String msg, Foo foo){
		System.out.println("执行真实方法：method_14 -->msg:" + msg);
	}
	
	
	
	
	public static void main(String[] args) {
		Foo foo = new Foo();
		foo.setId(2);
		foo.setName("Mock Name");
		foo.setNo("Mock No");
		System.out.println(JSON.toJSONString(foo));
	}

}
