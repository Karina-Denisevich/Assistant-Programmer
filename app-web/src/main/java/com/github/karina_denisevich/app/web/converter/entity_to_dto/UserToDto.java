package com.github.karina_denisevich.app.web.converter.entity_to_dto;

import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

public class UserToDto implements Converter<User, UserDTO> {

    @Autowired
    private ConversionServiceFactoryBean conversionService;

    @Override
    public UserDTO convert(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
