package com.maciejBigos.poAwarii.role;

import com.maciejBigos.poAwarii.user.User;
import com.maciejBigos.poAwarii.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    private final UserService userService;

    public RoleService(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    public void addRoleToUser(String userID, RoleLevel roleLevel){
        Role role = null;
        switch (roleLevel){
            case ADMIN:
                role = roleRepository.getById(0L);
                break;
            case USER:
                role = roleRepository.getById(1L);
                break;
            case SPEC:
                role = roleRepository.getById(2L);
                break;
        }
        userService.addRoleToUser(userID, role);
    }

    public void removeRoleFromUser(String userID, RoleLevel roleLevel){
        Role role = null;
        switch (roleLevel){
            case ADMIN:
                role = roleRepository.getById(0L);
                break;
            case USER:
                role = roleRepository.getById(1L);
                break;
            case SPEC:
                role = roleRepository.getById(2L);
                break;
        }
        userService.removeRoleFromUser(userID,role);
    }

    public boolean isUserHaveRole(User user, RoleLevel roleLevel) {
        List<Role> userRoles = user.getRoles();
        return userRoles.stream().anyMatch(role -> role.getRoleName().equals(roleLevel.name()));
    }

    public void createRole(Role role){
        roleRepository.save(role);
    }

}
