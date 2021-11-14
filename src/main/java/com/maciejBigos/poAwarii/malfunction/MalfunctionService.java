package com.maciejBigos.poAwarii.malfunction;

import com.maciejBigos.poAwarii.role.Role;
import com.maciejBigos.poAwarii.specialist.SpecialistProfile;
import com.maciejBigos.poAwarii.specialist.SpecialistService;
import com.maciejBigos.poAwarii.user.User;
import com.maciejBigos.poAwarii.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MalfunctionService {

    private MalfunctionRepository malfunctionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    public MalfunctionService(MalfunctionRepository malfunctionRepository){
        this.malfunctionRepository = malfunctionRepository;
    }

    private String betterStringStrikesBack(String userDefined, String newString){
        if (newString == null){
            return userDefined;
        }
        return newString;
    }

    public Malfunction addMalfunction(MalfunctionDTO malfunctionDTO, User user){
        Malfunction malfunction = new Malfunction();
        malfunction.setCreator(user);
        malfunction.setName(malfunctionDTO.getName());
        malfunction.setLocation(malfunctionDTO.getLocation());
        malfunction.setDescription(malfunctionDTO.getDescription());
        malfunction.setCategories(malfunctionDTO.getCategories());
        malfunction.setPhoneNumber(betterStringStrikesBack(user.getPhoneNumber(), malfunctionDTO.getPhoneNumber()));
        malfunction.setEmail(betterStringStrikesBack(user.getEmail(), malfunctionDTO.getEmail()));
        return malfunctionRepository.save(malfunction);
    }

    public List<Malfunction> getAllMalfunctions(){
        return malfunctionRepository.findAll();
    }
    public Malfunction getMalfunction(Long id){
        return malfunctionRepository.getById(id);
    }


    public Malfunction editMalfunction(MalfunctionDTO malfunctionDTO ,Long id){
        Malfunction malfunction = malfunctionRepository.getById(id);
        malfunction.setName(malfunctionDTO.getName());
        malfunction.setLocation(malfunctionDTO.getLocation());
        malfunction.setDescription(malfunctionDTO.getDescription());
        malfunction.setCategories(malfunctionDTO.getCategories());
        malfunction.setPhoneNumber(malfunctionDTO.getPhoneNumber());
        malfunction.setEmail(malfunctionDTO.getEmail());
        return malfunctionRepository.save(malfunction);
    }

    public void deleteMalfunction(Long id){
        Malfunction malfunction = getMalfunction(id);
        malfunctionRepository.delete(malfunction);
    }

    public Malfunction addInterestedSpecialist(Long malfunctionID,  Long specialistID){ //specialista jest gotow podjac sie tego zadania
        Malfunction malfunction = getMalfunction(malfunctionID);
        SpecialistProfile specialistProfile = specialistService.getSpecialistProfileByID(specialistID);
        malfunction.addSpecialistID(specialistProfile);
        return malfunctionRepository.save(malfunction);
    }

    public Malfunction removeInterestedSpecialist(Long malfunctionID, Long specialistID){ //odrzucenie spec przez zglaszajacego lub rezygnacja specialisty
        Malfunction malfunction = getMalfunction(malfunctionID);
        SpecialistProfile specialistProfile = specialistService.getSpecialistProfileByID(specialistID);
        malfunction.remove(specialistProfile);
        return malfunctionRepository.save(malfunction);
    }

    public Malfunction choseSpecialist(Long malfunctionID,  Long specialistID){ // akceptacja po rozmowie, rozpoczecie pracy specjalisty
        Malfunction malfunction = getMalfunction(malfunctionID);
        SpecialistProfile specialistProfile = specialistService.getSpecialistProfileByID(specialistID);
        malfunction.clearInterested().setSpecialist(specialistProfile);
        return malfunctionRepository.save(malfunction);
    }

    public Malfunction workIsDone(Long malfunctionID){  //zakonczenie pracy oraz archiwizacja
        Malfunction malfunction = malfunctionRepository.getById(malfunctionID);
        malfunctionRepository.deleteById(malfunctionID);
        //todo
        //return archiveMalfunctionRepository.save(malfunction);
        return malfunction;
    }


}
