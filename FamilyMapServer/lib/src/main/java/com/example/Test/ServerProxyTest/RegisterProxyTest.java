package com.example.Test.ServerProxyTest;

import com.example.Communicators.ProxyServer;
import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.Test.TestDriver;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/8/2017.
 */

public class RegisterProxyTest {
    private static Server server;

    @BeforeClass
    public static void initialize(){
        server = new Server();
        server.clearInitialize();
        server.main(TestDriver.getArgs());
    }

    @Before
    public void reset(){
        AuthDAO.SINGLETON.clearTokenTable();
        EventDAO.SINGLETON.clearEventTable();
        PersonDAO.SINGLETON.clearPersonTable();
        UserDAO.SINGLETON.clearUserTable();
    }

    @Test
    public void simpleAddTest(){
        RegisterResponse test;
        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));

        assertTrue(!test.isError());
        assertTrue(test.getAuthToken() != null);
        assertTrue(test.getPersonid() != null);
        assertEquals(test.getErrorMessage(), "");
        assertEquals(test.getUserName(), "Chrisa27");

        test = ProxyServer.SINGLETON.register(new RegisterRequest("Joe", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));

        assertTrue(!test.isError());
        assertTrue(test.getAuthToken() != null);
        assertTrue(test.getPersonid() != null);
        assertEquals(test.getErrorMessage(), "");
        assertEquals(test.getUserName(), "Joe");

        test = ProxyServer.SINGLETON.register(new RegisterRequest("Lazy", "p", "C", "R", "c@yes.com", "f"));

        assertTrue(!test.isError());
        assertTrue(test.getAuthToken() != null);
        assertTrue(test.getPersonid() != null);
        assertEquals(test.getErrorMessage(), "");
        assertEquals(test.getUserName(), "Lazy");
    }

    @Test
    public void existingUsernameTest(){
        RegisterResponse test;
        ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));
        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "That username is already in use.");
        assertEquals(test.getUserName(), null);

        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "joe", "John", "Arnold", "ja@gmail.com", "m"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "That username is already in use.");
        assertEquals(test.getUserName(), null);
    }

    @Test
    public void missingFieldTest(){
        RegisterResponse test;
        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "One of the fields was missing.");
        assertEquals(test.getUserName(), null);
    }

    @Test
    public void genderTest(){
        RegisterResponse test;
        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "M"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "The gender was invalid.");
        assertEquals(test.getUserName(), null);


        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa2", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "F"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "The gender was invalid.");
        assertEquals(test.getUserName(), null);

        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chrisa", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "`"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "The gender was invalid.");
        assertEquals(test.getUserName(), null);

        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chris", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "t"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "The gender was invalid.");
        assertEquals(test.getUserName(), null);

        test = ProxyServer.SINGLETON.register(new RegisterRequest("Chri", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "#"));

        assertTrue(test.isError());
        assertEquals(test.getAuthToken(),  null);
        assertEquals(test.getPersonid(), null);
        assertEquals(test.getErrorMessage(), "The gender was invalid.");
        assertEquals(test.getUserName(), null);
    }

    @AfterClass
    public static void cleanup(){
        server.close();
        server.closeServer();
    }
}
