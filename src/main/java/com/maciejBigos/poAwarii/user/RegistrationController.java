package com.maciejBigos.poAwarii.user;

import com.maciejBigos.poAwarii.help.UserAlreadyExistException;
import com.maciejBigos.poAwarii.role.RoleLevel;
import com.maciejBigos.poAwarii.role.RoleService;
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

    @PostMapping(value = "/registration", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity register(@Valid @RequestBody UserDto userDto){
        User user;
        try {
            user = userService.registerNewUser(userDto);
            roleService.addRoleToUser(user.getId(), RoleLevel.USER);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

}
