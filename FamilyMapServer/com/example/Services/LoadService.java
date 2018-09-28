package com.example.Services;

import com.example.Communicators.RequestHolders.LoadRequest;
import com.example.Communicators.ResultHolders.LoadResult;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.Event;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;

/**
 * Created by video on 2/15/2017.
 */

public class LoadService {
    public static LoadService SINGLETON = new LoadService();

    private LoadService(){};

    public LoadResult load(LoadRequest loadRequest){
        LoadResult lr = new LoadResult();

        if(loadRequest != null) {
            UserAccount[] users = loadRequest.getUsers();
            Person[] persons = loadRequest.getPersons();
            Event[] events = loadRequest.getEvents();

            if (persons == null || users == null || events == null) {
                lr.setError(true);
                lr.setReturnMessage("One of the arrays is empty");
                return lr;
            }

            AuthDAO.SINGLETON.clearTokenTable();
            EventDAO.SINGLETON.clearEventTable();
            PersonDAO.SINGLETON.clearPersonTable();
            UserDAO.SINGLETON.clearUserTable();

            for (int i = 0; i < users.length; i++) {
                UserDAO.SINGLETON.addUser(users[i]);
            }

            for (int i = 0; i < events.length; i++) {
                EventDAO.SINGLETON.addEvent(events[i]);
            }

            for (int i = 0; i < persons.length; i++) {
                PersonDAO.SINGLETON.addPerson(persons[i]);
            }

            lr.setReturnMessage("Successfully added " + loadRequest.getUsers().length +
                    " users, " + loadRequest.getPersons().length + " persons, and " +
                    loadRequest.getEvents().length + " events to the database.");

        }
        else {
            lr.setError(true);
            lr.setReturnMessage("There was no data passed in");
        }

        return lr;
    }
}
