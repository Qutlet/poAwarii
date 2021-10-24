package com.maciejBigos.poAwarii.malfunction;

import com.maciejBigos.poAwarii.user.User;

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
    //todo learn how to do it ¯\_(ツ)_/¯
//    @ManyToOne
//    @MapsId
//    @JoinColumn(name = "userID",nullable = false)
//    private User creator;
//    @ManyToOne
//    @MapsId
//    @JoinColumn(name = "userID", nullable = true)
//    private User specialist;
    private String creatorID;
    private String specialistID;
    @ElementCollection
    private List<String> specialistIDs = new ArrayList<>(); // lista spec ktorzy deklaruja sie podjac zadania, oczekuja na akceptacje uzytkownika
    private String name;
    private String description;
    @ElementCollection
    private List<String> categories = new ArrayList<>();
    private String location;
    private String phoneNumber;
    private String email;

    public Malfunction() {
    }

    public Malfunction(String name) {
        this.name = name;
    }

    public Malfunction(Long id, String name) {
        this.id = id;
        this.name = name;
    }

//    public Malfunction(User creator, String name, String description, List<String> categories, String location, String phoneNumber, String email) {
//        this.creator = creator;
//        this.name = name;
//        this.description = description;
//        this.categories = categories;
//        this.location = location;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//    }


    public Malfunction(String creatorID, String name, String description, List<String> categories, String location, String phoneNumber, String email) {
        this.creatorID = creatorID;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

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

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getSpecialistID() {
        return specialistID;
    }

    public void setSpecialistID(String specialistID) {
        this.specialistID = specialistID;
    }

    public List<String> getSpecialistIDs() {
        return specialistIDs;
    }

    public void setSpecialistIDs(List<String> specialistIDs) {
        this.specialistIDs = specialistIDs;
    }

    public boolean addSpecialistID(String aLong) {
        return specialistIDs.add(aLong);
    }

    public boolean addAll(Collection<? extends String> c) {
        return categories.addAll(c);
    }

    public Malfunction clearInterested() {
        specialistIDs.clear();
        return this;
    }

    public boolean remove(Object o) {
        return specialistIDs.remove(o);
    }
}
