package com.cn.kvn.mock.server.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 复合过滤器. 把配置在 web.xml 的过滤器转移为 spring bean
 * 
 * 给过滤器提供注入功能, 同时避免 web.xml 过多过滤器导致配置长
 * 
 * @author platform
 */
public class CompositeFilter implements Filter {

    // 封装的过滤器列表
    private List<? extends Filter> filters = new ArrayList<Filter>();

    public void setFilters(List<? extends Filter> filters) {
        this.filters = new ArrayList<Filter>(filters);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        for (Filter filter : filters) {
            filter.init(new FixedFilterConfig(config, filter));
        }
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        new VirtualFilterChain(chain, filters).doFilter(request, response);
    }

    @Override
    public void destroy() {
        for (int i = filters.size(); i-- > 0;) {
            Filter filter = filters.get(i);
            filter.destroy();
        }
    }

    /**
     * 虚拟过滤器链.
     * 
     * 支持组合过滤器内包含多个过滤器
     */
    private static class VirtualFilterChain implements FilterChain {
        private final FilterChain originalChain;
        private final List<? extends Filter> additionalFilters;
        private int currentPosition = 0;

        private VirtualFilterChain(FilterChain chain,
                                   List<? extends Filter> additionalFilters) {
            this.originalChain = chain;
            this.additionalFilters = additionalFilters;
        }

        public void doFilter(final ServletRequest request,
                             final ServletResponse response) throws IOException, ServletException {
            if (currentPosition == additionalFilters.size()) {
                originalChain.doFilter(request, response);
            } else {
                currentPosition++;
                Filter nextFilter = additionalFilters.get(currentPosition - 1);
                nextFilter.doFilter(request, response, this);
            }
        }

    }

    /**
     * 修复 spring OncePerRequestFilter 过滤 getFilterName 始终返回组合过滤器名, 而不是真实过滤器名的问题.
     */
    private static class FixedFilterConfig implements FilterConfig {
        private FilterConfig config;
        private Filter filter;

        public FixedFilterConfig(FilterConfig config, Filter filter) {
            this.config = config;
            this.filter = filter;
        }

        @Override
        public String getFilterName() {
            return filter.getClass().getName();
        }

        @Override
        public String getInitParameter(String param) {
            return config.getInitParameter(param);
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Enumeration getInitParameterNames() {
            return config.getInitParameterNames();
        }

        @Override
        public ServletContext getServletContext() {
            return config.getServletContext();
        }

    }

}
