package com.cn.kvn.mock.server.framework.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.cn.kvn.mock.log.Log;
import com.cn.kvn.mock.log.LogOp;
import com.cn.kvn.mock.server.framework.util.WebUtil;
import com.google.common.base.Strings;

/**
 * 请求摘要拦截器
 * 
 * 记录请求响应时间
 * 
 */
public class PageDigestFilter extends BaseFilter {

    private static Logger logger = LoggerFactory.getLogger("PAGE-DIGEST");

    /**
     * 参数值限制的最大打印字符数的默认值
     */
    public static final int DEFAULT_VALUE_LENGTH_LIMIT = 64;
    
    /**
     * 设置不记录请求参数名的正则表达式. 例如: (?i).*(password|pwd|sign).*
     */
    private String ignoreParamPattern = "(?i).*(password|pwd|bankCard|CardNo|bindCardNo).*";

    /**
     * 参数值限制的最大打印字符数
     */
    private int valueLengthLimit = DEFAULT_VALUE_LENGTH_LIMIT;

    
    /**
     * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (logger.isInfoEnabled()) {
            long startTime = System.currentTimeMillis();
            logger.info(Log.op(LogOp.WEB_REQ).msg(params(request)).toString());

            try {
                filterChain.doFilter(request, response);
            } finally {
                long elapseTime = System.currentTimeMillis() - startTime;
                logger.info(Log.op(LogOp.WEB_RESP).msg(monitor(elapseTime, request)).toString());
            }

        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 记录请求参数
     * 
     * @return 每个方法的请求参数组成的字符串
     */
    public String params(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(256);

        String ip = WebUtil.getIpAddr(request);
        String range = request.getHeader("Range");
        range = Strings.emptyToNull(range);

        sb.append("HttpRequest : ");
        sb.append(ip).append(" ");
        if (range != null) {
            sb.append(range).append(" ");
        }
        sb.append("params:{");

        Map params = request.getParameterMap();
        int index = 0;
        for (Object obj : params.keySet()) {
            if (index > 0) {
                sb.append(",");
            }
            String key = (String) obj;
            String[] value = (String[]) params.get(key);
            if (!StringUtils.isEmpty(ignoreParamPattern) && key.matches(ignoreParamPattern)) {
                sb.append(key).append("=").append("******");
            } else if (value.length == 1) {
                sb.append(key).append("=").append(valueByMask(value[0]));
            } else {
                sb.append(key).append("=[");
                for(int i=0; i<value.length; i++) {
                    sb.append(valueByMask(value[i]));
                    if(i+1 <value.length) {
                        sb.append(",");
                    }
                }
                sb.append("]");
            }
            index++;
        }
        sb.append("}");
        return sb.toString();
    }
    
    private String valueByMask(String itemValue) {
        if(itemValue.length() > valueLengthLimit) {
            return itemValue.substring(0, valueLengthLimit) + "...";
        } else {
            return itemValue;
        }
    }

    
    /**
     * 记录请求时间
     * 
     * @param execTime 请求执行时间
     * @param request 请求体
     * @return
     */
    public String monitor(long execTime, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(80);
        String ip = WebUtil.getIpAddr(request);
        sb.append("HttpResponse: ");
        sb.append(ip).append(" ");
        sb.append("execTime:").append(execTime);
        return sb.toString();
    }

    /**
     * 设置不记录请求参数名的正则表达式. 例如: (?i).*(password|pwd|sign).*
     * @param ignoreParamPattern 不记录请求参数名的正则表达式
     */
    public void setIgnoreParamPattern(String ignoreParamPattern) {
        this.ignoreParamPattern = ignoreParamPattern;
    }
    
    /**
     * 设置参数值限制的最大打印字符数
     * @param valueLengthLimit  参数值限制的最大打印字符数
     */
    public void setValueLengthLimit(int valueLengthLimit) {
        this.valueLengthLimit = valueLengthLimit;
    }
    
}
