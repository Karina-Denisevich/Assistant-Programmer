package com.github.karina_denisevich.app.web.converter.entity_to_dto;

import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTOAdmin;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class UserToDtoAdmin implements Converter<User, UserDTOAdmin> {

    @Override
    public UserDTOAdmin convert(final User user) {
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
