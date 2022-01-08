package com.maciejBigos.poAwarii.security;

import com.maciejBigos.poAwarii.repository.MalfunctionRepository;
import com.maciejBigos.poAwarii.model.Role;
import com.maciejBigos.poAwarii.model.enums.RoleLevel;
import com.maciejBigos.poAwarii.repository.SpecialistRepository;
import com.maciejBigos.poAwarii.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthenticationService {

    private final SpecialistRepository specialistRepository;

    private final MalfunctionRepository malfunctionRepository;

    private final UserRepository userRepository;

    public AuthenticationService(SpecialistRepository specialistRepository, MalfunctionRepository malfunctionRepository, UserRepository userRepository) {
        this.specialistRepository = specialistRepository;
        this.malfunctionRepository = malfunctionRepository;
        this.userRepository = userRepository;
    }

    public boolean isAdmin(Authentication loggedUser){
        List<Role> roles = new ArrayList<Role>((Collection<? extends Role>) loggedUser.getAuthorities());
        return roles.stream().anyMatch(role -> role.getRoleName().equals(RoleLevel.ADMIN.name()));
    }

    public boolean checkOwnershipForMalfunction(Long malfunctionID, Authentication loggedUser){
        return malfunctionRepository.getById(malfunctionID).getCreator().getEmail().equals(loggedUser.getName());
    }

    public boolean confirmOwnershipForSpecialistProfile(Long specialistProfileID, Authentication loggedUser){
        return specialistRepository.getById(specialistProfileID).getEmail().equals(loggedUser.getName());
    }

    public boolean isSpec(Authentication loggedUser){
        List<Role> roles = new ArrayList<Role>((Collection<? extends Role>) loggedUser.getAuthorities());
        return roles.stream().anyMatch(role -> role.getRoleName().equals(RoleLevel.SPEC.name()));
    }

    public boolean isAccountOwner(String userID ,Authentication loggedUser){
        return userRepository.getById(userID).getEmail().equals(loggedUser.getName());
    }

}
