package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.exceptions.UserAlreadyExistException;
import com.maciejBigos.poAwarii.model.Role;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.model.User;
import com.maciejBigos.poAwarii.model.DTO.UserDto;
import com.maciejBigos.poAwarii.model.messeges.ResponseMessage;
import com.maciejBigos.poAwarii.model.messeges.ResponseUser;
import com.maciejBigos.poAwarii.security.CustomUserDetails;
import com.maciejBigos.poAwarii.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    public static final String NO_USER_FOUND = "NO USER FOUND FOR ID: ";
    public static final String SUCCESS = "OPERATION FINISHED SUCCESSFULLY";

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

    public List<ResponseUser> getAllUsers() {
       return userRepository.findAll().stream().map(user -> ResponseUser.builder.id(user.getId())
               .firstName(user.getFirstName())
               .lastName(user.getLastName())
               .phoneNumber(user.getPhoneNumber())
               .email(user.getEmail())
               .photo(user.getPhoto()).build()).collect(Collectors.toList());
    }

    public List<String> getUserRoles(String id) {
        return userRepository.findById(id).get().getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
    }

    public ResponseUser getMe(String name) {
        User user = userRepository.findByEmail(name);
        return ResponseUser.builder
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .build();
    }

    public ResponseMessage editUser(String userId, UserDto userDto) {
        try {
            User user = userRepository.findById(userId).get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
            return new ResponseMessage(SUCCESS);
        } catch (NoSuchElementException elementException) {
            return new ResponseMessage(NO_USER_FOUND + userId);
        }
    }

    public ResponseMessage changePassword(String userId, String password) {
        try {
            User user = userRepository.findById(userId).get();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return new ResponseMessage(SUCCESS);
        } catch (NoSuchElementException elementException) {
            return new ResponseMessage(NO_USER_FOUND + userId);
        }
    }

    public int getRoleLevel(String userId) {
        User user = userRepository.findById(userId).get();
        List<Long> list = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        if (list.contains(0L)) {
            return 0;
        }
        if (list.contains(2L)) {
            return 2;
        }
        return 1;
    }
}
