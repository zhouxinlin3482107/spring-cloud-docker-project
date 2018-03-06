package com.example.uaa.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class LoginFilter implements Filter {

    private String testHomePage;
    private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    public LoginFilter(String csfaHomePage) {

        this.testHomePage = testHomePage;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        logger.debug("Request URI: " + request.getRequestURI());
        logger.debug("Request URL: " + request.getRequestURL());
        logger.debug("Query String: " + request.getQueryString());
        logger.debug("Path Info: " + request.getPathInfo());
        logger.debug("Auth Type: " + request.getAuthType());
        logger.debug("New Session: " + isNewSession(request));

        if (request.getRequestURI().equals("/uaa/login")) {
            DefaultSavedRequest springSecuritySavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (null != springSecuritySavedRequest) {
                String redirectUrl = springSecuritySavedRequest.getRedirectUrl();
                logger.debug("DefaultSavedRequest: " + redirectUrl);
                if (redirectUrl.endsWith("/login?logout")) {
                    logger.debug("Redirect TEST Home Page: " + testHomePage);
                    response.sendRedirect(testHomePage);
                }
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isNewSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attr = attributeNames.nextElement();
            logger.debug("Session Attribute Name: " + attr + " Value: " + session.getAttribute(attr));
        }
        return session.isNew();
    }

    @Override
    public void destroy() {

    }
}
