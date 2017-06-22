package com.cn.kvn.mock.local.remote.http_client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author wzy
 * @date 2017年6月22日 下午4:19:31
 */

public class JsonRequestParameter {
	public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

	private JSONObject jb = new JSONObject();

	/**
	 * 添加参数
	 * 
	 * @param key
	 * @param value
	 */
	public JsonRequestParameter addParameter(String key, String value) {
		try {
			jb.put(key, value);
		} catch (Exception e) {
		}
		return this;
	}

	public JsonRequestParameter addParameter(String key, int value) {
		try {
			jb.put(key, value);
		} catch (Exception e) {
		}
		return this;
	}

	public JsonRequestParameter addParameter(String key, String[] values) {
		try {
			JSONArray ja = new JSONArray();
			for (int i = 0; values != null && i < values.length; i++) {
				if (values[i] != null) {
					ja.add(values[i]);
				}
			}
			jb.put(key, ja);
		} catch (Exception e) {
		}
		return this;
	}

	/**
	 * 构建出json字符串
	 * 
	 * @return
	 */
	public RequestBody build() {
		String s = jb.toString();
		System.out.println("json请求体:" + s);
		jb = new JSONObject();
		RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, s);
		return requestBody;
	}
}
