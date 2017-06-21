package com.cn.kvn.mock.server.framework;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;

/**
 * Created by wzy on 2016/9/12.
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {
	
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(FastJson.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // http://www.cnblogs.com/daxin/p/3296493.html#top

        String queryString = request.getParameterNames().nextElement().toString();

        // 利用fastjson转换为对应的类型
        if("java.util.List".equals(parameter.getParameterType().getName())){ // 兼容对List对象的转型
        	Type pa = ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments()[0];
        	return JSON.parseArray(queryString, ReflectionUtil.getClass(pa));
        }
        

        return JSON.parseObject(queryString, parameter.getParameterType());
    }
    
}
