package com.github.karina_denisevich.app.services.impl;

import com.github.karina_denisevich.app.dao.repository.UserRepository;
import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User create(User user) {
        user.setCreatedAt(String.valueOf(LocalDateTime.now()));
        if(user.getAuthorities() == null) {
            List<Authority> authorities = new ArrayList() {{
                add(Authority.ROLE_USER);
            }};
            user.setAuthorities(authorities);
        }
        return repository.save(user);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public User find(String id) {
        return repository.findOne(id);
    }

    //uses only to get user from db
    @Override
    public User findByUsername(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.details.id")
    public User update(String id, User user) {
        user.setId(id);
        User saved = repository.findOne(id);
        if (saved != null) {
            user.setCreatedAt(saved.getCreatedAt());
            user.setUpdatedAt(String.valueOf(LocalDateTime.now()));
            repository.save(user);
            return user;
        } else {
            user.setId(null);
            return user;
        }
    }

    @Override
  //  @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.userId")
    public String delete(String id) {
        if (repository.findOne(id) == null) {
            return null;
        }
        repository.delete(id);
        return id;
    }
}
