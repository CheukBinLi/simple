package com.simple.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class CrossFilter implements Filter, FilterConstant {

    private static final Logger LOG = LoggerFactory.getLogger(CrossFilter.class);

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (null == httpRequest.getHeader("Access-Control-Request-Method")) {
			httpResponse.addHeader("Access-Control-Allow-Origin", "*");

			httpResponse.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
			httpResponse.addHeader("Access-Control-Max-Age", MAX_AGE);
			httpResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with," + TOKEN_NAME + ",content-type");
			httpResponse.addHeader("Access-Control-Expose-Headers", "x-requested-with," + TOKEN_NAME + ",content-type");
			httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		}
		//		String path = httpRequest.getServletPath();
		//		if (LOG.isDebugEnabled()) LOG.debug(path);
		//		if (isDebug || except.contains(path) || (null != regular && matches(path))) {
		//			filterChain.doFilter(request, response);
		//			return;
		//		}
		//		if (matches(path)) chain.doFilter(request, response);
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
