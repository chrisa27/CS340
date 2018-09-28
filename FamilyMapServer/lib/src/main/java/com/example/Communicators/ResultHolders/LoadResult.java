package com.example.Communicators.ResultHolders;

public class LoadResult {
    private boolean error;
    private String returnMessage;

    public LoadResult(){
        error = false;
        returnMessage = null;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }
}
