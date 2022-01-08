package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.exceptions.UserAlreadyExistException;
import com.maciejBigos.poAwarii.model.User;
import com.maciejBigos.poAwarii.model.DTO.UserDto;
import com.maciejBigos.poAwarii.model.enums.RoleLevel;
import com.maciejBigos.poAwarii.model.messeges.ResponseUser;
import com.maciejBigos.poAwarii.service.RoleService;
import com.maciejBigos.poAwarii.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RegistrationController {

    private final UserService userService;

    private final RoleService roleService;

    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @CrossOrigin
    @PostMapping(value = "/registration", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody UserDto userDto){
        ResponseUser user;
        try {
            user = userService.registerNewUser(userDto);
            roleService.addRoleToUser(user.getId(), RoleLevel.USER);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

}
