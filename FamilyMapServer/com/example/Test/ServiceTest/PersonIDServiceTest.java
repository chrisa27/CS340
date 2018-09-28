package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;
import com.example.Services.PersonIDService;
import com.example.Services.RegisterService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by video on 3/6/2017.
 */

public class PersonIDServiceTest {
    private static Server server;
    private static String token;
    private static Person user;

    @BeforeClass
    public static void initialize(){
        server = new Server();
        server.initializeDatabase();

        RegisterResponse rs = RegisterService.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));
        token = rs.getAuthToken();
        UserAccount userAccount = UserDAO.SINGLETON.getUser("Chrisa27");

        user = PersonDAO.SINGLETON.getPerson(userAccount.getPersonid());
    }

    @Test
    public void invalidToken(){
        PersonResponse pr = PersonIDService.SINGLETON.personID("55", user.getMother());

        assertTrue(pr.isError());
        assertEquals(pr.getErrorMessage(), "The authorization token is not valid.");
        assertEquals(pr.getAllPeople(), null);
    }

    @Test
    public void invalidID(){
        PersonResponse pr = PersonIDService.SINGLETON.personID(token, "22");

        assertTrue(pr.isError());
        assertEquals(pr.getErrorMessage(), "Invalid personID");
        assertEquals(pr.getAllPeople(), null);
    }

    @Test
    public void idTest(){
        PersonResponse pr = PersonIDService.SINGLETON.personID(token, user.getMother());

        assertTrue(!pr.isError());
        assertTrue(pr.getErrorMessage() == null);
        assertEquals(pr.getAllPeople()[0], PersonDAO.SINGLETON.getPerson(user.getMother()));
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
