package com.example.Services;

import com.example.Communicators.ResultHolders.EventResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.ServerModel.Event;
import com.example.ServerModel.UserAccount;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by video on 2/15/2017.
 */

public class EventIDService {
    public static EventIDService SINGLETON = new EventIDService();

    private EventIDService(){};

    public EventResponse eventID(String token, String eventid){
        EventResponse er = new EventResponse();
        String username = AuthDAO.SINGLETON.getUsername(token);
        Set<Event> events = new HashSet<>();

        if (username == null){
            er.setError(true);
            er.setErrorMessage("The authorization token is invalid");
            return er;
        }

        UserAccount user = UserDAO.SINGLETON.getUser(username);

        if (user == null){
            er.setError(true);
            er.setErrorMessage("Internal server error");
            return er;
        }

        Event event = EventDAO.SINGLETON.getIDEvent(eventid, user.getPersonid());

        if (event == null){
            er.setError(true);
            er.setErrorMessage("Invalid eventid");
            return er;
        }

        events.add(event);
        er.addEvents(events);

        return er;
    }
}
