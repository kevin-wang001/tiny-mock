package com.cn.kvn.mock.local.domain;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.processor.MockByHttpProcessor;
import com.cn.kvn.mock.tool.AsmUtils;
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
		
		// asm 获取参数名称
		String[] paramNames = AsmUtils.getMethodParameterNamesByAsm4(this.mockedClass, this.mockedMethod);
		paramList = Lists.newArrayList();
		for(int i=0; i<args.length; i++){
			MockHttpParam param = new MockHttpParam();
			param.setParamClass(args[i].getClass());
			param.setParamName(paramNames[i]);
			param.setParamValue(JSON.toJSONString(args[i])); // 使用JSON的方式序列化
			paramList.add(param);
		}
	}
	
	/**
	 * 构造spring mvc支持的json格式
	 * @return
	 */
	public String generateJsonParams(){
		if(CollectionUtils.isEmpty(paramList)){
			return null;
		}
		
		StringBuffer json = new StringBuffer("{");
		for(MockHttpParam param : paramList){
			json.append("\"").append(param.getParamName()).append("\":").append(param.getParamValue()).append(",");
		}
		json.deleteCharAt(json.length() - 1);
		json.append("}");
		return json.toString();
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
