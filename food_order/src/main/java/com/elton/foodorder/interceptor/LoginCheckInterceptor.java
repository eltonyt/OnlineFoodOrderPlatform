package com.elton.foodorder.interceptor;

import com.alibaba.fastjson.JSON;
import com.elton.foodorder.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    // HELPS MATCH PATHS
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private boolean userLoggedIn = true;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Received Request: {}", request.getRequestURI());
        if (request.getSession().getAttribute("employee") == null)
            userLoggedIn = false;
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String requestURI = request.getRequestURI();
        // WHITELIST
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        boolean whiteListedRequest = false;
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                whiteListedRequest = true;
                break;
            }
        }
        if (whiteListedRequest)
            return;

        if (userLoggedIn)
            return;
        System.out.println(JSON.toJSONString(R.error("NOTLOGIN")));
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
}
