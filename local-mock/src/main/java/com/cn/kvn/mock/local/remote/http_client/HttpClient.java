package com.cn.kvn.mock.local.remote.http_client;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.kvn.mock.local.domain.MockByHttpItem;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
* @author wzy
* @date 2017年6月21日 下午3:10:43
*/
public class HttpClient {
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
		String jsonParam = JSON.toJSONString(mockItem.getParamList());
		Builder builder = new Request.Builder().url(mockItem.getServerPath());
		if(StringUtils.isNotBlank(jsonParam) && !"null".equals(jsonParam.toLowerCase())){
			RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonParam);
			builder.post(body);
		}
		Request request = builder.build();
		Response response = HttpClient.SingletonHolder.INSTANCE.newCall(request).execute();
		String responseBody = response.body().toString(); // 约定是JSON返回
		return JSONObject.parseObject(responseBody, mockItem.getMockedMethod().getReturnType());
	}
}
