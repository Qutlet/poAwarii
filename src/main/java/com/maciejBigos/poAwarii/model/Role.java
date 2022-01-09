package com.maciejBigos.poAwarii.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(updatable = false)
    @NotNull
    private String roleName;

    public Role(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {

    }

    @Override
    public String getAuthority() {
        return roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
