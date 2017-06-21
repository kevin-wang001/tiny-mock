package com.cn.kvn.mock.local.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.processor.MockByHttpProcessor;
import com.google.common.collect.Lists;

/**
 * 单个mock配置项，配置时，必须 scope = prototype，各个mock配置互不影响
 * 
 * @author wzy
 * @date 2017年6月19日 上午11:32:23
 */
@Constraint(processBy = MockByHttpProcessor.class)
public class MockByHttpItem extends MockItem {
	/**
	 * mock 服务的 http url
	 */
	private String serverPath;
	
	/**
	 * mock 服务的参数。不序列化
	 */
	private transient Object[] args;
	
	/**
	 * mock 服务的 http url 参数集
	 */
	private List<MockHttpParam> paramList;
	
	public void buildParamList(){
		if(ArrayUtils.isEmpty(args)){
			return;
		}
		
		paramList = Lists.newArrayList();
		for(Object arg : args){
			MockHttpParam param = new MockHttpParam();
			param.setParamClass(arg.getClass());
			param.setParam(JSON.toJSONString(arg)); // 使用JSON的方式序列化
			paramList.add(param);
		}
	}
	

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public List<MockHttpParam> getParamList() {
		return paramList;
	}

	public void setParamList(List<MockHttpParam> paramList) {
		this.paramList = paramList;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}
