package com.github.karina_denisevich.app.web.converter.dto_to_entity;

import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTOAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DtoToUserAdmin implements Converter<UserDTOAdmin, User> {

    @Autowired
    private ConversionServiceFactoryBean conversionService;

    @Override
    public User convert(UserDTOAdmin userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        if (userDTO.getAuthorities() != null) {
            List<Authority> authorities = new ArrayList();
            authorities.add(Authority.valueOf(userDTO.getAuthorities()));
            user.setAuthorities(authorities);
        }
        return user;
    }
}
