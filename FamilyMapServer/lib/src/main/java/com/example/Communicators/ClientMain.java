package com.example.Communicators;

import com.example.Communicators.RequestHolders.LoginRequest;
import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.LoginResult;
import com.example.Communicators.ResultHolders.RegisterResponse;

/**
 * Created by video on 2/27/2017.
 */

public class ClientMain {
    public static void main(String[] args){
        RegisterResponse response = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "chrisarnold27@gmail.com", "m"));
        System.out.println(response.getUserName() + " " + response.getAuthToken() + " " + response.getPersonid());

        LoginResult loginResult = ProxyServer.SINGLETON.login(new LoginRequest("Chrisa27", "pass"));
        System.out.println(loginResult.getAuthToken() + " " + loginResult.getUsername() + " " + loginResult.getPersonid());

    }
}
