package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.exceptions.UserAlreadyHaveRoleException;
import com.maciejBigos.poAwarii.exceptions.UserIsNotSpecialistException;
import com.maciejBigos.poAwarii.model.DTO.SpecialistProfileDTO;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.model.enums.RoleLevel;
import com.maciejBigos.poAwarii.model.messeges.ResponseSpecialistProfile;
import com.maciejBigos.poAwarii.repository.SpecialistRepository;
import com.maciejBigos.poAwarii.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public ResponseSpecialistProfile addSpecialistProfile(SpecialistProfileDTO specialistProfileDTO, Authentication authentication) throws UserAlreadyHaveRoleException {
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
        specialistProfile.setLocation(specialistProfileDTO.getLocation());
        specialistProfile.setDescription(specialistProfileDTO.getDescription());
        specialistRepository.save(specialistProfile);
        return ResponseSpecialistProfile.builder
                .id(specialistProfile.getId())
                .userId(specialistProfile.getUser())
                .categories(specialistProfile.getCategories())
                .customProfileName(specialistProfile.getCustomProfileName())
                .email(specialistProfile.getEmail())
                .firstName(specialistProfile.getFirstName())
                .lastName(specialistProfile.getLastName())
                .phoneNumber(specialistProfile.getPhoneNumber())
                .photos(specialistProfile.getPhotos())
                .userPhoto(specialistProfile.getUser())
                .location(specialistProfile.getLocation())
                .description(specialistProfile.getDescription())
                .build();
    }

    public SpecialistProfile getRawSpecialistProfileById(Long id){
        return specialistRepository.findById(id).get();
    }

    public SpecialistProfile getRawSpecialistProfileByUserId(String userId){
        return specialistRepository.findByUserId(userId).get();
    }

    public ResponseSpecialistProfile getSpecialistProfileByID(Long id){
        SpecialistProfile specialistProfile = specialistRepository.findById(id).orElseThrow();
        return ResponseSpecialistProfile.builder
                .id(specialistProfile.getId())
                .userId(specialistProfile.getUser())
                .categories(specialistProfile.getCategories())
                .customProfileName(specialistProfile.getCustomProfileName())
                .email(specialistProfile.getEmail())
                .firstName(specialistProfile.getFirstName())
                .lastName(specialistProfile.getLastName())
                .phoneNumber(specialistProfile.getPhoneNumber())
                .photos(specialistProfile.getPhotos())
                .userPhoto(specialistProfile.getUser())
                .location(specialistProfile.getLocation())
                .description(specialistProfile.getDescription())
                .build();
    }

    public List<ResponseSpecialistProfile> getAllSpecialistProfiles(){
        return specialistRepository.findAll().stream().map(specialistProfile -> ResponseSpecialistProfile.builder
                .id(specialistProfile.getId())
                .userId(specialistProfile.getUser())
                .categories(specialistProfile.getCategories())
                .customProfileName(specialistProfile.getCustomProfileName())
                .email(specialistProfile.getEmail())
                .firstName(specialistProfile.getFirstName())
                .lastName(specialistProfile.getLastName())
                .phoneNumber(specialistProfile.getPhoneNumber())
                .photos(specialistProfile.getPhotos())
                .userPhoto(specialistProfile.getUser())
                .location(specialistProfile.getLocation())
                .description(specialistProfile.getDescription())
                .build()).collect(Collectors.toList());
    }

    public void deleteSpecialistProfile(Long id){
        specialistRepository.deleteById(id);
    }

    public List<ResponseSpecialistProfile> getAllCategorizedSpecialistProfiles(String cat){
        List<SpecialistProfile> specialistProfileList = specialistRepository.findAll();
        Set<String> stringi = new HashSet<>(Arrays.asList(cat.split("\\+")));
        specialistProfileList.removeIf(specialistProfile -> !isContainAny(specialistProfile.getCategories(), Arrays.asList(stringi.toArray())));
        return specialistProfileList.stream().map(specialistProfile -> ResponseSpecialistProfile.builder
                .id(specialistProfile.getId())
                .userId(specialistProfile.getUser())
                .categories(specialistProfile.getCategories())
                .customProfileName(specialistProfile.getCustomProfileName())
                .email(specialistProfile.getEmail())
                .firstName(specialistProfile.getFirstName())
                .lastName(specialistProfile.getLastName())
                .phoneNumber(specialistProfile.getPhoneNumber())
                .photos(specialistProfile.getPhotos())
                .userPhoto(specialistProfile.getUser())
                .location(specialistProfile.getLocation())
                .description(specialistProfile.getDescription())
                .build()).collect(Collectors.toList());
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

    public ResponseSpecialistProfile update(Long id,SpecialistProfileDTO specialistProfileDTO){
        SpecialistProfile specialistProfile = specialistRepository.getById(id);
        specialistProfile.setCategories(specialistProfileDTO.getCategories());
        specialistProfile.setCustomProfileName(specialistProfileDTO.getCustomProfileName());
        specialistProfile.setEmail(specialistProfileDTO.getEmail());
        specialistProfile.setFirstName(specialistProfileDTO.getFirstName());
        specialistProfile.setLastName(specialistProfileDTO.getLastName());
        specialistProfile.setPhoneNumber(specialistProfileDTO.getPhoneNumber());
        specialistProfile.setLocation(specialistProfileDTO.getLocation());
        specialistProfile.setDescription(specialistProfileDTO.getDescription());
        specialistRepository.save(specialistProfile);
        return ResponseSpecialistProfile.builder
                .id(specialistProfile.getId())
                .userId(specialistProfile.getUser())
                .categories(specialistProfile.getCategories())
                .customProfileName(specialistProfile.getCustomProfileName())
                .email(specialistProfile.getEmail())
                .firstName(specialistProfile.getFirstName())
                .lastName(specialistProfile.getLastName())
                .phoneNumber(specialistProfile.getPhoneNumber())
                .photos(specialistProfile.getPhotos())
                .userPhoto(specialistProfile.getUser())
                .location(specialistProfile.getLocation())
                .description(specialistProfile.getDescription())
                .build();
    }

    public void addPhoto(String senderId, String filename) {
        SpecialistProfile specialistProfile = specialistRepository.findByUserId(senderId).get();
        specialistProfile.getPhotos().add(filename);
        specialistRepository.save(specialistProfile);
    }

    public ResponseSpecialistProfile getByUserId(String userId) throws UserIsNotSpecialistException {
        try {
            return specialistRepository.findByUserId(userId).map(specialistProfile -> ResponseSpecialistProfile.builder
                    .id(specialistProfile.getId())
                    .userId(specialistProfile.getUser())
                    .categories(specialistProfile.getCategories())
                    .customProfileName(specialistProfile.getCustomProfileName())
                    .email(specialistProfile.getEmail())
                    .firstName(specialistProfile.getFirstName())
                    .lastName(specialistProfile.getLastName())
                    .phoneNumber(specialistProfile.getPhoneNumber())
                    .photos(specialistProfile.getPhotos())
                    .userPhoto(specialistProfile.getUser())
                    .location(specialistProfile.getLocation())
                    .description(specialistProfile.getDescription())
                    .build()).get();
        } catch (NoSuchElementException e) {
            throw new UserIsNotSpecialistException(userId);
        }

    }
}
