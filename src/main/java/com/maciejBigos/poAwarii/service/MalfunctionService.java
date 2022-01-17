package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.model.DTO.MalfunctionDTO;
import com.maciejBigos.poAwarii.model.Malfunction;
import com.maciejBigos.poAwarii.model.enums.MalfunctionStatus;
import com.maciejBigos.poAwarii.model.messeges.ResponseMalfunction;
import com.maciejBigos.poAwarii.repository.MalfunctionRepository;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    public ResponseMalfunction addMalfunction(MalfunctionDTO malfunctionDTO, User user){
        Malfunction malfunction = new Malfunction();
        malfunction.setCreator(user);
        malfunction.setName(malfunctionDTO.getName());
        malfunction.setLocation(malfunctionDTO.getLocation());
        malfunction.setDescription(malfunctionDTO.getDescription());
        malfunction.setCategories(malfunctionDTO.getCategories());
        malfunction.setPhoneNumber(betterStringStrikesBack(user.getPhoneNumber(), malfunctionDTO.getPhoneNumber()));
        malfunction.setEmail(betterStringStrikesBack(user.getEmail(), malfunctionDTO.getEmail()));
        malfunctionRepository.save(malfunction);
        return ResponseMalfunction
                .builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }

    public List<ResponseMalfunction> getAllMalfunctions(){
        List<Malfunction> malfunctionList = malfunctionRepository.findAll().stream().filter(malfunction -> malfunction.getStatus() == MalfunctionStatus.PENDING).collect(Collectors.toList());
        return malfunctionList.stream().map(malfunction -> ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build()).collect(Collectors.toList());
    }
    public ResponseMalfunction getMalfunction(Long id){
        Malfunction malfunction = malfunctionRepository.findById(id).orElseThrow();
        return  ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }


    public ResponseMalfunction editMalfunction(MalfunctionDTO malfunctionDTO ,Long id){
        Malfunction malfunction = malfunctionRepository.findById(id).get();
        if (malfunction.getStatus() != MalfunctionStatus.PENDING) {
            //todo uneditable object
            //throw new
        }
        malfunction.setName(malfunctionDTO.getName());
        malfunction.setLocation(malfunctionDTO.getLocation());
        malfunction.setDescription(malfunctionDTO.getDescription());
        malfunction.setCategories(malfunctionDTO.getCategories());
        malfunction.setPhoneNumber(malfunctionDTO.getPhoneNumber());
        malfunction.setEmail(malfunctionDTO.getEmail());
        malfunctionRepository.save(malfunction);
        return  ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }

    public void deleteMalfunction(Long id){
        malfunctionRepository.deleteById(id);
    }

    public ResponseMalfunction addInterestedSpecialist(Long malfunctionID,  String userId){ //specialista jest gotow podjac sie tego zadania
        Malfunction malfunction = malfunctionRepository.findById(malfunctionID).get();
        if (malfunction.getStatus() != MalfunctionStatus.PENDING) {
            //todo uneditable object
            //throw new
        }
        SpecialistProfile specialistProfile = specialistService.getRawSpecialistProfileByUserId(userId);
        malfunction.addSpecialistID(specialistProfile.getId());
        malfunctionRepository.save(malfunction);
        return  ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }

    public ResponseMalfunction removeInterestedSpecialist(Long malfunctionID, Long specialistID){ //odrzucenie spec przez zglaszajacego lub rezygnacja specialisty
        Malfunction malfunction = malfunctionRepository.findById(malfunctionID).get();
        if (malfunction.getStatus() != MalfunctionStatus.PENDING) {
            //todo uneditable object
            //throw new
        }
        SpecialistProfile specialistProfile = specialistService.getRawSpecialistProfileById(specialistID);
        malfunction.remove(specialistProfile.getId());
        malfunctionRepository.save(malfunction);
        return  ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }

    public ResponseMalfunction choseSpecialist(Long malfunctionID,  Long specialistID){ // akceptacja po rozmowie, rozpoczecie pracy specjalisty
        Malfunction malfunction = malfunctionRepository.findById(malfunctionID).get();
        if (malfunction.getStatus() != MalfunctionStatus.PENDING) {
            //todo uneditable object
            //throw new
        }
        SpecialistProfile specialistProfile = specialistService.getRawSpecialistProfileById(specialistID);
        malfunction.clearInterested().setSpecialist(specialistProfile);
        malfunction.setStatus(MalfunctionStatus.IN_WORK);
        malfunctionRepository.save(malfunction);
        return  ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }

    public ResponseMalfunction workIsDone(Long malfunctionID){  //zakonczenie pracy oraz archiwizacja
        Malfunction malfunction = malfunctionRepository.findById(malfunctionID).get();
        if (malfunction.getStatus() == MalfunctionStatus.ENDED) {
            //todo uneditable object
            //throw new
        }
        malfunction.setStatus(MalfunctionStatus.ENDED);
        //malfunctionRepository.deleteById(malfunctionID);
        //todo
        //return archiveMalfunctionRepository.save(malfunction);
        return  ResponseMalfunction.builder
                .id(malfunction.getId())
                .creatorId(malfunction.getCreator())
                .name(malfunction.getName())
                .location(malfunction.getLocation())
                .description(malfunction.getDescription())
                .categories(malfunction.getCategories())
                .phoneNumber(malfunction.getPhoneNumber())
                .email(malfunction.getEmail())
                .specialistId(malfunction.getSpecialist())
                .specialistIds(malfunction.getSpecialists())
                .status(malfunction.getStatus())
                .build();
    }

    public List<ResponseMalfunction> getAllUserMalfunction(String userId) {
        return malfunctionRepository.findAll().stream().map(malfunction -> ResponseMalfunction.builder
                        .id(malfunction.getId())
                        .creatorId(malfunction.getCreator())
                        .name(malfunction.getName())
                        .location(malfunction.getLocation())
                        .description(malfunction.getDescription())
                        .categories(malfunction.getCategories())
                        .phoneNumber(malfunction.getPhoneNumber())
                        .email(malfunction.getEmail())
                        .specialistId(malfunction.getSpecialist())
                        .specialistIds(malfunction.getSpecialists())
                        .status(malfunction.getStatus())
                    .build())
                .filter(responseMalfunction -> responseMalfunction.getCreatorId()
                        .equals(userId))
                .collect(Collectors.toList());
    }

}
