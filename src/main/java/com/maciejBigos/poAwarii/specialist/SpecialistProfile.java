package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Entity
public class SpecialistProfile {

    @SequenceGenerator(
            name = "specialistProfile_sequence",
            sequenceName = "specialistProfile_sequence",
            allocationSize = 1
    )
    @Id
    @Column(name = "specialistProfileID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "specialistProfile_sequence"
    )
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_user_id")
    private User user;
    private String customProfileName;
    @ElementCollection
    private List<String> categories = new ArrayList<>();
    private String firstName; // Same as userFirstName
    private String lastName; // Same as userLastName
    private String email; // Same as userEmail
    private String phoneNumber; // Same as userPhoneNumber

    //todo photo gallery


    public SpecialistProfile(User user, String customProfileName, List<String> categories, String firstName, String lastName, String email, String phoneNumber) {
        this.user = user;
        this.customProfileName = customProfileName;
        this.categories = categories;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public SpecialistProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCustomProfileName() {
        return customProfileName;
    }

    public void setCustomProfileName(String customProfileName) {
        this.customProfileName = customProfileName;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
