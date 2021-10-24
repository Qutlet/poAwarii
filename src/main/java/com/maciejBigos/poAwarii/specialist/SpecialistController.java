package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    @PostMapping(path = "specProfile/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSpecialistProfile(@RequestBody SpecialistProfile specialistProfile){
        userService.makeUserSpecialist(specialistProfile.getUserID());
        specialistService.addSpecialistProfile(specialistProfile);
        return ResponseEntity.ok(specialistProfile);
    }

    @GetMapping(path = "specProfile/profile/{id}", produces = APPLICATION_JSON_VALUE)
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

    @DeleteMapping(path = "specProfile/delete/{id}")
    public ResponseEntity<?> deleteSpecialistProfile(@PathVariable Long id, Authentication authentication){
        if(specialistService.confirmOwnershipForSpecialistProfile(id,authentication.getName())){ //todo or is admin
            specialistService.deleteSpecialistProfile(id);
            userService.unmakeUserSpecialist(authentication.getName());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok("Forbidden action");
        }
    }
    //todo edit
}
