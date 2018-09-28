package com.example.Services;

import com.example.Communicators.ResultHolders.EventResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.Event;
import com.example.ServerModel.UserAccount;

import java.util.Set;

/**
 * Created by video on 2/15/2017.
 */

public class EventService {
    public static EventService SINGLETON = new EventService();

    private EventService(){};

    public EventResponse events(String token){
        EventResponse er = new EventResponse();
        String username = AuthDAO.SINGLETON.getUsername(token);

        if (username == null){
            er.setError(true);
            er.setErrorMessage("The authorization token is not valid");
            return er;
        }

        UserAccount user = UserDAO.SINGLETON.getUser(username);

        if (user == null){
            er.setError(true);
            er.setErrorMessage("Internal server error");
            return er;
        }

        Set<Event> allEvents = EventDAO.SINGLETON.getAllEvents(user.getPersonid());

        if (allEvents == null){
            er.setError(true);
            er.setErrorMessage("Internal server error");
            return er;
        }

        er.addEvents(allEvents);

        return er;
    }
}
