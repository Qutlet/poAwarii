package com.maciejBigos.poAwarii.model.messeges;

import com.maciejBigos.poAwarii.model.Deadline;
import com.maciejBigos.poAwarii.model.User;

import java.util.List;

public class ResponseSpecialistProfile {

    private Long id;
    private String userId;
    private String customProfileName;
    private List<String> categories;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userPhoto;
    private List<String> photos;
    private String location;
    private String description;
    private List<Deadline> deadlineList;

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
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

    public List<Deadline> getDeadlineList() {
        return deadlineList;
    }

    public void setDeadlineList(List<Deadline> deadlineList) {
        this.deadlineList = deadlineList;
    }

    public ResponseSpecialistProfile(Long id, String userId, String customProfileName, List<String> categories, String firstName, String lastName, String email, String phoneNumber, String userPhoto, List<String> photos, String location, String description, List<Deadline> deadlineList) {
        this.id = id;
        this.userId = userId;
        this.customProfileName = customProfileName;
        this.categories = categories;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userPhoto = userPhoto;
        this.photos = photos;
        this.location = location;
        this.description = description;
        this.deadlineList = deadlineList;
    }

    public static final ResponseSpecialistProfileBuilder builder = new ResponseSpecialistProfileBuilder();

    public static class ResponseSpecialistProfileBuilder {
        private Long id;
        private String userId;
        private String customProfileName;
        private List<String> categories;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private List<String> photos;
        private String userPhoto;
        private String location;
        private String description;
        private List<Deadline> deadlineList;

        public ResponseSpecialistProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ResponseSpecialistProfileBuilder userId(User user) {
            if (user != null){
                this.userId = user.getId();
            }
            return this;
        }

        public ResponseSpecialistProfileBuilder customProfileName(String customProfileName) {
            this.customProfileName = customProfileName;
            return this;
        }

        public ResponseSpecialistProfileBuilder categories(List<String> categories) {
            this.categories = categories;
            return this;
        }

        public ResponseSpecialistProfileBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ResponseSpecialistProfileBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ResponseSpecialistProfileBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ResponseSpecialistProfileBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ResponseSpecialistProfileBuilder photos(List<String> photos) {
            this.photos = photos;
            return this;
        }

        public ResponseSpecialistProfileBuilder userPhoto(User user) {
            if (user != null){
                this.userPhoto = user.getPhoto();
            }
            return this;
        }

        public ResponseSpecialistProfileBuilder location(String location) {
            this.location = location;
            return this;
        }

        public ResponseSpecialistProfileBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ResponseSpecialistProfileBuilder deadlineList(List<Deadline> deadlineList) {
            this.deadlineList = deadlineList;
            return this;
        }

        public ResponseSpecialistProfile build(){
            return new ResponseSpecialistProfile(id,userId,customProfileName,categories,firstName,lastName,
                    email,phoneNumber,userPhoto,photos,location, description, deadlineList);
        }
    }
}
