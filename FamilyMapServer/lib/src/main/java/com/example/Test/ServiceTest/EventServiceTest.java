package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.EventResponse;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Event;
import com.example.ServerModel.UserAccount;
import com.example.Services.EventService;
import com.example.Services.RegisterService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by video on 3/6/2017.
 */

public class EventServiceTest {
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
        EventResponse er = EventService.SINGLETON.events("55");

        assertTrue(er.isError());
        assertEquals(er.getErrorMessage(), "The authorization token is not valid");
        assertEquals(er.getAllEvents(), null);
    }

    @Test
    public void eventTest(){
        EventResponse er = EventService.SINGLETON.events(token);

        assertTrue(!er.isError());
        assertEquals(er.getErrorMessage(), null);
        assertTrue(er.getAllEvents() != null);
        assertEquals(er.getAllEvents().length, events.size());
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
