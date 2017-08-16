package com.github.karina_denisevich.app.services;


import com.github.karina_denisevich.app.common.constants.SecurityConstants;
import com.github.karina_denisevich.app.common.exception.model.UserNotFoundException;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.datamodel.UserAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class JsonWebTokenAuthenticationService implements TokenAuthenticationService {

    @Value("security.token.secret.key")
    private String secretKey;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JsonWebTokenAuthenticationService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Optional<Authentication> authenticate(final HttpServletRequest request) {
        final String token = request.getHeader(SecurityConstants.AUTH_HEADER_NAME);
        final Optional<Jws<Claims>> tokenData = parseToken(token);

        if (tokenData.isPresent()) {
            final User user = getUserFromToken(tokenData.get());
            return Optional.of(new UserAuthentication(user));
        }

        return Optional.empty();
    }

    //FIXME refactor it
    private Optional<Jws<Claims>> parseToken(final String token) {
        Optional<Jws<Claims>> result = Optional.empty();

        if (token != null) {
            try {
                result = Optional.ofNullable(Jwts.parser()
                        .setSigningKey(secretKey)
                        .parseClaimsJws(token));

            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                    | SignatureException | IllegalArgumentException ignored) {

            }
        }

        return result;
    }

    private User getUserFromToken(final Jws<Claims> tokenData) {
        final String username = tokenData.getBody().get("username").toString();

        return Optional.ofNullable((User) userDetailsService.loadUserByUsername(username))
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", tokenData.getBody()
                        .get("username").toString())));
    }
}
