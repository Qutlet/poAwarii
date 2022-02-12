package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.model.DTO.ChangePasswordDTO;
import com.maciejBigos.poAwarii.model.DTO.UserDto;
import com.maciejBigos.poAwarii.model.messeges.ResponseMessage;
import com.maciejBigos.poAwarii.model.messeges.ResponseUser;
import com.maciejBigos.poAwarii.security.AuthorizationService;
import com.maciejBigos.poAwarii.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    public static final String UNAUTHORIZED_MESSAGE = "YOU DON'T HAVE PERMISSIONS TO COMPLETE THIS ACTION, PLEASE CONTACT ADMINISTRATOR IF YOU CONSIDER IT'S ERROR";

    private final UserService userService;

    private final AuthorizationService authorizationService;

    private AuthenticationManager authenticationManager;

    public UserController(UserService userService, AuthorizationService authorizationService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authorizationService = authorizationService;
        this.authenticationManager = authenticationManager;
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
        if (authorizationService.isAdmin(authentication) || authorizationService.isAccountOwner(userID,authentication)){
            userService.deleteAccount(userID);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping
    public ResponseEntity<ResponseMessage> editUser(@RequestParam(name = "userID") String userId,
                                      @RequestBody UserDto userDto,
                                      Authentication authentication) {
        if (authorizationService.isAdmin(authentication) || authorizationService.isAccountOwner(userId, authentication)) {
            ResponseMessage responseMessage = userService.editUser(userId, userDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(UNAUTHORIZED_MESSAGE));
        }
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestParam(name = "userID") String userId,
                                                          @RequestBody ChangePasswordDTO changePasswordDTO,
                                                          Authentication authentication) {
        if (authorizationService.isAdmin(authentication)) {
            ResponseMessage responseMessage = userService.changePassword(userId, changePasswordDTO.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } else if (authorizationService.isAccountOwner(userId, authentication)) {
            Authentication tmpAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getName(),changePasswordDTO.getOldPassword()));
            if (tmpAuthentication.isAuthenticated()) {
                ResponseMessage responseMessage = userService.changePassword(userId, changePasswordDTO.getPassword());
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(UNAUTHORIZED_MESSAGE));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseMessage(UNAUTHORIZED_MESSAGE));
        }
    }


    //todo emm where endpoint???? !important
    //fixme !important


}
