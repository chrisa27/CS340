package com.example.video.familymapclient.Communication.ResultHolders;

public class LoginResult {
    private boolean error;
    private String authToken;
    private String username;
    private String personid;
    private String errorMessage;

    public LoginResult(){
        error = false;
        authToken = null;
        username = null;
        personid = null;
        errorMessage = null;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
