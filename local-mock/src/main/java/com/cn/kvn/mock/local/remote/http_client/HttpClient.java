package com.cn.kvn.mock.local.remote.http_client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.kvn.mock.local.domain.MockByHttpItem;
import com.cn.kvn.mock.local.domain.MockHttpParam;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
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

	public static String post(String url, String jsonParam) throws IOException{
		RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonParam);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = HttpClient.SingletonHolder.INSTANCE.newCall(request).execute();
		return response.body().string();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T post(MockByHttpItem mockItem, Class<T> returnClass) throws IOException{
		String jsonParam = JSON.toJSONString(mockItem.getParamList());
		RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonParam);
		Request request = new Request.Builder().url(mockItem.getServerPath()).post(body).build();
		Response response = HttpClient.SingletonHolder.INSTANCE.newCall(request).execute();
		String responseBody = response.body().toString(); // 约定是JSON返回
		return (T) JSONObject.parseObject(responseBody, mockItem.getMockedMethod().getReturnType());
	}
	
	public static Object post(MockByHttpItem mockItem) throws IOException{
		Builder requestBuilder = new Request.Builder().url(mockItem.getServerPath());
		if(!CollectionUtils.isEmpty(mockItem.getParamList())){
			FormBody.Builder builder = new FormBody.Builder();
			for(MockHttpParam param : mockItem.getParamList()){
				builder.add(param.getParamName(), param.getParam());
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
		if(returnType == Void.TYPE){
			return null;
		}
		
		String responseBody = new String(response.body().bytes(), "UTF-8"); // 约定是JSON返回
		return JSONObject.parseObject(responseBody, returnType);
	}
}
