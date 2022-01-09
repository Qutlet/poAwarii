package com.maciejBigos.poAwarii.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "specialist_profile")
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
    @ElementCollection
    private List<String> photos = new ArrayList<>();
    private String location;
    private String description;

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public SpecialistProfile(User user, String customProfileName, List<String> categories, String firstName, String lastName, String email, String phoneNumber, String location, String description) {
        this.user = user;
        this.customProfileName = customProfileName;
        this.categories = categories;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.description = description;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
