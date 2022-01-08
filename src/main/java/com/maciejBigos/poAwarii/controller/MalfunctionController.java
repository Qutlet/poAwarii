package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.model.messeges.ResponseMalfunction;
import com.maciejBigos.poAwarii.service.MalfunctionService;
import com.maciejBigos.poAwarii.model.DTO.MalfunctionDTO;
import com.maciejBigos.poAwarii.model.Malfunction;
import com.maciejBigos.poAwarii.security.AuthenticationService;
import com.maciejBigos.poAwarii.model.User;
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
public class MalfunctionController {

    @Autowired
    private MalfunctionService malfunctionService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/malfunctions/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMalfunction(@RequestBody MalfunctionDTO malfunctionDTO, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        ResponseMalfunction malfunction = malfunctionService.addMalfunction(malfunctionDTO, user);
        return ResponseEntity.ok(malfunction);
    }

    @GetMapping(path = "/test/malfunctions", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseMalfunction>> getMalfunctionsTEST(Authentication authentication){
        final List<ResponseMalfunction> malfunctions = malfunctionService.getAllMalfunctions();
        return ResponseEntity.ok(malfunctions);
    }

    @GetMapping(path = "/malfunctions/all", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMalfunctions(Authentication authentication){
        if (authenticationService.isAdmin(authentication) || authenticationService.isSpec(authentication)){
            final List<ResponseMalfunction> malfunctions = malfunctionService.getAllMalfunctions();
            return ResponseEntity.ok(malfunctions);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping(path = "/malfunctions/{id}/malfunction", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMalfunction> getMalfunction(@PathVariable Long id, Authentication authentication) {
        if (authenticationService.isAdmin(authentication) || authenticationService.isSpec(authentication) || authenticationService.checkOwnershipForMalfunction(id, authentication)){
            final ResponseMalfunction malfunction = malfunctionService.getMalfunction(id);
            return ResponseEntity.ok(malfunction);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @PutMapping(path = "/malfunctions/{id}/edit", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMalfunction> editMalfunction(@RequestBody MalfunctionDTO malfunctionDTO,@PathVariable Long id, Authentication authentication) {
        if (authenticationService.isAdmin(authentication)|| authenticationService.checkOwnershipForMalfunction(id,authentication)){
            ResponseMalfunction malfunction = malfunctionService.editMalfunction(malfunctionDTO,id);
            return ResponseEntity.ok(malfunction);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }

    @DeleteMapping(path = "/malfunctions/{id}/delete", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteMalfunction(@PathVariable Long id,Authentication authentication) {
        if (authenticationService.checkOwnershipForMalfunction(id,authentication)) {
            malfunctionService.deleteMalfunction(id);
            return ResponseEntity.ok("Malfunction deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Specialist is interested in this malfunction, or user is interested this specialist
     */
    @PutMapping(path = "/malfunctions/malfunction/{malfunctionID}/specialist/{specialistID}/interested",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addInterestedSpecialist(@PathVariable Long malfunctionID, @PathVariable Long specialistID, Authentication authentication){
        if (authenticationService.isAdmin(authentication) || authenticationService.isSpec(authentication) || authenticationService.checkOwnershipForMalfunction(malfunctionID,authentication)){
            final ResponseMalfunction malfunction =  malfunctionService.addInterestedSpecialist(malfunctionID,specialistID);
            return ResponseEntity.ok(malfunction);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Specialist lost his interest in this malfunction, or user is not accepting him as executor
     */
    @PutMapping(path = "/malfunctions/malfunction/{malfunctionID}/specialist/{specialistID}/uninterested", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeInterestedSpecialist(@PathVariable Long malfunctionID, @PathVariable Long specialistID, Authentication authentication){
        if (authenticationService.isAdmin(authentication) || authenticationService.isSpec(authentication) || authenticationService.checkOwnershipForMalfunction(malfunctionID,authentication)){
            final ResponseMalfunction malfunction = malfunctionService.removeInterestedSpecialist(malfunctionID,specialistID);
            return ResponseEntity.ok(malfunction);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * User accepting specialist as an executor
     */
    @PutMapping(path = "/malfunctions/malfunction/{malfunctionID}/specialist/{specialistID}/chosen",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> chooseSpecialist(@PathVariable Long malfunctionID, @PathVariable Long specialistID, Authentication authentication){
        if (authenticationService.checkOwnershipForMalfunction(malfunctionID,authentication)){
            return ResponseEntity.ok(malfunctionService.choseSpecialist(malfunctionID, specialistID));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden action");
        }
    }

    @PutMapping(path = "/malfunctions/malfunction/{malfunctionID}/workEnded",produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> workEnded(@PathVariable Long malfunctionID, Authentication authentication){ //todo this only successful end of work, add unsuccessful
        if (authenticationService.checkOwnershipForMalfunction(malfunctionID,authentication)){
            return ResponseEntity.ok(malfunctionService.workIsDone(malfunctionID));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden action");
        }
    }

    @PostMapping(path = "/test/cos")
    public ResponseEntity<?> cos(@RequestBody String test, Authentication authentication){
        return ResponseEntity.ok(authentication.getAuthorities());
    }
}
