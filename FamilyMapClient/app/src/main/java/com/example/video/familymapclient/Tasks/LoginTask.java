package com.example.video.familymapclient.Tasks;

import android.os.AsyncTask;

import com.example.video.familymapclient.Communication.ProxyServer;
import com.example.video.familymapclient.Communication.RequestHolders.LoginRequest;
import com.example.video.familymapclient.Communication.ResultHolders.LoginResult;

public class LoginTask extends AsyncTask <LoginRequest, Void, LoginResult> {
    @Override
    protected LoginResult doInBackground(LoginRequest... requests){
        LoginResult result = null;

        for (LoginRequest request : requests){
            result = ProxyServer.SINGLETON.login(request);
        }

        return result;
    }
}
