package com.example.Services;

import com.example.Communicators.RequestHolders.LoginRequest;
import com.example.Communicators.ResultHolders.LoginResult;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.AuthToken;
import com.example.ServerModel.UserAccount;

/**
 * Created by video on 2/15/2017.
 */

public class LoginService {
    public static LoginService SINGLETON = new LoginService();

    private LoginService(){};

    public LoginResult login(LoginRequest request){
        LoginResult lr = new LoginResult();

        if (request != null) {
            String username = request.getUsername();
            String password = request.getPassword();

            if (username.equals("") || password.equals("")) {
                lr.setError(true);
                lr.setErrorMessage("There is a value missing");
                return lr;
            }

            UserAccount user = UserDAO.SINGLETON.getUser(username);

            if (user == null) {
                lr.setError(true);
                lr.setErrorMessage("The username doesn't exist");
            } else if (!user.getPassword().equals(password)) {
                lr.setError(true);
                lr.setErrorMessage("The username and password don't match");
            } else {
                AuthToken token = new AuthToken(username);
                if (!AuthDAO.SINGLETON.addAuthToken(token)) {
                    lr.setError(true);
                    lr.setErrorMessage("Internal Server Error");
                    return lr;
                }

                lr.setUsername(username);
                lr.setAuthToken(token.getToken());
                lr.setPersonid(user.getPersonid());
            }
        }
        else{
            lr.setError(true);
            lr.setErrorMessage("There was no value passed in");
        }
        return lr;
    }
}
