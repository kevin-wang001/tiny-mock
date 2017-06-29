package com.cn.kvn.mock.local.annotation_mock;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 含有@MockBy注解的方法，将使用mockMethodFullPath指定的方法的返回值来返回 
 * @author Created by wzy on 2017/6/1.
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface MockBy {
	
	/**
	 * mock方法的全路径。通过它来定位到mock的类和方法
	 * <pre>
	 * 形如：  
	 * com.cn.kvn.mock.local.test.MockServiceA#mockMethod_4()
	 * com.cn.kvn.mock.local.test.MockServiceA#mockMethod_11(java.lang.String,com.cn.kvn.mock.local.test.Foo)
	 * </pre>
	 * @return
	 */
	String delegateMethodFullPath();
	
}
