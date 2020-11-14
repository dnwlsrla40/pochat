package com.woojin.pochat.util;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@WebFilter(urlPatterns = {"/api/**"}, description = "API 필터")
@Component("CorsFilter")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletRequest.setCharacterEncoding("utf-8");
        //set header

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods",
                "POST, GET, DELETE, PUT, PATCH, OPTIONS");
        httpServletResponse.setHeader("Accept-Charset", "utf-8");
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        httpServletResponse.setHeader("Expires", "-1");
        httpServletResponse.setHeader("Pragma", "no-cache");

        chain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
