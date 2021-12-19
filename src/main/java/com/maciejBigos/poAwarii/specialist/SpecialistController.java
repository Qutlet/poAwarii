package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.help.UserAlreadyHaveRoleException;
import com.maciejBigos.poAwarii.role.RoleLevel;
import com.maciejBigos.poAwarii.role.RoleService;
import com.maciejBigos.poAwarii.security.AuthenticationService;
import com.maciejBigos.poAwarii.user.User;
import com.maciejBigos.poAwarii.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SpecialistController {

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "specProfile/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSpecialistProfile(@RequestBody SpecialistProfileDTO specialistProfileDTO, Authentication authentication){
        SpecialistProfile specialistProfile = null;
        try {
            specialistProfile = specialistService.addSpecialistProfile(specialistProfileDTO,authentication);
        } catch (UserAlreadyHaveRoleException e) {
            ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
        return ResponseEntity.ok(specialistProfile);
    }

    @GetMapping(path = "specProfile/{id}/profile", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<SpecialistProfile> getOneSpecialistProfile(@PathVariable Long id){
        return ResponseEntity.ok(specialistService.getSpecialistProfileByID(id));
    }

    @GetMapping(path = "specProfile/cat/{cat}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpecialistProfile>> getAllCategorizedSpecialistProfile(@PathVariable String cat){
        return ResponseEntity.ok(specialistService.getAllCategorizedSpecialistProfiles(cat));
    }

    @GetMapping(path = "test/specProfile", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpecialistProfile>> getAllSpecialistProfile(){
        return ResponseEntity.ok(specialistService.getAllSpecialistProfiles());
    }

    @PutMapping(path = "specProfile/{id}/edit",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editSpecialistProfile(@PathVariable Long id,@RequestBody SpecialistProfileDTO specialistProfileDTO, Authentication authentication) {
        if (authenticationService.isAdmin(authentication) || authenticationService.confirmOwnershipForSpecialistProfile(id,authentication)) {
            SpecialistProfile specialistProfile = specialistService.update(id,specialistProfileDTO);
            return ResponseEntity.ok(specialistProfile);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping(path = "specProfile/{id}/delete/{userID}")
    public ResponseEntity<?> deleteSpecialistProfile(@PathVariable Long id, @PathVariable String userID, Authentication authentication){
        if(authenticationService.confirmOwnershipForSpecialistProfile(id,authentication)){
            specialistService.deleteSpecialistProfile(id);
            roleService.removeRoleFromUser(userID,RoleLevel.SPEC);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
