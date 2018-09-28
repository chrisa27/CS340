package com.example.video.familymapclient.Communication.RequestHolders;

public class LoginRequest {
    private String userName;
    private String password;

    public LoginRequest(){
        this.userName = "";
        this.password = "";
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
}
