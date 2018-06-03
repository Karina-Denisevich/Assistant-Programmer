package com.github.karina_denisevich.app.web.converter.entity_to_dto;

import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;

public class UserToDto implements Converter<User, UserDTO> {

    @Override
    public UserDTO convert(final User user) {
        UserDTO userDto = new UserDTO();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setLinesInfoDtos(user.getLinesInfoList());

        return userDto;
    }
}
