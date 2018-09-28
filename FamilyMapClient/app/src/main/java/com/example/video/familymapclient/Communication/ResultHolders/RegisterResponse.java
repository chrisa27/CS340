package com.example.video.familymapclient.Communication.ResultHolders;

public class RegisterResponse {
    private boolean error;
    private String authToken;
    private String userName;
    private String personid;
    private String errorMessage;

    public RegisterResponse(){
        error = false;
        errorMessage = "";
        authToken = null;
        userName = null;
        personid = null;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
