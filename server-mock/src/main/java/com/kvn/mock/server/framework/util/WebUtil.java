package com.kvn.mock.server.framework.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

/**
 * web层工具类
 * 
 * @author 464281
 * @version $Id: WebUtil.java 2014-5-27 下午3:05:58 $
 */
public class WebUtil extends WebUtils {

	/**
	 * unknown
	 */
	public static final String UNKNOWN = "unknown";

	/**
	 * 获取请求url上指定参数名对应的参数值
	 * <p>
	 * 没有对应的参数名则返回<code>NULL</code>，存在多个则返回第一个
	 * 
	 * @param request
	 *            HTTP请求
	 * @param name
	 *            指定参数名
	 * @return 对应的参数值
	 */
	public static final String getQueryParameter(HttpServletRequest request, String name) {
		String[] values = getQueryParameterValues(request, name);
		return (values == null || values.length <= 0) ? null : values[0];
	}

	/**
	 * 获取请求url上指定参数名对应的参数值集合
	 * <p>
	 * 没有对应的参数名则返回空集合
	 * 
	 * @param request
	 *            HTTP请求
	 * @param name
	 *            指定参数名
	 * @return 对应的参数值集合
	 */
	public static final String[] getQueryParameterValues(HttpServletRequest request, String name) {
		List<String> values = new ArrayList<String>();
		String queryString = request.getQueryString();

		if (!StringUtils.isEmpty(queryString)) {
			String[] queryNVs = queryString.split("&");

			for (String queryNV : queryNVs) {
				if (!StringUtils.isEmpty(queryNV)) {
					String[] parameterNV = queryString.split("=");

					if (parameterNV.length == 2 && parameterNV[0].equals(name)) {
						values.add(parameterNV[1]);
					}
				}
			}
		}
		return values.toArray(new String[] {});
	}

	/**
	 * 获取请求URI和请求参数
	 * 
	 * @param request
	 *            HTTP请求
	 * @return 请求URI和请求参数
	 */
	public static final String getRequestUriWithQueryString(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String queryString = request.getQueryString();
		if (StringUtils.isEmpty(queryString)) {
			return requestUri;
		} else {
			return requestUri + '?' + queryString;
		}
	}

	/**
	 * 获取ip地址
	 */
	public static final String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (!StringUtils.isEmpty(ip) && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
}
