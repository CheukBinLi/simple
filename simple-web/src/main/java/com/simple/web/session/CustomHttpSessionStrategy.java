package com.simple.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.Session;
import org.springframework.session.web.http.MultiHttpSessionStrategy;

import com.simple.web.filter.FilterConstant;

/***
 * 
 * @Title: simple-webTODO
 * @Description: 自定义SESSION
 * @Company:
 * @Email: 20796698@qq.com
 * @author cheuk.bin.li
 * @date 2017年6月23日 下午5:27:02
 *
 */
public class CustomHttpSessionStrategy implements MultiHttpSessionStrategy, FilterConstant {

	public String getRequestedSessionId(HttpServletRequest request) {

		Object result = request.getParameterMap().get(TOKEN_NAME);
		if (null != result) {
			if (result.getClass().isArray()) return ((String[]) result)[0].toString();
		}
		result = request.getHeader(TOKEN_NAME);
		return null == result ? null : result.toString();

	}

	public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(TOKEN_NAME, session.getId());
	}

	public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(TOKEN_NAME, "");
	}

	public HttpServletRequest wrapRequest(HttpServletRequest request, HttpServletResponse response) {
		return request;
	}

	public HttpServletResponse wrapResponse(HttpServletRequest request, HttpServletResponse response) {
		return response;
	}
}
