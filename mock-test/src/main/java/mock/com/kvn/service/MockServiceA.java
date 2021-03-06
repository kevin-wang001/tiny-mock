package mock.com.kvn.service;

import com.alibaba.fastjson.JSON;
import com.kvn.domain.Foo;
import com.kvn.mock.local.MockConfig;
import com.kvn.mock.local.domain.MockItem;
import com.kvn.service.ServiceA;
import org.springframework.stereotype.Service;

/**
 * @author wzy on 2017/6/1.
 */
@Service
public class MockServiceA {


    public String mockMethod_4() throws Throwable {
        MockItem mi = MockConfig.getMockItem(ServiceA.class.getMethod("method_4"));
        System.out.println("执行mock方法：mockMethod_4");
        System.out.println("mockMethod_4代理执行原始方法：" + mi.getPjp().proceed());
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
    
    public String method_22(String msg, Foo foo){
    	System.out.println("执行mock方法：method_22");
    	System.out.println("传递的参数：[msg:" + msg + ", foo:" + JSON.toJSONString(foo) + "]");
    	return "method_22返回值";
    }
    
    public String method_23(String msg, Foo foo){
        System.out.println("执行mock方法：method_23");
        System.out.println("传递的参数：[msg:" + msg + ", foo:" + JSON.toJSONString(foo) + "]");
        return "method_23返回值";
    }
    
    public String mockMethod_1(String msg, Foo foo){
        System.out.println("执行mock方法：mockMethod_1");
        System.out.println("传递的参数：[msg:" + msg + ", foo:" + JSON.toJSONString(foo) + "]");
        return "mockMethod_1返回值";
    }
}
