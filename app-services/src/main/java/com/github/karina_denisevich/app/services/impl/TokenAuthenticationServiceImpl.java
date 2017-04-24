package com.github.karina_denisevich.app.services.impl;

import com.github.karina_denisevich.app.common.constants.SecurityConstants;
import com.github.karina_denisevich.app.common.exception.model.UserNotFoundException;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.datamodel.UserAuthentication;
import com.github.karina_denisevich.app.services.TokenAuthenticationService;
import com.github.karina_denisevich.app.services.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService {

    @Value("security.token.secret.key")
    private String secretKey;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        if (token != null && !token.equals("undefined")) {
            final Jws<Claims> tokenData;
            try {
                tokenData = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            } catch (MalformedJwtException ex) {
                return null;
            }
            User user = getUserFromToken(tokenData);
            if (user != null) {
                return new UserAuthentication(user);
            }
        }
        return null;
    }

    @Override
    public void addAuthentication(HttpServletResponse response, UserDetails userDetails) {
        final User user = (User) userDetails;
        response.addHeader(SecurityConstants.AUTH_HEADER_NAME, tokenService.getToken(user.getUsername(), user.getPassword()));
    }

    private User getUserFromToken(Jws<Claims> tokenData) {
        try {
            return (User) userDetailsService.loadUserByUsername(tokenData.getBody().get("username").toString());
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException("User " + tokenData.getBody().get("username").toString() + " not found");
        }
    }
}
