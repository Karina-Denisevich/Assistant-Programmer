package com.github.karina_denisevich.app.datamodel;

import org.springframework.security.core.GrantedAuthority;


public enum Authority implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_ANONYMOUS;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
