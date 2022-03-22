package com.jsm.studymoa.config.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final String DEFAULT_PAGE = "/account/login";
    private final String DEFAULT_ERROR_MESSAGE = "아이디 또는 비밀번호를 확인해주세요.";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        request.setAttribute("returnUrl", request.getParameter("returnUrl"));
        request.setAttribute("errorMessage", DEFAULT_ERROR_MESSAGE);

        request.getRequestDispatcher(DEFAULT_PAGE).forward(request, response);
    }
}
