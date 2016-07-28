package com.shape.web.security;

import com.shape.web.entity.User;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by seongahjo on 2016. 7. 15..
 */
public class CustomAuthenticationSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LogService logService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User u = userRepository.findById(authentication.getName());
        request.getSession().setAttribute("user",u);
        String ip=request.getHeader("X_FORWARDED_FOR");
        if(ip==null)
            ip=request.getRemoteAddr();
        logService.addLog(ip,u);
        handle(request,response,authentication);
    }
}
