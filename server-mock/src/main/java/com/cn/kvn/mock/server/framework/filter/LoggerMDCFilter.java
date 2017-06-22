package com.cn.kvn.mock.server.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import com.cn.kvn.mock.log.LogConst;
import com.cn.kvn.mock.log.LogTools;
import com.cn.kvn.mock.server.framework.context.HttpServletHolder;

/**
 * 日志MDC过滤器
 * <p>
 * 加入url和uid的mdc参数
 * 
 */
public class LoggerMDCFilter extends BaseFilter {

	/**
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String uid = HttpServletHolder.getUid();
			if (StringUtils.isBlank(uid)) {
				uid = LogTools.generateUID();
			}
			MDC.put(LogConst.URL, request.getServletPath());
			MDC.put(LogConst.UID, uid);
			filterChain.doFilter(request, response);
		} finally {
			MDC.clear();
		}
	}

}
