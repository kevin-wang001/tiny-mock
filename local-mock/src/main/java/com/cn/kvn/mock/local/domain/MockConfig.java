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

import com.alibaba.fastjson.JSON;
import com.cn.kvn.mock.local.MockAspect;
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

	private List<MockReturnItem> mockReturnItemList;
	private List<MockByItem> mockByItemList;
	
	public void setMockReturnItemList(List<MockReturnItem> mockReturnItemList) {
		this.mockReturnItemList = mockReturnItemList;
	}
	public void setMockByItemList(List<MockByItem> mockByItemList) {
		this.mockByItemList = mockByItemList;
	}
	
	/**
	 * 将mock 的 List 配置，转成map；并清空 List
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// 1. 加载配置文件中的mock配置
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
					input.check();
					return input.getMockKey();
				}
			});
			
			MOCKBY_CONFIG_MAP.putAll(configMap);
		}
		
		
		// 2. 加载注解式的mock配置
//		Map<String, Object> mockReturnAnnotationBeans = applicationContext.getBeansWithAnnotation(MockReturn.class);
//		Map<String, Object> mockByAnnotationBeans = applicationContext.getBeansWithAnnotation(MockBy.class);
//		Map<String, MockReturnItem> mockReturnAnnotationMap = Maps.transformValues(mockReturnAnnotationBeans, new Function<Object, MockReturnItem>() {
//			@Override
//			public MockReturnItem apply(Object input) {
//				MockReturn mr = input.getClass().getAnnotation(MockReturn.class);
//				for(){
//					MockReturnItem mri = new MockReturnItem();
//					mri.setMockedClass(input.getClass());
//					mri.setMockedMethod(mockedMethod);
//				}
//				return null;
//			}
//		});
	}
	
	
	public static MockItem getMockItem(Method method){
		String key = method.getDeclaringClass().getName().concat("#").concat(method.getName()).concat(JSON.toJSONString(method.getParameterTypes()));
		if(MOCKRETURN_CONFIG_MAP.containsKey(key)){
			return MOCKRETURN_CONFIG_MAP.get(key);
		}
		
		if(MOCKBY_CONFIG_MAP.containsKey(key)){
			return MOCKBY_CONFIG_MAP.get(key);
		}
		
		logger.debug("方法[".concat(key).concat("]没有找到相应的mock配置"));
		return null;
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
		}
		
		throw new RuntimeException("putIfAbsent()失败，mockItem：" + mockItem.getClass().getName());
	}
	
}
