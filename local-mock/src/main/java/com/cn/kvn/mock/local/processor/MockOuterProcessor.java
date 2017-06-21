package com.cn.kvn.mock.local.processor;

import com.cn.kvn.mock.local.domain.MockItem;

/**
 * 标记接口：标记用户自定义扩展的 MockProcessor
* @author wzy
* @date 2017年6月19日 下午1:59:27
*/
public interface MockOuterProcessor<T extends MockItem> extends MockProcessor<T> {
	
}
