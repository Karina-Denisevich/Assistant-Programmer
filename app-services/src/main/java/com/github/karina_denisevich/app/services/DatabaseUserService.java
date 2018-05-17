package com.github.karina_denisevich.app.services;


import com.github.karina_denisevich.app.dao.repository.UserRepository;
import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.LinesInfo;
import com.github.karina_denisevich.app.datamodel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseUserService implements UserService {

    private final UserRepository repository;

    @Autowired
    public DatabaseUserService(final UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(final User user) {
        if (user.getAuthorities() == null) {
            List<Authority> authorities = new ArrayList() {{
                add(Authority.ROLE_USER);
            }};

            user.setAuthorities(authorities);
        }

        return repository.save(user);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public User find(final String id) {
        return repository.findOne(id);
    }

    //uses only to get user from db
    @Override
    public User findByUsername(final String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.details.id")
    public User update(final String id, final User user) {
        user.setId(id);
        return repository.save(user);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.userId")
    public String delete(final String id) {
        repository.delete(id);
        return id;
    }

    @Override
    public String saveLinesInfo(LinesInfo linesInfo, String userId) {
        User user = repository.findOne(userId);
        boolean found = user.getLinesInfoList().stream().anyMatch(linesInfo::equals);

        if(!found){
            user.getLinesInfoList().add(linesInfo);
        }
        repository.save(user);
        return "saved";
    }
}
