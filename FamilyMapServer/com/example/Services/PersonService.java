package com.example.Services;

import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;

import java.util.Set;

/**
 * Created by video on 2/15/2017.
 */

public class PersonService {
    public static PersonService SINGLETON = new PersonService();

    private PersonService(){};

    public PersonResponse person(String token){
        PersonResponse pr = new PersonResponse();
        String username = AuthDAO.SINGLETON.getUsername(token);

        if (username == null){
            pr.setError(true);
            pr.setErrorMessage("The authorization token is not valid");
            return pr;
        }

        UserAccount user = UserDAO.SINGLETON.getUser(username);

        if (user == null){
            pr.setError(true);
            pr.setErrorMessage("Internal Server Error");
            return pr;
        }


        Set<Person> allPeople = PersonDAO.SINGLETON.getAllPeople(user.getPersonid());

        if (allPeople == null){
            pr.setError(true);
            pr.setErrorMessage("Internal Server Error");
            return pr;
        }

        pr.addPeople(allPeople);

        return pr;
    }
}
