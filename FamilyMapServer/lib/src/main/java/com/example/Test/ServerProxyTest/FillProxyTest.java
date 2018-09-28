package com.example.Test.ServerProxyTest;

import com.example.Communicators.ProxyServer;
import com.example.Communicators.ResultHolders.FillResult;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Event;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;
import com.example.Test.TestDriver;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/9/2017.
 */

public class FillProxyTest {
    private static Server server;
    private static UserAccount user;

    @BeforeClass
    public static void initialize(){
        server = new Server();
        server.clearInitialize();
        server.main(TestDriver.getArgs());

        user = new UserAccount("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        Person person = new Person("Chris", "Arnold", "m");
        user.setPersonid(person.getPersonID());
        UserDAO.SINGLETON.addUser(user);
        PersonDAO.SINGLETON.addPerson(person);
    }

    @Test
    public void zeroGenerations(){
        FillResult result;
        result = ProxyServer.SINGLETON.fill("Chrisa27", 0);

        Set<Event> events = EventDAO.SINGLETON.getAllEvents(user.getPersonid());

        assertTrue(!result.isError());
        assertEquals(result.getReturnMessage(), "Successfully added 1 persons, and " + events.size() + " events to the database.");
    }

    @Test
    public void oneGeneration(){
        FillResult result;
        result = ProxyServer.SINGLETON.fill("Chrisa27", 1);

        Set<Event> events = EventDAO.SINGLETON.getAllEvents(user.getPersonid());
        Set<Person> people = PersonDAO.SINGLETON.getAllPeople(user.getPersonid());

        assertTrue(!result.isError());
        assertEquals(result.getReturnMessage(), "Successfully added " + people.size() + " persons, and " + events.size() + " events to the database.");
    }

    @Test
    public void fourGenerations(){
        FillResult result;
        result = ProxyServer.SINGLETON.fill("Chrisa27", 4);

        Set<Event> events = EventDAO.SINGLETON.getAllEvents(user.getPersonid());
        Set<Person> people = PersonDAO.SINGLETON.getAllPeople(user.getPersonid());

        assertTrue(!result.isError());
        assertEquals(result.getReturnMessage(), "Successfully added " + people.size() + " persons, and " + events.size() + " events to the database.");
    }

    @Test
    public void negativeGenerations(){
        FillResult result;
        result = ProxyServer.SINGLETON.fill("joe", -5);

        assertTrue(result.isError());
        assertEquals(result.getReturnMessage(), "Invalid number of generations");
    }

    @Test
    public void invalidUsername(){
        FillResult result;
        result = ProxyServer.SINGLETON.fill("joe", 0);

        assertTrue(result.isError());
        assertEquals(result.getReturnMessage(), "Invalid username");

        result = ProxyServer.SINGLETON.fill("joe", 4);

        assertTrue(result.isError());
        assertEquals(result.getReturnMessage(), "Invalid username");

        result = ProxyServer.SINGLETON.fill(null, 4);

        assertTrue(result.isError());
        assertEquals(result.getReturnMessage(), "The username is missing");
    }

    @AfterClass
    public static void cleanup(){
        server.close();
        server.closeServer();
    }
}
