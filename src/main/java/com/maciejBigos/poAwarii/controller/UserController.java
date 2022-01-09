package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.model.User;
import com.maciejBigos.poAwarii.model.messeges.ResponseUser;
import com.maciejBigos.poAwarii.security.AuthenticationService;
import com.maciejBigos.poAwarii.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping(path = "/{userID}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByID(@PathVariable String userID, Authentication authentication){
        ResponseUser user = userService.getUserByID(userID);
        return  ResponseEntity.ok(user);
    }

    @GetMapping(path = "/{userID}/roles" , produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserRoles(@PathVariable String userID, Authentication authentication) {
        return ResponseEntity.ok(userService.getUserRoles(userID));
    }

    @GetMapping(path = "/get/me", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMe(Authentication authentication) {
        return ResponseEntity.ok(userService.getMe(authentication.getName()));
    }

    @DeleteMapping(path = "/{userID}/delete")
    public ResponseEntity<?> deleteAccount(@PathVariable String userID, Authentication authentication){
        if (authenticationService.isAdmin(authentication) || authenticationService.isAccountOwner(userID,authentication)){
            userService.deleteAccount(userID);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    //todo emm where endpoint???? !important
    //fixme !important


}
