package com.example.video.familymapclient.Activities.Main.LoginFragment;

import com.example.video.familymapclient.Communication.RequestHolders.LoginRequest;
import com.example.video.familymapclient.Communication.RequestHolders.RegisterRequest;
import com.example.video.familymapclient.Model.UserInfo;

class LoginModel {
    private String mServerHost;
    private String mServerPort;
    private RegisterRequest mRegisterRequest;
    private LoginRequest mLoginRequest;
    private UserInfo mUserInfo;
    private boolean mSuccess;

    LoginModel(){
        mServerHost = null;
        mServerPort = null;
        mRegisterRequest = new RegisterRequest();
        mLoginRequest = new LoginRequest();
        mUserInfo = new UserInfo();
        mSuccess = false;
    }

    String getServerHost() {
        return mServerHost;
    }

    void setServerHost(String serverHost) {
        mServerHost = serverHost;
    }

    String getServerPort() {
        return mServerPort;
    }

    void setServerPort(String serverPort) {
        mServerPort = serverPort;
    }

    RegisterRequest getRegisterRequest() {
        return mRegisterRequest;
    }

    LoginRequest getLoginRequest() {
        return mLoginRequest;
    }

    UserInfo getUserInfo() {
        return mUserInfo;
    }

    void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }

    boolean isSuccess() {
        return mSuccess;
    }

    void setSuccess(boolean success) {
        mSuccess = success;
    }
}
