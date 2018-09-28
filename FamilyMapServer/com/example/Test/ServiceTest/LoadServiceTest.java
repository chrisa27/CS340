package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.LoadRequest;
import com.example.Communicators.ResultHolders.LoadResult;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Event;
import com.example.ServerModel.Location;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;
import com.example.Services.LoadService;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by video on 3/6/2017.
 */

public class LoadServiceTest {
    private static Server server;
    private static LoadRequest loadRequest;

    @BeforeClass
    public static void initialize() throws Exception {
        server = new Server();
        server.initializeDatabase();

        Set<UserAccount> users = new HashSet<>();
        Set<Person> people = new HashSet<>();
        Set<Event> events = new HashSet<>();

        UserAccount user = new UserAccount("Joe", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        users.add(user);
        user = new UserAccount("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        users.add(user);
        user = new UserAccount("Lazy", "p", "C", "R", "c@yes.com", "f");
        users.add(user);

        Person person = new Person("Chris", "Arnold", "m");
        people.add(person);
        person = new Person("Joe", "Moe", "m");
        people.add(person);
        person = new Person("Hey", "Arnold", "f");
        people.add(person);

        Location location = new Location(55.66, 66.66, "Hey", "USA");
        Event event = new Event("555", "666", location, "birth", 1555);
        events.add(event);
        event = new Event("123", "456", location, "death", 2323);
        events.add(event);
        event = new Event("432", "999", location, "marriage", 444);
        events.add(event);

        loadRequest = new LoadRequest(users, people, events);
    }

    @Before
    public void reset(){
        AuthDAO.SINGLETON.clearTokenTable();
        EventDAO.SINGLETON.clearEventTable();
        PersonDAO.SINGLETON.clearPersonTable();
        UserDAO.SINGLETON.clearUserTable();
    }

    @Test
    public void emptyTest(){
        LoadResult lr = LoadService.SINGLETON.load(new LoadRequest(null, null, null));

        assertTrue(lr.isError());
        assertEquals(lr.getReturnMessage(), "One of the arrays is empty");
    }

    @Test
    public void fillTest(){
        LoadResult lr = LoadService.SINGLETON.load(loadRequest);

        assertTrue(!lr.isError());
        assertEquals(lr.getReturnMessage(), "Successfully added " + 3 + " users, " +
                3 + " persons, and " + 3 + " events to the database.");
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
