package com.example.video.familymapclient.Tasks;

import android.os.AsyncTask;

import com.example.video.familymapclient.Communication.ProxyServer;
import com.example.video.familymapclient.Communication.RequestHolders.RegisterRequest;
import com.example.video.familymapclient.Communication.ResultHolders.RegisterResponse;

public class RegisterTask extends AsyncTask<RegisterRequest, Void, RegisterResponse> {
    @Override
    protected RegisterResponse doInBackground(RegisterRequest... requests){
        RegisterResponse response = new RegisterResponse();

        for (RegisterRequest request : requests) {
            response = ProxyServer.SINGLETON.register(request);
        }

        return response;
    }
}
