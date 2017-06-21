package com.cn.kvn.mock.local.domain;

import com.cn.kvn.mock.local.processor.MockByHttpProcessor;

/**
 * 单个mock配置项，配置时，必须 scope = prototype，各个mock配置互不影响
 * 
 * @author wzy
 * @date 2017年6月19日 上午11:32:23
 */
@Constraint(processBy = MockByHttpProcessor.class)
public class MockByHttpItem extends MockItem {
	
}
