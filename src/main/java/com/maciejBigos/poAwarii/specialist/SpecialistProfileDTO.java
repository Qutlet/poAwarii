package com.maciejBigos.poAwarii.specialist;

import com.maciejBigos.poAwarii.help.ValidEmail;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SpecialistProfileDTO {

    @NotNull
    private String customProfileName;
    @NotNull
    private List<String> categories;
    private String firstName;
    private String lastName;
    @ValidEmail
    private String email;
    private String phoneNumber;

    public SpecialistProfileDTO(String customProfileName, List<String> categories, String firstName, String lastName, String email, String phoneNumber) {
        this.customProfileName = customProfileName;
        this.categories = categories;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public SpecialistProfileDTO(String customProfileName, List<String> categories) {
        this.customProfileName = customProfileName;
        this.categories = categories;
    }

    public SpecialistProfileDTO() {
    }

    public String getCustomProfileName() {
        return customProfileName;
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setCustomProfileName(String customProfileName) {
        this.customProfileName = customProfileName;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
