package com.github.karina_denisevich.app.web.controller;


import com.github.karina_denisevich.app.services.TokenService;
import com.github.karina_denisevich.app.web.dto.LoginDTO;
import com.github.karina_denisevich.app.web.dto.TokenDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

//FIXME add api versioning!
public class AuthenticationController {

    private final TokenService tokenService;

    public AuthenticationController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody LoginDTO dto) {
        return new ResponseEntity<>(new TokenDTO(tokenService.getToken(dto.getUsername(),
                dto.getPassword())), HttpStatus.OK);
    }
}
