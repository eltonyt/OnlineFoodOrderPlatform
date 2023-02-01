package com.elton.foodorder.filter;

import com.alibaba.fastjson.JSON;
import com.elton.foodorder.utils.BaseContext;
import com.elton.foodorder.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        log.info("Received Request: {}", httpServletRequest.getRequestURI());

        String requestURI = httpServletRequest.getRequestURI();
        // WHITELIST
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        boolean whiteListedRequest = whiteListedURI(urls, requestURI);
        if (whiteListedRequest) {
            log.info("This is a white listed request.");
            filterChain.doFilter(servletRequest, httpServletResponse);
            return;
        }

        if (httpServletRequest.getSession().getAttribute("employee") != null) {
            log.info("Employee already logged in.");
            Long employeeId = (Long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(employeeId);
            filterChain.doFilter(servletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    private boolean whiteListedURI(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
