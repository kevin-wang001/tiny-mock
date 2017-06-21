package com.cn.kvn.mock.local.annotation_mock;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 使用http接口来mock，实现mock服务，解耦mock
 * @author Created by wzy on 2017/6/20.
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface MockByHttp {
	/**
	 * http url。默认使用mock/class/method?params=xxx
	 * @return
	 */
	String serverPath() default "";
}
