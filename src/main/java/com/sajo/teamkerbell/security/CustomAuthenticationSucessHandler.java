package com.sajo.teamkerbell.security;

import com.sajo.teamkerbell.entity.User;
import com.sajo.teamkerbell.service.LogsService;
import com.sajo.teamkerbell.service.UserService;
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


    private UserService userService;

    private LogsService logService;

    public CustomAuthenticationSucessHandler(UserService userService, LogsService logService) {
        this.userService = userService;
        this.logService = logService;
    }

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException {
        User u = userService.getUser(authentication.getName());
        request.getSession().setAttribute("useridx", u.getUserId());

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
