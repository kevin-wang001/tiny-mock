package com.cn.kvn.mock.local.domain;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.annotation_mock.MockBy;
import com.cn.kvn.mock.local.annotation_mock.MockByHttp;
import com.cn.kvn.mock.local.annotation_mock.MockReturn;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

/**
 * mock配置汇总
* @author wzy
* @date 2017年6月19日 上午11:26:38
*/
public class MockConfig implements InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(MockConfig.class);
	
	@Resource
	private ApplicationContext applicationContext;
	// <KEY: className#methodName#arguments, VALUE: MockReturnItem>
	public static  Map<String, MockReturnItem> MOCKRETURN_CONFIG_MAP = new ConcurrentHashMap<>();
	public static  Map<String, MockByItem> MOCKBY_CONFIG_MAP = new ConcurrentHashMap<>();
	public static  Map<String, MockByHttpItem> MOCKBYHTTP_CONFIG_MAP = new ConcurrentHashMap<>();

	private List<MockReturnItem> mockReturnItemList;
	private List<MockByItem> mockByItemList;
	private List<MockByHttpItem> mockByHttpItemList;
	private static String mockServerAddress;
	
	public void setMockReturnItemList(List<MockReturnItem> mockReturnItemList) {
		this.mockReturnItemList = mockReturnItemList;
	}
	public void setMockByItemList(List<MockByItem> mockByItemList) {
		this.mockByItemList = mockByItemList;
	}
	public void setMockByHttpItemList(List<MockByHttpItem> mockByHttpItemList) {
		this.mockByHttpItemList = mockByHttpItemList;
	}
	@SuppressWarnings("static-access")
	public void setMockServerAddress(String mockServerAddress) {
		this.mockServerAddress = mockServerAddress;
	}
	/**
	 * 将mock 的 List 配置，转成map；（并清空 List ？）
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// 加载配置文件中的mock配置
		if(!CollectionUtils.isEmpty(mockReturnItemList)){
			Map<String, MockReturnItem> configMap = Maps.uniqueIndex(mockReturnItemList, new Function<MockReturnItem, String>() {
				@Override
				public String apply(MockReturnItem input) {
					// check 配置
					input.check();
					return input.getMockKey();
				}
			});
			
			MOCKRETURN_CONFIG_MAP.putAll(configMap);
		}
		
		if(!CollectionUtils.isEmpty(mockByItemList)){
			Map<String, MockByItem> configMap = Maps.uniqueIndex(mockByItemList, new Function<MockByItem, String>() {
				@Override
				public String apply(MockByItem input) {
					// check 配置
					input.check();
					return input.getMockKey();
				}
			});
			
			MOCKBY_CONFIG_MAP.putAll(configMap);
		}
		
		if(!CollectionUtils.isEmpty(mockByHttpItemList)){
			Map<String, MockByHttpItem> configMap = Maps.uniqueIndex(mockByHttpItemList, new Function<MockByHttpItem, String>() {
				@Override
				public String apply(MockByHttpItem input) {
					// check 配置
					input.check();
					return input.getMockKey();
				}
			});
			
			MOCKBYHTTP_CONFIG_MAP.putAll(configMap);
		}
	}
	
	
	/**
	 * 通过Method 获取 MockItem
	 * @param method
	 * @return
	 */
	public static MockItem getMockItem(Method method){
		String key = method.getDeclaringClass().getName().concat("#").concat(method.getName()).concat(JSON.toJSONString(method.getParameterTypes()));
		if(MOCKRETURN_CONFIG_MAP.containsKey(key)){
			return MOCKRETURN_CONFIG_MAP.get(key);
		}
		
		if(MOCKBY_CONFIG_MAP.containsKey(key)){
			return MOCKBY_CONFIG_MAP.get(key);
		}
		
		if(MOCKBYHTTP_CONFIG_MAP.containsKey(key)){
			return MOCKBYHTTP_CONFIG_MAP.get(key);
		}
		
		logger.debug("方法[".concat(key).concat("]没有找到相应的mock配置"));
		return null;
	}
	
	/**
	 * 根据 method 获取 MockItem，并将 MockItem 设置到相应的 Config_Map 中。<br/>
	 * @return 返回MockItem。如果 method不需要Mock（即没有配置mock），则返回null
	 */
	public static MockItem getAndSetMockItem(Method method){
		MockItem mi = getMockItem(method);
		if(mi != null){
			return mi;
		}
		
		/**
		 * 注解类型的Mock，第一次从Config_Map中是取不到的。<br/>
		 * 对于注解类型的Mock，创建 MockItem，并添加到MockConfig中
		 */
		MockReturn mr = method.getAnnotation(MockReturn.class);
		if (mr != null) {
			MockReturnItem mri = new MockReturnItem();
			mri.setMockedClass(method.getDeclaringClass());
			mri.setMockedMethod(method);
			mri.setReturnValue(mr.value());
			// putIfAbsent : 加入mockConfig
			MockConfig.putIfAbsent(mri);
			mi = mri;
		}
		
		MockBy mb = method.getAnnotation(MockBy.class);
		if (mb != null) {
			MockByItem mbi = new MockByItem();
			mbi.setMockedClass(method.getDeclaringClass());
			mbi.setMockedMethod(method);
			mbi.setDelegateClass(mb.useClass());
			mbi.setDelegateMethodName(mb.useMethod());
			mbi.setPassParameter(mb.passParameter());
			// putIfAbsent : 加入mockConfig
			MockConfig.putIfAbsent(mbi);
			mi = mbi;
		}
		
		MockByHttp mbh = method.getAnnotation(MockByHttp.class);
		if(mbh != null){
			MockByHttpItem hbhi = new MockByHttpItem();
			hbhi.setMockedClass(method.getDeclaringClass());
			hbhi.setMockedMethod(method);
			String serverPath = mbh.serverPath();
			if(StringUtils.isEmpty(serverPath)){ // default serverPath
				serverPath = mockServerAddress + "/mock/" + method.getDeclaringClass().getName().replaceAll("\\.", "-") + "/" + method.getName();
			}
			hbhi.setServerPath(serverPath);
			hbhi.buildParamList();
			// putIfAbsent : 加入mockConfig
			MockConfig.putIfAbsent(hbhi);
			mi = hbhi;
		}
		
		return mi;
	}
	
	public static void putIfAbsent(MockItem mockItem){
		if(mockItem instanceof MockReturnItem){
			if(!MOCKRETURN_CONFIG_MAP.containsKey(mockItem.getMockKey())){
				MOCKRETURN_CONFIG_MAP.put(mockItem.getMockKey(), (MockReturnItem) mockItem);
			}
			return;
		} else if(mockItem instanceof MockByItem){
			if(!MOCKBY_CONFIG_MAP.containsKey(mockItem.getMockKey())){
				MOCKBY_CONFIG_MAP.put(mockItem.getMockKey(), (MockByItem) mockItem);
			}
			return;
		} else if(mockItem instanceof MockByHttpItem){
			if(!MOCKBYHTTP_CONFIG_MAP.containsKey(mockItem.getMockKey())){
				MOCKBYHTTP_CONFIG_MAP.put(mockItem.getMockKey(), (MockByHttpItem) mockItem);
			}
			return;
		}
		
		throw new RuntimeException("putIfAbsent()失败，mockItem：" + mockItem.getClass().getName());
	}
	
}
