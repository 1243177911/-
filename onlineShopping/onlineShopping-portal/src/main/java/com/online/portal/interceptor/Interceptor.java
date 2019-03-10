package com.online.portal.interceptor;

import com.online.common.utils.CookieUtils;
import com.online.pojo.TbUser;
import com.online.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得cookie
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        //通过cookie获得user对象(写一个service调用sso的服务)
        TbUser user = userService.getUserByToken(token);
        if(user == null){
            response.sendRedirect(userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN +"?redirect=http://localhost:8082" + request.getRequestURI());
            return false;
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
