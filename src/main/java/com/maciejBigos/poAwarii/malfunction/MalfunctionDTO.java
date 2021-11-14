package com.maciejBigos.poAwarii.malfunction;

import com.maciejBigos.poAwarii.help.ValidEmail;

import javax.persistence.ElementCollection;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class MalfunctionDTO {

    private String name;
    private String description;
    private List<String> categories = new ArrayList<>();
    private String location;
    private String phoneNumber;
    @ValidEmail
    private String email;

    public MalfunctionDTO() {
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
}
