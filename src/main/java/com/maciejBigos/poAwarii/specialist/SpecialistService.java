package com.maciejBigos.poAwarii.specialist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SpecialistService {

    private SpecialistRepository specialistRepository;

    @Autowired
    public SpecialistService(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    public void addSpecialistProfile(SpecialistProfile specialistProfile){
        specialistRepository.save(specialistProfile);
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
        specialistProfileList.removeIf(specialistProfile -> !specialistProfile.getCategories().contains(cat));
        return specialistProfileList;
    }

    public boolean confirmOwnershipForSpecialistProfile(Long specialistProfileID, String userName){
        return specialistRepository.getById(specialistProfileID).getEmail().equals(userName);
    }

    public void update(){

    }

}
