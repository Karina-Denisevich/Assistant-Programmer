package com.github.karina_denisevich.app.web.security.filter;


import com.github.karina_denisevich.app.services.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class AuthenticationTokenFilter extends GenericFilterBean {

    private final TokenAuthenticationService authenticationService;

    public AuthenticationTokenFilter(final TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        Optional<Authentication> authentication = authenticationService.authenticate(httpRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));

        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
