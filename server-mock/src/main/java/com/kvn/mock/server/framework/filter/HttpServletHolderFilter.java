package com.kvn.mock.server.framework.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kvn.mock.server.framework.context.HttpServletHolder;

/**
 * 设置HTTP SERVLET请求和响应持有者本地线程变量
 * 
 * @author 464281
 */
public class HttpServletHolderFilter extends BaseFilter {

	/**
	 * (non-Javadoc)
	 * 
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String uniqueId = request.getHeader(HttpServletHolder.UNIQUE_ID_HEADER_NAME);
			HttpServletHolder.set(request, response, uniqueId);
			filterChain.doFilter(request, response);
		} finally {
			HttpServletHolder.remove();
		}
	}
}
