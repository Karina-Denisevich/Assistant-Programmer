package com.github.karina_denisevich.app.web.converter.dto_to_entity;

import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.web.dto.UserDTOAdmin;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DtoToUserAdmin implements Converter<UserDTOAdmin, User> {

    @Override
    public User convert(final UserDTOAdmin userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());

        if (StringUtils.isNotEmpty(userDTO.getAuthorities())) {
            List<Authority> authorities = new ArrayList();
            authorities.add(Authority.valueOf(userDTO.getAuthorities()));
            user.setAuthorities(authorities);
        }
        return user;
    }
}
