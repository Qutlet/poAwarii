package com.maciejBigos.poAwarii.specialist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void update(){

    }

}
