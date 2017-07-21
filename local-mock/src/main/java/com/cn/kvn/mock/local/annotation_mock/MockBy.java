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
	 * mock.com.kvn.service.MockServiceA#mockMethod_4()
	 * mock.com.kvn.service.MockServiceA#mockMethod_11(java.lang.String,com.kvn.domain.Foo)
	 * 
	 * <b>默认值：</b>
	 * 默认使用的mock类路径为[mock类全路径 = "mock." + 真实类的包名 + ".Mock" + 真实类的类名]，
	 * 默认使用的mock方法名与参数与原方法保持一致
	 * </pre>
	 * @return
	 */
	String delegateMethodFullPath() default MOCKBY_DEFALUT_PATH;
	
	String MOCKBY_DEFALUT_PATH = "DEFAULT_PATH";
	
}
