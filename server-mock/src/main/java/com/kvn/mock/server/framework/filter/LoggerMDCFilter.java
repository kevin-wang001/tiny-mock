package com.kvn.mock.server.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;

import com.kvn.mock.server.framework.common.LogConst;

/**
 * 日志MDC过滤器
 * <p>
 * 加入url和uid的mdc参数
 * 
 * @author 464281
 */
public class LoggerMDCFilter extends BaseFilter {

	/**
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			MDC.put(LogConst.URL, request.getServletPath());
			filterChain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}

}
