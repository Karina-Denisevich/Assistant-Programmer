package com.github.karina_denisevich.app.services;


import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface TokenAuthenticationService {

    Optional<Authentication> authenticate(HttpServletRequest request);
}
