package com.maciejBigos.poAwarii.init;

import com.maciejBigos.poAwarii.controller.RegistrationController;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Initialization {

    private final RegistrationController registrationController;

    public Initialization(RegistrationController registrationController) {
        this.registrationController = registrationController;
    }

    @PostConstruct
    public void init(){

    }

}
