package com.maciejBigos.poAwarii.security;

import com.maciejBigos.poAwarii.model.Role;
import com.maciejBigos.poAwarii.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       // final Set<Role> roles = new HashSet<>();
        final Collection<Role> userRoles = this.user.getRoles();
        if (userRoles.size() == 0){
            userRoles.add(new Role("USER"));
        }
//        else {
//            userRoles.forEach(roleName -> roles.add(new Role(roleName)));
//        }
        return userRoles;
    }

    public String getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
