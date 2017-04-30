package com.github.karina_denisevich.app.web.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

public class UserDTOAdmin extends AbstractDTO implements Serializable {

    @NotEmpty(message = "User name should not be empty")
    private String username;
    @Length(min = 4, message = "Password length should be more than 3")
    private String password;
    private String authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
