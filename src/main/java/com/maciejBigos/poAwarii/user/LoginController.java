package com.maciejBigos.poAwarii.user;

import com.maciejBigos.poAwarii.security.JsonWebToken;
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
    private AuthenticationManager authenticationManager;

    public LoginController(UserService userService, JsonWebToken jsonWebToken, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jsonWebToken = jsonWebToken;
        this.authenticationManager = authenticationManager;
    }

    @CrossOrigin
    @PostMapping(value = "/logon", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logon(@Valid @RequestBody LoginBody loginBody){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginBody.getEmail(),loginBody.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String generatedJsonWebToken = jsonWebToken.generateJsonWebToken(authentication);
        return ResponseEntity.ok(generatedJsonWebToken);
    }
}
