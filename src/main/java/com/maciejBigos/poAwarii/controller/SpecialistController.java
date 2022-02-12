package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.exceptions.UserAlreadyHaveRoleException;
import com.maciejBigos.poAwarii.exceptions.UserIsNotSpecialistException;
import com.maciejBigos.poAwarii.model.DTO.SpecialistProfileDTO;
import com.maciejBigos.poAwarii.model.enums.RoleLevel;
import com.maciejBigos.poAwarii.model.messeges.ResponseMessage;
import com.maciejBigos.poAwarii.model.messeges.ResponseSpecialistProfile;
import com.maciejBigos.poAwarii.service.RoleService;
import com.maciejBigos.poAwarii.security.AuthorizationService;
import com.maciejBigos.poAwarii.service.SpecialistService;
import com.maciejBigos.poAwarii.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
public class SpecialistController {

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping(path = "specProfile/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSpecialistProfile(@RequestBody SpecialistProfileDTO specialistProfileDTO, Authentication authentication){
        ResponseSpecialistProfile specialistProfile = null;
        try {
            specialistProfile = specialistService.addSpecialistProfile(specialistProfileDTO,authentication);
        } catch (UserAlreadyHaveRoleException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
        }
        return ResponseEntity.ok(specialistProfile);
    }

    @CrossOrigin
    @GetMapping(path = "specProfile/{id}/profile", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseSpecialistProfile> getOneSpecialistProfile(@PathVariable Long id){
        return ResponseEntity.ok(specialistService.getSpecialistProfileByID(id));
    }

    @GetMapping(path = "specProfile/cat/{cat}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseSpecialistProfile>> getAllCategorizedSpecialistProfile(@PathVariable String cat){
        return ResponseEntity.ok(specialistService.getAllCategorizedSpecialistProfiles(cat));
    }

    @GetMapping(path = "test/specProfile", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseSpecialistProfile>> getAllSpecialistProfile(){
        return ResponseEntity.ok(specialistService.getAllSpecialistProfiles());
    }

    @PutMapping(path = "specProfile/{id}/edit",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editSpecialistProfile(@PathVariable Long id,@RequestBody SpecialistProfileDTO specialistProfileDTO, Authentication authentication) {
        if (authorizationService.isAdmin(authentication) || authorizationService.confirmOwnershipForSpecialistProfile(id,authentication)) {
            ResponseSpecialistProfile specialistProfile = specialistService.update(id,specialistProfileDTO);
            return ResponseEntity.ok(specialistProfile);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping(path = "specProfile/{id}/delete/{userID}")
    public ResponseEntity<?> deleteSpecialistProfile(@PathVariable Long id, @PathVariable String userID, Authentication authentication){
        if(authorizationService.confirmOwnershipForSpecialistProfile(id,authentication)){
            specialistService.deleteSpecialistProfile(id);
            roleService.removeRoleFromUser(userID,RoleLevel.SPEC);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("specProfile/user/{userID}")
    public ResponseEntity<?> getSpecialistProfileByUser(@PathVariable String userID, Authentication authentication) {
        try {
            return ResponseEntity.ok(specialistService.getByUserId(userID));
        } catch (UserIsNotSpecialistException e) {
            return ResponseEntity.ok(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("test/spec/{id}")
    public List<Boolean> getTest(@PathVariable Long id) {
        return specialistService.getRawSpecialistProfileById(id).getDeadlineConfig();
    }

}
