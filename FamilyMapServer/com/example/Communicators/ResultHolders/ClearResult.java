package com.example.Communicators.ResultHolders;

public class ClearResult {
    private boolean error;
    private String message;

    public ClearResult(){
        error = false;
        message = "Clear succeeded.";
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage(){ return message; }

    public void setMessage(String message){ this.message = message; }
}
