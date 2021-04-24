package com.alextim.security.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.alextim.security.token.PrimaryAuthenticationToken;
import com.alextim.security.token.SecondaryAuthenticationToken;

@Component
@Slf4j
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationProcessingFilter(@Autowired AuthenticationManagerImpl authenticationManager) {
        super(new AntPathRequestMatcher("/login", "POST"));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
        setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("Attempt authentication");

        Authentication authentication = null;

        Set<String> parameters = new HashSet<>(Collections.list(request.getParameterNames()));

        if(parameters.contains("username") && parameters.contains("password")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            authentication = new UsernamePasswordAuthenticationToken(username, password);
        }
        else if(parameters.contains("username") && parameters.contains("sms")) {
            String username = request.getParameter("username");
            String sms = request.getParameter("sms");
            authentication = new SecondaryAuthenticationToken(username, sms);
        }
        else {
            log.warn("There is no username/password or username/sms in the parameters.");
        }
        return getAuthenticationManager().authenticate(authentication);
    }

    private class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            if(authentication instanceof PrimaryAuthenticationToken) {
                log.info("Authentication success witch PrimaryAuthenticationToken");
                request.getRequestDispatcher("/login_success1").forward(request, response);
            }
            else if(authentication instanceof SecondaryAuthenticationToken) {
                log.info("Authentication success witch SecondaryAuthenticationToken");
                request.getRequestDispatcher("/login_success2").forward(request, response);
            }
            else if(authentication instanceof UsernamePasswordAuthenticationToken) {
                log.info("Authentication success witch UsernamePasswordAuthenticationToken");
                request.getRequestDispatcher("/login_success").forward(request, response);
            }
            else {
                log.warn("Unknown authentication token");
            }
        }
    }

    private class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            log.info("Authentication fail");
            request.getRequestDispatcher("/login_fail").forward(request, response);
        }
    }
}