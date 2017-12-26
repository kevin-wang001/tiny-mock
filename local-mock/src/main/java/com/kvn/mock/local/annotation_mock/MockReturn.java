package com.kvn.mock.local.annotation_mock;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.kvn.mock.local.MockAspect;

/**
 * 含有@MockReturn的方法，将直接返回value
 * @author Created by wzy on 2017/6/1.
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface MockReturn {
    /**
     * mock的返回值。复杂对象使用JSON串作为返回值
     * @see MockAspect
     */
    String value();
    
    /**
     * mock 随机生成返回对象时，对String类型的变量，返回的随机串的长度
     * @return
     */
//    int randomStrLength() default 4;

    String THROW = "THROW()"; // 抛出一个异常
    String RANDOM = "RANDOM()"; // 返回一个随机对象
    String RANDOM_EXCEPTION = "RANDOM_EXCEPTION()"; // 返回随机对象或抛出一个异常
    
}
