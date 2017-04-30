package com.github.karina_denisevich.app.web.converter.entity_to_dto;

import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import com.github.karina_denisevich.app.web.dto.UserDTOAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public class UserToDtoAdmin implements Converter<User, UserDTOAdmin> {

    @Autowired
    private ConversionServiceFactoryBean conversionService;

    @Override
    public UserDTOAdmin convert(User user) {
        UserDTOAdmin userDto = new UserDTOAdmin();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        String stringAuthorities = "";
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority != null) {
                    stringAuthorities = authority.getAuthority();
                }
            }
        }
        userDto.setAuthorities(stringAuthorities);
        return userDto;
    }
}
