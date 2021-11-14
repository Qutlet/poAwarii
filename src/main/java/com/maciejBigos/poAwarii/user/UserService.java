package com.maciejBigos.poAwarii.user;

import com.maciejBigos.poAwarii.help.UserAlreadyExistException;
import com.maciejBigos.poAwarii.role.Role;
import com.maciejBigos.poAwarii.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    public User registerNewUser(UserDto userDto) throws UserAlreadyExistException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
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

    public User getUserByID(String userID){
        return userRepository.getById(userID);
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
}
