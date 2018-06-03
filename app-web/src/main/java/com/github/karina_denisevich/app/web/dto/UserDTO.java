package com.github.karina_denisevich.app.web.dto;


import com.github.karina_denisevich.app.datamodel.LinesInfo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDTO extends AbstractDTO implements Serializable {

    private static final long serialVersionUID = -5569761957638033790L;

    @NotEmpty(message = "User name should not be empty")
    private String username;
    @Length(min = 4, message = "Password length should be more than 3")
    private String password;
    private List<LinesInfo> linesInfoDtos;

    public UserDTO() {

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

    public List<LinesInfo> getLinesInfoDtos() {
        return linesInfoDtos;
    }

    public void setLinesInfoDtos(List<LinesInfo> linesInfoDtos) {
        this.linesInfoDtos = linesInfoDtos;
    }
}
