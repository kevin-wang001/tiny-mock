package com.kvn.mock.local.remote.http_client;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kvn.mock.common.Log;
import com.kvn.mock.local.domain.MockByHttpItem;
import com.kvn.mock.local.domain.MockHttpParam;
import com.kvn.mock.local.exception.LocalMockErrorCode;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

/**
* @author wzy
* @date 2017年6月21日 下午3:10:43
*/
public class HttpClient {
	private static final Logger logger = LoggerFactory.getLogger(HttpClient.class);
	public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
	
	public static class SingletonHolder {
		public static OkHttpClient INSTANCE = new OkHttpClient();
	}

	public static Object post(MockByHttpItem mockItem) throws Exception{
		Builder requestBuilder = new Request.Builder().url(mockItem.getServerPath());
		if(!CollectionUtils.isEmpty(mockItem.getParamList())){
			FormBody.Builder builder = new FormBody.Builder();
			for(MockHttpParam param : mockItem.getParamList()){
				builder.add(param.getParamName(), param.getParamValue());
			}
			requestBuilder.post(builder.build());
		}
		Request request = requestBuilder.build();
		Response response = HttpClient.SingletonHolder.INSTANCE.newCall(request).execute();
		if(!response.isSuccessful()){
			logger.error("调用[{}]返回状态异常:response={}", mockItem.getServerPath(), response.toString());
			throw LocalMockErrorCode.HTTP_POST_ERROR.exp();
		}
		
		Class<?> returnType = mockItem.getMockedMethod().getReturnType();
		if(Void.TYPE == returnType){
			return null;
		}
		
		String responseBody = new String(response.body().bytes(), "UTF-8"); // 约定是JSON返回
		logger.info(Log.op("okHttp post").msg("remote server 返回值[{0}]", responseBody).toString());
		if(List.class == returnType){// 兼容对List对象的转型
			Type pa = ((ParameterizedType) mockItem.getMockedMethod().getGenericReturnType()).getActualTypeArguments()[0];
			return JSON.parseArray(responseBody, (Class<?>) pa);
		}
		
		return JSONObject.parseObject(responseBody, returnType);
	}
}
