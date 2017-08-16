package com.github.karina_denisevich.app.web.dto;


import java.io.Serializable;

public class TokenDTO implements Serializable {

    private static final long serialVersionUID = -5458105107471833143L;

    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
