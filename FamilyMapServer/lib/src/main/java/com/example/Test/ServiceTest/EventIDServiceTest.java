package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.EventResponse;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Event;
import com.example.ServerModel.UserAccount;
import com.example.Services.EventIDService;
import com.example.Services.RegisterService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by video on 3/6/2017.
 */

public class EventIDServiceTest {
    private static Server server;
    private static String token;
    private static UserAccount user;
    private static Set<Event> events;

    @BeforeClass
    public static void initialize(){
        server = new Server();
        server.clearInitialize();

        RegisterResponse rs = RegisterService.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));
        token = rs.getAuthToken();
        user = UserDAO.SINGLETON.getUser("Chrisa27");
        events = EventDAO.SINGLETON.getAllEvents(user.getPersonid());
    }

    @Test
    public void invalidToken(){
        Iterator<Event> it = events.iterator();
        EventResponse test = EventIDService.SINGLETON.eventID("55", it.next().getEventID());

        assertTrue(test.isError());
        assertEquals(test.getErrorMessage(), "The authorization token is invalid");
        assertEquals(test.getAllEvents(), null);
    }

    @Test
    public void invalidID(){
        EventResponse test = EventIDService.SINGLETON.eventID(token, "66");

        assertTrue(test.isError());
        assertEquals(test.getErrorMessage(), "Invalid eventid");
        assertEquals(test.getAllEvents(), null);
    }

    @Test
    public void idTest(){
        Iterator<Event> it = events.iterator();
        Event event = it.next();
        EventResponse test = EventIDService.SINGLETON.eventID(token, event.getEventID());

        assertTrue(!test.isError());
        assertEquals(test.getErrorMessage(), null);
        assertEquals(test.getAllEvents()[0], EventDAO.SINGLETON.getEvent(event.getEventID()));
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
