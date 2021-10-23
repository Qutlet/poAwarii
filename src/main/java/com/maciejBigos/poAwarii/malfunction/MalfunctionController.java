package com.maciejBigos.poAwarii.malfunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class MalfunctionController {

    @Autowired
    private MalfunctionService malfunctionService;

    @PostMapping(path = "/malfunctions", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity addMalfunction(@RequestBody Malfunction malfunction) {

        malfunctionService.addMalfunction(malfunction);
        return ResponseEntity.ok("Malfunction created successfully");
    }

    @GetMapping(path = "/malfunctions", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getMalfunctions(){
        final List<Malfunction> malfunctions = malfunctionService.getAllMalfunctions();
        return ResponseEntity.ok(malfunctions);
    }

    @GetMapping(path = "/malfunctions/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getMalfunction(@PathVariable Long id) {
        final Malfunction malfunction = malfunctionService.getMalfunction(id);
        return ResponseEntity.ok(malfunction);
    }

    @PutMapping(path = "/malfunctions/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity editMalfunction(@PathVariable Long id) {
        malfunctionService.editMalfunction(id);
        return ResponseEntity.ok("Malfunction edited successfully");
    }

    @DeleteMapping(path = "/malfunctions/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteMalfunction(@PathVariable Long id){
        malfunctionService.deleteMalfunction(id);
        return ResponseEntity.ok("Malfunction deleted successfully");
    }
}
