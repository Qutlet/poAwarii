package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.exceptions.UserIsNotSpecialistException;
import com.maciejBigos.poAwarii.model.DTO.LoginBody;
import com.maciejBigos.poAwarii.model.User;
import com.maciejBigos.poAwarii.model.messeges.ResponseLogin;
import com.maciejBigos.poAwarii.security.JsonWebToken;
import com.maciejBigos.poAwarii.service.SpecialistService;
import com.maciejBigos.poAwarii.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class LoginController {

    private final UserService userService;
    private final JsonWebToken jsonWebToken;
    private final SpecialistService specialistService;
    private AuthenticationManager authenticationManager;

    public LoginController(UserService userService, JsonWebToken jsonWebToken, SpecialistService specialistService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jsonWebToken = jsonWebToken;
        this.specialistService = specialistService;
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin
    @PostMapping(value = "/logon", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logon(@Valid @RequestBody LoginBody loginBody) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getEmail(),loginBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String generatedJsonWebToken = jsonWebToken.generateJsonWebToken(authentication);
        User user = userService.findByEmail(loginBody.getEmail());
        ResponseLogin responseLogin;
        try {
            Long specId = specialistService.getByUserId(user.getId()).getId();
            responseLogin = new ResponseLogin(generatedJsonWebToken, userService.getRoleLevel(user.getId()), user.getId(), specId);
            return ResponseEntity.ok(responseLogin);
        } catch (UserIsNotSpecialistException e) {
            responseLogin = new ResponseLogin(generatedJsonWebToken, userService.getRoleLevel(user.getId()), user.getId(), (long) -1);
            return ResponseEntity.ok(responseLogin);
        }
    }
}
