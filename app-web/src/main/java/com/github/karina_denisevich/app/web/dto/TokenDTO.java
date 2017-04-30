package com.github.karina_denisevich.app.web.dto;

import java.io.Serializable;


public class TokenDTO implements Serializable {

    private String token;

    public TokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
