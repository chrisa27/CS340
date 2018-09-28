package com.example.Services;

import com.example.Communicators.ResultHolders.FillResult;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.Event;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;

import java.util.Set;

/**
 * Created by video on 2/15/2017.
 */

public class FillService {
    public static FillService SINGLETON = new FillService();

    private FillService(){};

    public FillResult fill(String username, int generations){
        FillResult fr = new FillResult();

        if (generations < 0){
            fr.setError(true);
            fr.setReturnMessage("Invalid number of generations");
            return fr;
        }

        if (username == null){
            fr.setError(true);
            fr.setReturnMessage("The username is missing");
            return fr;
        }

        UserAccount user = UserDAO.SINGLETON.getUser(username);

        if (user == null){
            fr.setError(true);
            fr.setReturnMessage("Invalid username");
            return fr;
        }

        Person person = PersonDAO.SINGLETON.getPerson(user.getPersonid());

        EventDAO.SINGLETON.deleteUserInfo(user.getPersonid());
        PersonDAO.SINGLETON.deleteUserInfo(user.getPersonid());

        person.generateEvents(person.getPersonID(), person.getDescendentID(), 0);
        person.generateAncestors(generations, person);

        Set<Event> events = EventDAO.SINGLETON.getAllEvents(person.getDescendentID());
        Set<Person> people = PersonDAO.SINGLETON.getAllPeople(person.getDescendentID());

        fr.setReturnMessage("Successfully added " + people.size() + " persons, and " + events.size() + " events to the database.");

        return fr;
    }

}
