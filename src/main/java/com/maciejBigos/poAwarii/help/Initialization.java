package com.maciejBigos.poAwarii.help;

import com.maciejBigos.poAwarii.role.RoleLevel;
import com.maciejBigos.poAwarii.user.RegistrationController;
import com.maciejBigos.poAwarii.user.UserDto;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Component
public class Initialization {

    private final RegistrationController registrationController;

    public Initialization(RegistrationController registrationController) {
        this.registrationController = registrationController;
    }

    @PostConstruct
    public void init(){
        //UserDto userDto = new UserDto("Administrator","PoAwarii","dupa8","dupa8","maciej.bigos@hycom.pl");
        //registrationController.register(userDto);
    }

}
