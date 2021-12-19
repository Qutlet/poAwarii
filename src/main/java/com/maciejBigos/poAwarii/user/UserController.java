package com.maciejBigos.poAwarii.user;

import com.maciejBigos.poAwarii.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {


    private final UserService userService;

    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "/users/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByID(@PathVariable String userID, Authentication authentication){
        User user = userService.getUserByID(userID);
        return  ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/users/{userID}/delete")
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
