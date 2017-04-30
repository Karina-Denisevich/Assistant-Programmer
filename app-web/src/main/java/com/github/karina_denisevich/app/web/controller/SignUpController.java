package com.github.karina_denisevich.app.web.controller;

import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.services.UserService;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/signup")
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversionServiceFactoryBean conversionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@Valid @RequestBody UserDTO userDto) {
        User user = (conversionService.getObject().convert(userDto, User.class));
        userService.create(user);
        return new ResponseEntity<>(user.getId(), HttpStatus.CREATED);
    }
}
