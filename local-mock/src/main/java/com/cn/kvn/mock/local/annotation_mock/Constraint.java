package com.cn.kvn.mock.local.annotation_mock;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.cn.kvn.mock.local.processor.MockProcessor;

/**
 * 使用在注解上，用于指定MockProcessor
* @author wzy
* @date 2017年6月20日 下午2:56:19
*/
@Documented
@Target({ ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Constraint {
	@SuppressWarnings("rawtypes")
	Class<? extends MockProcessor> processBy();
}
