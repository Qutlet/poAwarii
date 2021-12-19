package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.help.UserAlreadyHaveRoleException;
import com.maciejBigos.poAwarii.role.Role;
import com.maciejBigos.poAwarii.role.RoleLevel;
import com.maciejBigos.poAwarii.role.RoleService;
import com.maciejBigos.poAwarii.user.User;
import com.maciejBigos.poAwarii.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpecialistService {

    private SpecialistRepository specialistRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    public SpecialistService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    private String betterStringStrikesBack(String userDefined, String newString){
        if (newString == null){
            return userDefined;
        }
        return newString;
    }

    public SpecialistProfile addSpecialistProfile(SpecialistProfileDTO specialistProfileDTO, Authentication authentication) throws UserAlreadyHaveRoleException {
        User user = userService.findByEmail(authentication.getName());
        if (roleService.isUserHaveRole(user,RoleLevel.SPEC)) {
            throw new UserAlreadyHaveRoleException(RoleLevel.SPEC.name());
        }
        roleService.addRoleToUser(user.getId(), RoleLevel.SPEC);
        SpecialistProfile specialistProfile = new SpecialistProfile();
        specialistProfile.setUser(user);
        specialistProfile.setCategories(specialistProfileDTO.getCategories());
        specialistProfile.setCustomProfileName(specialistProfileDTO.getCustomProfileName());
        specialistProfile.setEmail(betterStringStrikesBack(user.getEmail(),specialistProfileDTO.getEmail()));
        specialistProfile.setFirstName(betterStringStrikesBack(user.getFirstName(), specialistProfileDTO.getFirstName()));
        specialistProfile.setLastName(betterStringStrikesBack(user.getLastName(), specialistProfileDTO.getLastName()));
        specialistProfile.setPhoneNumber(betterStringStrikesBack(user.getPhoneNumber(), specialistProfileDTO.getPhoneNumber()));
        return specialistRepository.save(specialistProfile);
    }

    public SpecialistProfile getSpecialistProfileByID(Long id){
        return specialistRepository.getById(id);
    }

    public List<SpecialistProfile> getAllSpecialistProfiles(){
        return specialistRepository.findAll();
    }

    public void deleteSpecialistProfile(Long id){
        specialistRepository.deleteById(id);
    }

    public List<SpecialistProfile> getAllCategorizedSpecialistProfiles(String cat){
        List<SpecialistProfile> specialistProfileList = specialistRepository.findAll();
        Set<String> stringi = new HashSet<>(Arrays.asList(cat.split("\\+")));
        specialistProfileList.removeIf(specialistProfile -> isContainAny(specialistProfile.getCategories(), Arrays.asList(stringi.toArray())));
        return specialistProfileList;
    }

    private boolean isContainAny(List list, List list1){
        for (Object o : list) {
            for (Object o1 : list1) {
                if (o.equals(o1)){
                    return true;
                }
            }
        }
        return false;
    }

    public SpecialistProfile update(Long id,SpecialistProfileDTO specialistProfileDTO){
        SpecialistProfile specialistProfile = specialistRepository.getById(id);
        specialistProfile.setCategories(specialistProfileDTO.getCategories());
        specialistProfile.setCustomProfileName(specialistProfileDTO.getCustomProfileName());
        specialistProfile.setEmail(specialistProfileDTO.getEmail());
        specialistProfile.setFirstName(specialistProfileDTO.getFirstName());
        specialistProfile.setLastName(specialistProfileDTO.getLastName());
        specialistProfile.setPhoneNumber(specialistProfileDTO.getPhoneNumber());
        return specialistRepository.save(specialistProfile);
    }

}
