package com.cn.kvn.mock.local.test;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @author wzy on 2017/6/1.
 */
@Service
public class MockServiceA {

    public String mockMethod_4() {
        System.out.println("执行mock方法：mockMethod_4");
        return "mockMethod_4返回值";
    }

    public String mockMethod_5(){
    	System.out.println("执行mock方法：mockMethod_5");
    	return "mockMethod_5返回值";
    }
    
    public String mockMethod_11(String msg, Foo foo){
        System.out.println("执行mock方法：mockMethod_11");
        System.out.println("传递的参数：[msg:" + msg + ", foo:" + JSON.toJSONString(foo) + "]");
        return "mockMethod_11返回值";
    }
    
    public String mockMethod_14(String msg, Foo foo){
        System.out.println("执行mock方法：mockMethod_14");
        System.out.println("传递的参数：[msg:" + msg + ", foo:" + JSON.toJSONString(foo) + "]");
        return "mockMethod_14返回值";
    }
    
    public String mockMethod_1(String msg, Foo foo){
        System.out.println("执行mock方法：mockMethod_1");
        System.out.println("传递的参数：[msg:" + msg + ", foo:" + JSON.toJSONString(foo) + "]");
        return "mockMethod_1返回值";
    }
}
