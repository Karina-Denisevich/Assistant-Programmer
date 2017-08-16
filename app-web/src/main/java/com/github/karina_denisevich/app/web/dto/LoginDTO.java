package com.github.karina_denisevich.app.web.dto;


import java.io.Serializable;

public class LoginDTO implements Serializable {

    private static final long serialVersionUID = -1420548333235236093L;

    private String username;
    private String password;

    public LoginDTO() {

    }

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
}
