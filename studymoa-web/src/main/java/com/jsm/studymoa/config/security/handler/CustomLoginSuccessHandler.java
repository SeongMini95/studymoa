package com.jsm.studymoa.config.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final String DEFAULT_PAGE = "/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        RequestCache requestCache = new HttpSessionRequestCache();
        requestCache.removeRequest(request, response);

        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isBlank()) {
            returnUrl = DEFAULT_PAGE;
        }

        response.sendRedirect(returnUrl);
    }
}
