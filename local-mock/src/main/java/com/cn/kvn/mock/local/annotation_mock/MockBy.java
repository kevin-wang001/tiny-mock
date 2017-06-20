package com.cn.kvn.mock.local.annotation_mock;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cn.kvn.mock.local.processor.MockByProcessor;

/**
 * 含有@MockBy注解的方法，将使用useClass中的useMethod方法的返回值来返回 
 * @author Created by wzy on 2017/6/1.
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Constraint(processBy = MockByProcessor.class)
public @interface MockBy {
	/**
	 * useClass : 用于mock的class
	 */
	Class<?> useClass();

	/**
	 * <pre>
	 * 用于mock的method名字。
	 * Note:
	 * 1. 这个method必需在useClass中定义过。通过这个class去spring容器中获取这个bean
	 * 2. 暂时只支持无参的方法
	 * </pre>
	 */
	String useMethod();

	/**
	 * 是否将真实方法的参数往mock方法中传递，默认false
	 * 
	 * @return
	 */
	boolean passParameter() default false;
}
