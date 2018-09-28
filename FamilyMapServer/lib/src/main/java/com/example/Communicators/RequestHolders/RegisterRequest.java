package com.example.Communicators.RequestHolders;

public class RegisterRequest {
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    public RegisterRequest(String username,
                           String password,
                           String firstName,
                           String lastName,
                           String email,
                           String gender)
    {
        this.userName = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    @Override
    public String toString(){
        return userName + " " + password + " " + firstName + " " +
                lastName + " " + email + " " + gender;
    }
}
