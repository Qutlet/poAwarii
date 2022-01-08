package com.maciejBigos.poAwarii.model.messeges;

public class ResponseUser {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public ResponseUser(String id, String firstName, String lastName, String email, String phoneNumber, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public static ResponseUserBuilder builder = new ResponseUserBuilder();

    public static class ResponseUserBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String photo;

        public ResponseUserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ResponseUserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ResponseUserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ResponseUserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ResponseUserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ResponseUserBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }

        public ResponseUser build() {
            return new ResponseUser(id,firstName,lastName,email,phoneNumber,photo);
        }
    }
}
