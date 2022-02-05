package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.exceptions.UserAlreadyHaveRoleException;
import com.maciejBigos.poAwarii.exceptions.UserIsNotSpecialistException;
import com.maciejBigos.poAwarii.model.DTO.SpecialistProfileDTO;
import com.maciejBigos.poAwarii.model.Deadline;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.model.enums.RoleLevel;
import com.maciejBigos.poAwarii.model.messeges.ResponseSpecialistProfile;
import com.maciejBigos.poAwarii.repository.DeadlineRepository;
import com.maciejBigos.poAwarii.repository.SpecialistRepository;
import com.maciejBigos.poAwarii.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpecialistService {

    private static final Logger logger = LoggerFactory.getLogger(SpecialistService.class);

    private SpecialistRepository specialistRepository;
    private DeadlineRepository deadlineRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    public SpecialistService(SpecialistRepository specialistRepository, DeadlineRepository deadlineRepository) {
        this.specialistRepository = specialistRepository;
        this.deadlineRepository = deadlineRepository;
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
        for (int i = 0; i < 14; i++) {
            System.out.println("i = " + i);
            Long savedId = makeDeadline(specialistProfileDTO.getDeadlinesDayUsage().get(i % 7), i);
            specialistProfile.add(savedId);
        }
        specialistProfile.setDeadlineConfig(specialistProfileDTO.getDeadlinesDayUsage());
        specialistRepository.save(specialistProfile);
        List<Deadline> deadlineList = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
        deadlineList.sort(Comparator.comparing(Deadline::getDate));
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
                .deadlineList(deadlineList)
                .build();
    }

    public SpecialistProfile getRawSpecialistProfileById(Long id){
        return specialistRepository.findById(id).get();
    }

    private Long makeDeadline(boolean usable, int i) {
        Deadline d = deadlineRepository.save(new Deadline(LocalDateTime.now().plusDays(i),true, usable));
        return d.getId();
    }

    public void takeDeadline(Long deadlineId, Long malfunctionId) {
        Deadline tmp = deadlineRepository.findById(deadlineId).get();
        tmp.setFree(false);
        tmp.setMalfunctionId(malfunctionId);
        deadlineRepository.save(tmp);
    }

    public void freeDeadline(Long malfunctionId) {
        try {
            Deadline tmp = deadlineRepository.findByMalfunctionId(malfunctionId).get();
            tmp.setFree(true);
            deadlineRepository.save(tmp);
        } catch (NoSuchElementException e) {
            logger.warn(String.format("Deadline for malfunction %s not exists", malfunctionId));
        }

    }

    public SpecialistProfile getRawSpecialistProfileByUserId(String userId){
        return specialistRepository.findByUserId(userId).get();
    }

    public SpecialistProfile getIdByDeadlineId(Long dId) {
        return specialistRepository.findByDeadlineIdsContainDeadlineId(Collections.singletonList(dId));
    }

    public ResponseSpecialistProfile getSpecialistProfileByID(Long id){
        SpecialistProfile specialistProfile = specialistRepository.findById(id).orElseThrow();
        List<Deadline> deadlineList = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
        deadlineList.sort(Comparator.comparing(Deadline::getDate));
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
                .deadlineList(deadlineList)
                .build();
    }

    public List<ResponseSpecialistProfile> getAllSpecialistProfiles(){
        return specialistRepository.findAll().stream().map(specialistProfile -> {
            List<Deadline> deadlineList = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
            deadlineList.sort(Comparator.comparing(Deadline::getDate));
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
                            .deadlineList(deadlineList)
                            .build();}
                        ).collect(Collectors.toList());
    }

    public void deleteSpecialistProfile(Long id){
        specialistRepository.deleteById(id);
    }

    public List<ResponseSpecialistProfile> getAllCategorizedSpecialistProfiles(String cat){
        List<SpecialistProfile> specialistProfileList = specialistRepository.findAll();
        Set<String> stringi = new HashSet<>(Arrays.asList(cat.split("\\+")));
        specialistProfileList.removeIf(specialistProfile -> !isContainAny(specialistProfile.getCategories(), Arrays.asList(stringi.toArray())));
        return specialistProfileList.stream().map(specialistProfile -> {
            List<Deadline> deadlineList = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
            deadlineList.sort(Comparator.comparing(Deadline::getDate));
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
                    .deadlineList(deadlineList)
                    .build();
        }).collect(Collectors.toList());
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
        List<Deadline> deadlineList = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
        deadlineList.sort(Comparator.comparing(Deadline::getDate));
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
                .deadlineList(deadlineList)
                .build();
    }

    public void addPhoto(String senderId, String filename) {
        SpecialistProfile specialistProfile = specialistRepository.findByUserId(senderId).get();
        specialistProfile.getPhotos().add(filename);
        specialistRepository.save(specialistProfile);
    }

    public ResponseSpecialistProfile getByUserId(String userId) throws UserIsNotSpecialistException {
        try {
            return specialistRepository.findByUserId(userId).map(specialistProfile -> {
                List<Deadline> deadlineList = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
                deadlineList.sort(Comparator.comparing(Deadline::getDate));
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
                        .deadlineList(deadlineList)
                        .build();
            }).get();
        } catch (NoSuchElementException e) {
            throw new UserIsNotSpecialistException(userId);
        }

    }
}
