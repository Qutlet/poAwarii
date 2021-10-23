package com.maciejBigos.poAwarii.user;

import com.maciejBigos.poAwarii.help.UserSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class User {

    @Id
    @Column(name = "userID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @GenericGenerator(
            name = "user_sequence",
            strategy = "com.maciejBigos.poAwarii.help.UserSequenceIdGenerator",
            parameters = {@Parameter(name = UserSequenceIdGenerator.INCREMENT_PARAM, value = "50")}
    )
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private String phoneNumber;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
        this.enabled = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> rolesNames) {
        this.roles = rolesNames;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
