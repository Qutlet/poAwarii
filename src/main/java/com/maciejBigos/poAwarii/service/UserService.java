package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.exceptions.UserAlreadyExistException;
import com.maciejBigos.poAwarii.model.Role;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.model.User;
import com.maciejBigos.poAwarii.model.DTO.UserDto;
import com.maciejBigos.poAwarii.model.messeges.ResponseUser;
import com.maciejBigos.poAwarii.security.CustomUserDetails;
import com.maciejBigos.poAwarii.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class UserService implements UserDetailsService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseUser registerNewUser(UserDto userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return ResponseUser.builder
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();
    }

    private boolean emailExist(String email) {
        //return userRepository.findByEmail(email) != null;
        AtomicBoolean cos = new AtomicBoolean(false);
        userRepository.findAll().forEach(user -> {
            if (user.getEmail().equals(email)){
                System.out.println( cos.get());
            cos.set(true);
        }
        });
        return cos.get();
    }

    public void addRoleToUser(String userID, Role role){
        User user = userRepository.getById(userID);
        user.add(role);
        userRepository.save(user);
    }

    public void removeRoleFromUser(String userID, Role role){
        User user = userRepository.getById(userID);
        user.remove(role);
        userRepository.save(user);
    }

    public ResponseUser getUserByID(String userID){
        User user = userRepository.findById(userID).get();
        return ResponseUser.builder
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteAccount(String userID){
        userRepository.deleteById(userID);
    }

    @Deprecated
    public User makeUserSpecialist(String id){
        User user = userRepository.getById(id);
        user.add(new Role("SPEC"));
        return userRepository.save(user);
    }

    @Deprecated
    public User unmakeUserSpecialist(String userID){
        User user = userRepository.getById(userID);
        user.remove(new Role("SPEC"));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    public void addPhoto(String senderId, String filename) {
        User user = userRepository.findById(senderId).get();
        user.setPhoto(filename);
        userRepository.save(user);
    }
}
