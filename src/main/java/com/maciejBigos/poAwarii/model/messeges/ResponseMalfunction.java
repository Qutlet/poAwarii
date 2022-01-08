package com.maciejBigos.poAwarii.model.messeges;

import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseMalfunction {

    private Long id;
    private String creatorId;
    private Long specialistId;
    private List<Long> specialistIds;
    private String name;
    private String description;
    private List<String> categories;
    private String location;
    private String phoneNumber;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Long getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(Long specialistId) {
        this.specialistId = specialistId;
    }

    public List<Long> getSpecialistIds() {
        return specialistIds;
    }

    public void setSpecialistIds(List<Long> specialistIds) {
        this.specialistIds = specialistIds;
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

    public ResponseMalfunction(Long id, String creatorId, Long specialistId, List<Long> specialistIds, String name, String description, List<String> categories, String location, String phoneNumber, String email) {
        this.id = id;
        this.creatorId = creatorId;
        this.specialistId = specialistId;
        this.specialistIds = specialistIds;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public static final ResponseMalfunctionBuilder builder = new ResponseMalfunctionBuilder();

    public static class ResponseMalfunctionBuilder {

        private Long id;
        private String creatorId;
        private Long specialistId;
        private List<Long> specialistIds = new ArrayList<>();
        private String name;
        private String description;
        private List<String> categories = new ArrayList<>();
        private String location;
        private String phoneNumber;
        private String email;

        public ResponseMalfunctionBuilder id(Long id){
            this.id = id;
            return this;
        }

        public ResponseMalfunctionBuilder creatorId(User creator) {
            if (creator != null) {
                this.creatorId = creator.getId();
            }
            return this;
        }

        public ResponseMalfunctionBuilder specialistId(SpecialistProfile specialist) {
            if (specialist != null) {
                this.specialistId = specialist.getId();
            }
            return this;
        }

        public ResponseMalfunctionBuilder specialistIds(List<SpecialistProfile> specialists) {
            this.specialistIds = specialists.stream()
                    .map(SpecialistProfile::getId).collect(Collectors.toList());
            return this;
        }

        public ResponseMalfunctionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ResponseMalfunctionBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ResponseMalfunctionBuilder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public ResponseMalfunctionBuilder location(String location) {
            this.location = location;
            return this;
        }

        public ResponseMalfunctionBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ResponseMalfunctionBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ResponseMalfunction build() {
            return new ResponseMalfunction(id,
                    creatorId,
                    specialistId,
                    specialistIds,
                    name,
                    description,
                    categories,
                    location,
                    phoneNumber,
                    email);
        }
    }

}
