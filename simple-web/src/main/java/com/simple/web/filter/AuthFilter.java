package com.simple.web.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class AuthFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    private Set<String> unChecked;

    private String errorUrl;

    private boolean isDebug;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (isDebug)
            chain.doFilter(request, response);
        // request.get
    }

    public void destroy() {

    }

    private boolean matches(String path) {
        if (null != unChecked)
            for (String str : unChecked) {
                if (path.matches(str))
                    return true;
            }
        return false;
    }

    public Set<String> getUnChecked() {
        return unChecked;
    }

    public AuthFilter setUnChecked(Set<String> unChecked) {
        this.unChecked = unChecked;
        return this;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public AuthFilter setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
        return this;
    }

}
