package com.github.karina_denisevich.app.web.converter.dto_to_entity;

import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

@SuppressWarnings("unchecked")
public class DtoToUser implements Converter<UserDTO, User> {

    @Autowired
    private ConversionServiceFactoryBean conversionService;

    @Override
    public User convert(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setAccountNonExpired(false);
        user.setCredentialsNonExpired(false);
        user.setEnabled(true);
        user.setAccountNonLocked(true);

        return user;
    }
}
