package com.kvn.mock.local.domain;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kvn.mock.local.processor.MockProcessor;

/**
 * 使用在注解上，用于指定MockProcessor
* @author wzy
* @date 2017年6月20日 下午2:56:19
*/
@Documented
@Target({ TYPE })
@Retention(RUNTIME)
@Inherited // 注解继承
public @interface Constraint {
	@SuppressWarnings("rawtypes")
	Class<? extends MockProcessor> processBy();
}
