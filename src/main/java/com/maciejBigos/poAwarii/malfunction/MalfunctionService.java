package com.maciejBigos.poAwarii.malfunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MalfunctionService {

    private MalfunctionRepository malfunctionRepository;

    @Autowired
    public MalfunctionService(MalfunctionRepository malfunctionRepository){
        this.malfunctionRepository = malfunctionRepository;
    }

    public void addMalfunction(Malfunction malfunction){
        malfunctionRepository.save(malfunction);
    }

    public List<Malfunction> getAllMalfunctions(){
        return malfunctionRepository.findAll();
    }
    public Malfunction getMalfunction(Long id){
        return malfunctionRepository.getById(id);
    }

    public void editMalfunction(Long id){ //todo emm cos tu nie styka xd
        Malfunction malfunction = getMalfunction(id);
        malfunctionRepository.save(malfunction);
    }

    public void deleteMalfunction(Long id){ //todo check ownership
        Malfunction malfunction = getMalfunction(id);
        malfunctionRepository.delete(malfunction);
    }

    public Malfunction addInterestedSpecialist(Long malfunctionID,String specialistID){
        Malfunction malfunction = getMalfunction(malfunctionID);
        malfunction.addSpecialistID(specialistID);
        return malfunctionRepository.save(malfunction);
    }

    public Malfunction removeInterestedSpecialist(Long malfunctionID, String specialistID){
        Malfunction malfunction = getMalfunction(malfunctionID);
        malfunction.remove(specialistID);
        return malfunctionRepository.save(malfunction);
    }

    public Malfunction choseSpecialist(Long malfunctionID, String specialistID){
        Malfunction malfunction = getMalfunction(malfunctionID);
        malfunction.clearInterested().setSpecialistID(specialistID);
        return malfunctionRepository.save(malfunction);

    }

}
