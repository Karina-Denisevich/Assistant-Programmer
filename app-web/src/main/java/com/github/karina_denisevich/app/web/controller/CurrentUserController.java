package com.github.karina_denisevich.app.web.controller;

import com.github.karina_denisevich.app.datamodel.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@SuppressWarnings("unchecked")
public class CurrentUserController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getCurrentUser() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
