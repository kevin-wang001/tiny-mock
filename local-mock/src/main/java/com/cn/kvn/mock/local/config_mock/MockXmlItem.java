package com.cn.kvn.mock.local.config_mock;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.util.CollectionUtils;

import com.cn.kvn.mock.local.domain.MockItem;
import com.cn.kvn.mock.local.exception.LocalMockErrorCode;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
* @author wzy
* @date 2017年6月22日 上午9:28:03
*/
public class MockXmlItem {
	private MockItem mockItem;
	
	/** 被mock的class的method，即真实的method */
	private String mockedMethodName;

	/**
	 * {@link #mockedMethodName}方法的参数个数<br/>
	 * 类中的方法被重载的时候，需要用到这个参数去定位到底是类
	 * {@link com.cn.kvn.mock.local.domain.MockItem#getMockedClass()}
	 * 的哪一个方法
	 */
	private Integer mockedMethodParameterCount;

	public MockXmlItem(MockItem mockItem) {
		super();
		this.mockItem = mockItem;
	}

	/**
	 * 设置 mockedMethod的值
	 */
	public void initMockedMethod() {
		Method[] methods = this.mockItem.getMockedClass().getMethods();
		Multimap<String, Method> methodMap = Multimaps.index(Lists.newArrayList(methods), new Function<Method, String>() {
			@Override
			public String apply(Method input) {
				return input.getName();
			}
		});

		Collection<Method> coll = methodMap.get(mockedMethodName);

		// 找不到方法
		if (CollectionUtils.isEmpty(coll)) {
			throw LocalMockErrorCode.METHOD_NOT_EXIST.exp(this.mockItem.getMockedClass().getName(), mockedMethodName);
		}

		// 方法存在重载
		if (coll.size() >= 2) {
			if (mockedMethodParameterCount == null) {
				throw LocalMockErrorCode.ILLEGAL_PARAM.exp(this.mockItem.getMockedClass().getName().concat("方法[").concat(mockedMethodName).concat("]存在重载，需要在xml中设置parameterCount参数才能定位"));
			}
			for (Method m : coll) {
				if (m.getParameterTypes().length == mockedMethodParameterCount) {
					this.mockItem.setMockedMethod(m);
					return;
				}
			}

			throw LocalMockErrorCode.METHOD_NOT_MATCH.exp(this.mockItem.getMockedClass().getName(), mockedMethodParameterCount, mockedMethodName);
		}

		this.mockItem.setMockedMethod(coll.iterator().next()); // 只匹配到一个，直接赋值
	}

	public void setMockedMethodParameterCount(Integer mockedMethodParameterCount) {
		this.mockedMethodParameterCount = mockedMethodParameterCount;
	}

	public String getMockedMethodName() {
		return mockedMethodName;
	}

	public void setMockedMethodName(String mockedMethodName) {
		this.mockedMethodName = mockedMethodName;
	}

	public MockItem getMockItem() {
		return mockItem;
	}

	public void setMockItem(MockItem mockItem) {
		this.mockItem = mockItem;
	}
	
}