package com.simple.web.filter;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.simple.core.entity.User;
import com.simple.core.model.LoginInfoModel;
import com.simple.core.util.SimpleConstant;

public class DebugLoginFilter implements Filter, SimpleConstant {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Object tenantId;
        LoginInfoModel loginInfoModel;
        Object tempValue;
        if (null == (tempValue = httpServletRequest.getSession().getAttribute(SESSION_LOGIN_INFO))) {
            User user = new User();
            tenantId = request.getParameter("tenantId");
            user.setUserName("DebugAdmin");
            if (null != tenantId) {
                user.setTenantId((null == tenantId ? 110120130L : Long.valueOf(tenantId.toString())));
            }
            httpServletRequest.getSession().setAttribute(SESSION_LOGIN_INFO, tempValue = new LoginInfoModel().setUser(user));
        }
        loginInfoModel = (LoginInfoModel) tempValue;
        if (null != (tenantId = request.getParameter("tenantId"))) {
            loginInfoModel.getUser().setTenantId(Long.valueOf(tenantId.toString()));
            httpServletRequest.getSession().setAttribute(SESSION_LOGIN_INFO, loginInfoModel);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
