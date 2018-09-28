package com.example.Services;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.AuthToken;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;

public class RegisterService {
    public static RegisterService SINGLETON = new RegisterService();

    private RegisterService(){};

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse registerResult = new RegisterResponse();
        if (request != null) {
            String username = request.getUsername();
            String password = request.getPassword();
            String firstName = request.getFirstName();
            String lastName = request.getLastName();
            String email = request.getEmail();
            String gender = request.getGender();

            if (username.equals("") || password.equals("") || firstName.equals("") || lastName.equals("") || email.equals("") || gender.equals("")) {
                registerResult.setError(true);
                registerResult.setErrorMessage("One of the fields was missing.");
                return registerResult;
            }

            if (!gender.equals("m") && !gender.equals("f")) {
                registerResult.setError(true);
                registerResult.setErrorMessage(registerResult.getErrorMessage() + "The gender was invalid.");
                return registerResult;
            }
            UserAccount check = UserDAO.SINGLETON.getUser(username);
            if (check != null) {
                registerResult.setError(true);
                registerResult.setErrorMessage(registerResult.getErrorMessage() + "That username is already in use.");
                return registerResult;
            }
            if (!registerResult.isError()) {
                firstName = Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1).toLowerCase();
                lastName = Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1).toLowerCase();
                UserAccount user = new UserAccount(username, password, firstName, lastName, email, gender);

                Person person = new Person(firstName, lastName, gender);
                user.setPersonid(person.getPersonID());

                if (!person.generateAncestors(4, person)) {
                    registerResult.setError(true);
                    registerResult.setErrorMessage(registerResult.getErrorMessage() + "Internal server error.");
                    return registerResult;
                }

                if (!UserDAO.SINGLETON.addUser(user)) {
                    registerResult.setError(true);
                    registerResult.setErrorMessage(registerResult.getErrorMessage() + "Internal server error.");
                    return registerResult;
                }

                AuthToken token = new AuthToken(user.getUsername());
                if (!AuthDAO.SINGLETON.addAuthToken(token)) {
                    registerResult.setError(true);
                    registerResult.setErrorMessage(registerResult.getErrorMessage() + "Internal server error.");
                    return registerResult;
                }
                if (!registerResult.isError()) {
                    registerResult.setUserName(user.getUsername());
                    registerResult.setPersonid(user.getPersonid());
                    registerResult.setAuthToken(token.getToken());
                }
            }
        }
        else{
            registerResult.setError(true);
            registerResult.setErrorMessage("There was no value passed in");
        }

        return registerResult;
    }
}
