package com.shape.web.security;

import com.shape.web.entity.User;
import com.shape.web.repository.UserRepository;
import com.shape.web.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by seongahjo on 2016. 7. 15..
 */
public class CustomAuthenticationSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogsService logService;



    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        User u = userRepository.findById(authentication.getName());
        request.getSession().setAttribute("useridx", u.getUseridx());

        String ip = request.getHeader("X_FORWARDED_FOR");
        if (ip == null)
            ip = request.getRemoteAddr();
        // 127.0.0.1 IPv4
        // -Djava.net.preferIPv4Stack=true
        // 0:0:0:0:0:0:0:1 IPv6
        StringBuilder sb = new StringBuilder();
        Enumeration<String> it = request.getHeaderNames();
        while (it.hasMoreElements()) {
            String temp = it.nextElement();
            sb.append(temp);
            sb.append(":");
            sb.append(request.getHeader(temp));
            sb.append("\\");
        }
        logService.addLog(ip, sb.toString(), u);
        handle(request, response, authentication);
    }
}
