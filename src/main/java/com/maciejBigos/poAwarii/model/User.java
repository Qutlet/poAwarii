package com.maciejBigos.poAwarii.model;

import com.maciejBigos.poAwarii.generators.UserSequenceIdGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Users")
public class User {

    @Id
    @Column(name = "userID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @GenericGenerator(
            name = "user_sequence",
            strategy = "com.maciejBigos.poAwarii.generators.UserSequenceIdGenerator",
            parameters = {@Parameter(name = UserSequenceIdGenerator.INCREMENT_PARAM, value = "50")}
    )
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    @JoinTable(name = "Users_roles",
            joinColumns = @JoinColumn(name = "Users_userID", referencedColumnName = "userID"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String phoneNumber;

    @Column(name = "enabled")
    private boolean enabled;

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean add(Role role) {
        return roles.add(role);
    }

    public boolean remove(Object o) {
        return roles.remove(o);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", photo='" + photo + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
