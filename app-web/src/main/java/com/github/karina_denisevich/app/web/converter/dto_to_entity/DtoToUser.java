package com.github.karina_denisevich.app.web.converter.dto_to_entity;

import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DtoToUser implements Converter<UserDTO, User> {

    @Override
    public User convert(final UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAccountNonExpired(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setLinesInfoList(userDTO.getLinesInfoDtos() == null ? new ArrayList<>() : userDTO.getLinesInfoDtos());

        return user;
    }
}
