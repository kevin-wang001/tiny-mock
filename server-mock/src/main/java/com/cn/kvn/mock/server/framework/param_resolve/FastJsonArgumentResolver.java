package com.cn.kvn.mock.server.framework.param_resolve;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.log.Log;
import com.cn.kvn.mock.tool.ReflectionUtil;
import com.google.common.base.Strings;

/**
 * Created by wzy on 2016/9/12.
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(FastJson.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		String paramJsonValue = webRequest.getParameter(parameter.getParameterName());
		
		if(Strings.isNullOrEmpty(paramJsonValue)){
			logger.warn(Log.op("resolveArgument").msg("警告：根据参数名[{0}]找到的参数值为空。【请检查Client端的参数名与Server端的参数名称是否相同】", parameter.getParameterName()).toString());
		}
		
		Class<?> parameterType = parameter.getParameterType();
		if(List.class == parameterType){// 兼容对List对象的转型
			Type pa = ((ParameterizedType) parameter.getGenericParameterType()).getActualTypeArguments()[0];
			return JSON.parseArray(paramJsonValue, ReflectionUtil.getClass(pa));
		}
		
		return JSON.parseObject(paramJsonValue, parameterType);

	}

}
