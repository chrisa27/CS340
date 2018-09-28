package com.example.Services;

import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by video on 2/15/2017.
 */

public class PersonIDService {
    public static PersonIDService SINGLETON = new PersonIDService();

    private PersonIDService(){};

    public PersonResponse personID(String token, String personid){
        PersonResponse pr = new PersonResponse();
        String username = AuthDAO.SINGLETON.getUsername(token);
        Set<Person> people = new HashSet<>();

        if (username == null){
            pr.setError(true);
            pr.setErrorMessage("The authorization token is not valid.");
            return pr;
        }

        UserAccount user = UserDAO.SINGLETON.getUser(username);

        if (user == null){
            pr.setError(true);
            pr.setErrorMessage("Internal server error");
            return pr;
        }

        Person person = PersonDAO.SINGLETON.getPersonID(personid, user.getPersonid());

        if (person == null){
            pr.setError(true);
            pr.setErrorMessage("Invalid personID");
            return pr;
        }

        people.add(person);

        pr.addPeople(people);

        return pr;
    }
}
