package com.cn.kvn.mock.server.framework;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * http response的切面
 * @author wzy
 *
 */
public class ResponseAspect {
	public static final String WEB_REQ = "http.req";
    public static final String WEB_RESP = "http.resp";
    public static final String WEB_REQ_TIME = "req.time";

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 拦截请求,打印相应结果
     * @param pjp 
     * @return
     * @throws Throwable 
     */
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 获取token
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        // TODO 以后要注意那些非ResponseBody的情况，譬如直接加@RESTController的情况
        ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
        if (responseBody == null) {
            return pjp.proceed();
        }
        Object retValue = pjp.proceed();
        if(retValue != null) {
            doLog(retValue);
        }
        return retValue;
    }

    /**
     * 打印返回日志,不能影响到主流程
     * @param retValue
     */
    private void doLog(Object retValue) {
        try{
            String json = JSON.toJSONString(retValue);
            
            logger.info(WEB_RESP + "|ResponseBody:" + json);
        }catch(Throwable ex) {
            logger.debug(WEB_RESP + "|retValue:{}, exception occur:{}", retValue, ex);
        }
    }

}
