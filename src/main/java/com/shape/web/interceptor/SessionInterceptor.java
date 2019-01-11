package com.shape.web.interceptor;

import com.shape.web.controller.BaseController;
import com.shape.web.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Session interceptor test");
        int userIdx=(Integer)request.getSession().getAttribute(Attribute.USER_IDX);
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        if(handlerMethod.getBean().getClass().isAssignableFrom(BaseController.class)){
            ((BaseController)handlerMethod.getBean()).setSessionId(userIdx);
        }
        return super.preHandle(request, response, handler);
    }
}
