package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.RegisterRequest;
import com.example.Communicators.ResultHolders.RegisterResponse;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.Services.RegisterService;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by video on 3/1/2017.
 */

public class RegisterServiceTest {
    private static Server server;

    @BeforeClass
    public static void initialize() throws Exception{
        server = new Server();
        server.initializeDatabase();
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
        RegisterRequest request = new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        RegisterResponse registerResponse = RegisterService.SINGLETON.register(request);

        assertTrue(!registerResponse.isError());
        assertTrue(registerResponse.getAuthToken() != null);
        assertTrue(registerResponse.getPersonid() != null);
        assertEquals(registerResponse.getErrorMessage(), "");
        assertEquals(registerResponse.getUserName(), "Chrisa27");

        request = new RegisterRequest("Joe", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        registerResponse = RegisterService.SINGLETON.register(request);

        assertTrue(!registerResponse.isError());
        assertTrue(registerResponse.getAuthToken() != null);
        assertTrue(registerResponse.getPersonid() != null);
        assertEquals(registerResponse.getErrorMessage(), "");
        assertEquals(registerResponse.getUserName(), "Joe");

        request = new RegisterRequest("Lazy", "p", "C", "R", "c@yes.com", "f");
        registerResponse = RegisterService.SINGLETON.register(request);

        assertTrue(!registerResponse.isError());
        assertTrue(registerResponse.getAuthToken() != null);
        assertTrue(registerResponse.getPersonid() != null);
        assertEquals(registerResponse.getErrorMessage(), "");
        assertEquals(registerResponse.getUserName(), "Lazy");
    }

    @Test
    public void existingUsernameTest(){
        RegisterRequest request = new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        RegisterResponse registerResponse = RegisterService.SINGLETON.register(request);
        registerResponse = RegisterService.SINGLETON.register(request);

        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "That username is already in use.");
        assertEquals(registerResponse.getUserName(), null);

        request = new RegisterRequest("Chrisa27", "joe", "John", "Arnold", "ja@gmail.com", "m");
        registerResponse = RegisterService.SINGLETON.register(request);

        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "That username is already in use.");
        assertEquals(registerResponse.getUserName(), null);
    }

    @Test
    public void missingFieldTest(){
        RegisterRequest request = new RegisterRequest("Chrisa27", "", "Chris", "Arnold", "Chrisarnold27@gmail.com", "m");
        RegisterResponse registerResponse = RegisterService.SINGLETON.register(request);

        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "One of the fields was missing.");
        assertEquals(registerResponse.getUserName(), null);
    }

    @Test
    public void genderTest(){
        RegisterRequest request = new RegisterRequest("Chrisa27", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "M");
        RegisterResponse registerResponse = RegisterService.SINGLETON.register(request);
        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "The gender was invalid.");
        assertEquals(registerResponse.getUserName(), null);


        request = new RegisterRequest("Chrisa2", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "F");
        registerResponse = RegisterService.SINGLETON.register(request);
        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "The gender was invalid.");
        assertEquals(registerResponse.getUserName(), null);

        request = new RegisterRequest("Chrisa", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "`");
        registerResponse = RegisterService.SINGLETON.register(request);
        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "The gender was invalid.");
        assertEquals(registerResponse.getUserName(), null);

        request = new RegisterRequest("Chris", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "t");
        registerResponse = RegisterService.SINGLETON.register(request);
        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "The gender was invalid.");
        assertEquals(registerResponse.getUserName(), null);

        request = new RegisterRequest("Chri", "pass", "Chris", "Arnold", "Chrisarnold27@gmail.com", "c");
        registerResponse = RegisterService.SINGLETON.register(request);
        assertTrue(registerResponse.isError());
        assertEquals(registerResponse.getAuthToken(),  null);
        assertEquals(registerResponse.getPersonid(), null);
        assertEquals(registerResponse.getErrorMessage(), "The gender was invalid.");
        assertEquals(registerResponse.getUserName(), null);
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
