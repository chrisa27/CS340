package com.example.Test.ServerProxyTest;

import com.example.Communicators.ProxyServer;
import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.PersonResponse;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.Person;
import com.example.ServerModel.UserAccount;
import com.example.Services.PersonService;
import com.example.Services.RegisterService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/8/2017.
 */

public class PersonProxyTest {
    private static Server server;
    private static Set<Person> people;
    private static String token;
    private static UserAccount user;

    @BeforeClass
    public static void initialize(){
        server = new Server();
        server.clearInitialize();
        server.main(null);

        RegisterResponse rs = RegisterService.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));
        token = rs.getAuthToken();
        user = UserDAO.SINGLETON.getUser("Chrisa27");
    }

    @Test
    public void invalidToken(){
        PersonResponse pr = ProxyServer.SINGLETON.person(null, "333");

        assertTrue(pr.isError());
        assertEquals(pr.getErrorMessage(), "The authorization token is not valid");
        assertEquals(pr.getAllPeople(), null);
    }

    @Test
    public void personTest(){
        PersonResponse pr = ProxyServer.SINGLETON.person(null, token);

        assertTrue(!pr.isError());
        assertEquals(pr.getErrorMessage(), null);
        assertTrue(pr.getAllPeople() != null);
        assertTrue(pr.getAllPeople().length == 31);
    }

    @AfterClass
    public static void cleanup(){
        server.close();
        server.closeServer();
    }
}
