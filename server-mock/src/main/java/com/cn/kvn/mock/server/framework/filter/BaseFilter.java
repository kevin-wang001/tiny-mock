package com.cn.kvn.mock.server.framework.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 框架所有filter的抽象父类，主要是过滤掉一些静态资源、健康检查的链接等
 * @author zhengxiaohong
 */
public abstract class BaseFilter extends OncePerRequestFilter {

    /**
     * 默认过滤链接
     */
    private List<Pattern> excludeUrlPatterList = new ArrayList<Pattern>();
    
    /**
     * 过滤链接，只是做属性用
     */
    protected List<String> excludeUrls = null;

    /**
     * 是否被初始化过
     */
    private boolean inited = false;
    
    @Override
    protected void initFilterBean() throws ServletException {
        if(inited) {
            return ;
        }
        super.initFilterBean();
        // 静态资源
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.js"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.css"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.html"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.png"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.jpg"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.gif"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.xls"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.xlsx"));
        excludeUrlPatterList.add(Pattern.compile("/(.*)?/(.*)?\\.jsp"));
        
        excludeUrlPatterList.add(Pattern.compile("/health_check"));
        excludeUrlPatterList.add(Pattern.compile("/check_health"));
        excludeUrlPatterList.add(Pattern.compile("/_health.html"));
        excludeUrlPatterList.add(Pattern.compile("/"));
        
        // 更新初始化状态（这个方法是会被调用多次的）
        inited = true;
    }

    /**
     * 判断是否走当前filter
     * @param request 请求体
     * @return 不走filter的话，返回true；请求要过filter的话，返回false
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (excludeUrlPatterList == null) {
            return false;
        }
        for(Pattern pattern : excludeUrlPatterList) {
            String reqPath = request.getServletPath();
            Matcher matcher = pattern.matcher(reqPath);
            if(matcher.matches()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 设置不打印请求的url
     * @param excludeUrls 不打印请求的url
     */
    public void setExcludeUrls(List<String> excludeUrls) {
        if(excludeUrls == null) {
            return ;
        }
        for(String url : excludeUrls) {
            Pattern pattern = Pattern.compile(url);
            excludeUrlPatterList.add(pattern);
        }
    }
}
