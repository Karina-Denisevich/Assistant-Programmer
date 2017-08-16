package com.github.karina_denisevich.app.services;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public DatabaseUserDetailsService(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        return Optional.ofNullable(userService.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with username:%s not found", username)));
    }
}
