package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;
import com.example.Services.PersonService;
import com.example.Services.RegisterService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

/**
 * Created by video on 3/6/2017.
 */

public class PersonServiceTest {
    private static Server server;
    private static Set<Person> people;
    private static String token;
    private static UserAccount user;

    @BeforeClass
    public static void initialize(){
        server = new Server();
        server.initializeDatabase();

        RegisterResponse rs = RegisterService.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));
        token = rs.getAuthToken();
        user = UserDAO.SINGLETON.getUser("Chrisa27");
    }

    @Test
    public void invalidToken(){
        PersonResponse pr = PersonService.SINGLETON.person("333");

        assertTrue(pr.isError());
        assertEquals(pr.getErrorMessage(), "The authorization token is not valid");
        assertEquals(pr.getAllPeople(), null);
    }

    @Test
    public void personTest(){
        PersonResponse pr = PersonService.SINGLETON.person(token);

        assertTrue(!pr.isError());
        assertEquals(pr.getErrorMessage(), null);
        assertTrue(pr.getAllPeople() != null);
        assertTrue(pr.getAllPeople().length == 31);
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
