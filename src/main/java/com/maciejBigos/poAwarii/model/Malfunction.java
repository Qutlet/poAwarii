package com.maciejBigos.poAwarii.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Malfunction {

    @SequenceGenerator(
            name = "malfunction_sequence",
            sequenceName = "malfunction_sequence",
            allocationSize = 1
    )
    @Id
    @Column(name = "malfunctionID")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "malfunction_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private User creator;
    @ManyToOne
    @JoinColumn(name = "specialist_specialist_profile_id")
    private SpecialistProfile specialist;
    @ElementCollection
    private List<SpecialistProfile> specialists = new ArrayList<>(); // lista spec ktorzy deklaruja sie podjac zadania, oczekuja na akceptacje uzytkownika
    private String name;
    private String description;
    @ElementCollection
    private List<String> categories = new ArrayList<>();
    private String location;
    private String phoneNumber;
    private String email;

    public SpecialistProfile getSpecialist() {
        return specialist;
    }

    public User getCreator() {
        return creator;
    }

    public Malfunction() {
    }

    public Malfunction(String name) {
        this.name = name;
    }

    public Malfunction(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Malfunction(User creator, String name, String description, List<String> categories, String location, String phoneNumber, String email) {
        this.creator = creator;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


//    public Malfunction(String creatorID, String name, String description, List<String> categories, String location, String phoneNumber, String email) {
//        this.creatorID = creatorID;
//        this.name = name;
//        this.description = description;
//        this.categories = categories;
//        this.location = location;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setSpecialist(SpecialistProfile specialist) {
        this.specialist = specialist;
    }

    public List<SpecialistProfile> getSpecialists() {
        return specialists;
    }

    public void setSpecialistIDs(List<SpecialistProfile> specialists) {
        this.specialists = specialists;
    }

    public void addSpecialistID(SpecialistProfile specialistProfile) {
        specialists.add(specialistProfile);
    }

    public boolean addAll(Collection<? extends String> c) {
        return categories.addAll(c);
    }

    public Malfunction clearInterested() {
        specialists.clear();
        return this;
    }

    public void remove(Object o) {
        specialists.remove(o);
    }
}
