package fr.unice.model;

import java.util.Objects;



 // Contains all the information of a user

public class User {
    private String email;
    private String phoneNumber;
    private String password = null;

    public User(){}
    public User(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    public User(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMail() {
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }



    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
