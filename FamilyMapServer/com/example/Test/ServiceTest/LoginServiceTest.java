package com.example.Test.ServiceTest;

import com.example.Communicators.RequestHolders.LoginRequest;
import com.example.Communicators.ResultHolders.LoginResult;
import com.example.Database.DataAccess.AuthDAO;
import com.example.Database.DataAccess.EventDAO;
import com.example.Database.DataAccess.PersonDAO;
import com.example.Database.DataAccess.UserDAO;
import com.example.Server;
import com.example.ServerModel.UserAccount;
import com.example.Services.LoginService;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


/**
 * Created by video on 3/1/2017.
 */

public class LoginServiceTest {
    private static Server server;

    @BeforeClass
    public static void initialize() throws Exception{
        server = new Server();
        server.initializeDatabase();
    }


    @Before
    public void reset() {
        AuthDAO.SINGLETON.clearTokenTable();
        EventDAO.SINGLETON.clearEventTable();
        PersonDAO.SINGLETON.clearPersonTable();
        UserDAO.SINGLETON.clearUserTable();

        UserDAO.SINGLETON.addUser(new UserAccount("Chrisa27", "pass", "Chris", "Arnold", "chrisa27@gmail.com", "m"));
    }

    @Test
    public void usernameTest(){
        LoginResult lr = LoginService.SINGLETON.login(new LoginRequest("joe", "pass"));

        assertTrue(lr.isError());
        assertEquals(lr.getErrorMessage(), "The username doesn't exist");
        assertEquals(lr.getUsername(), null);
        assertEquals(lr.getAuthToken(), null);
        assertEquals(lr.getPersonid(), null);
    }

    @Test
    public void matchTest(){
        LoginResult lr = LoginService.SINGLETON.login(new LoginRequest("Chrisa27", "password"));

        assertTrue(lr.isError());
        assertEquals(lr.getErrorMessage(), "The username and password don't match");
        assertEquals(lr.getUsername(), null);
        assertEquals(lr.getAuthToken(), null);
        assertEquals(lr.getPersonid(), null);

        lr = LoginService.SINGLETON.login(new LoginRequest("Chrisa27", "pass"));
        assertTrue(!lr.isError());
        assertTrue(lr.getPersonid() != null);
        assertTrue(lr.getAuthToken() != null);
        assertEquals(lr.getErrorMessage(), null);
        assertEquals(lr.getUsername(), "Chrisa27");
    }

    @Test
    public void missingFieldTest(){
        LoginResult lr = LoginService.SINGLETON.login(new LoginRequest("", "password"));

        assertTrue(lr.isError());
        assertEquals(lr.getErrorMessage(), "There is a value missing");
        assertEquals(lr.getUsername(), null);
        assertEquals(lr.getAuthToken(), null);
        assertEquals(lr.getPersonid(), null);

        lr = LoginService.SINGLETON.login(new LoginRequest("Chrisa27", ""));

        assertTrue(lr.isError());
        assertEquals(lr.getErrorMessage(), "There is a value missing");
        assertEquals(lr.getUsername(), null);
        assertEquals(lr.getAuthToken(), null);
        assertEquals(lr.getPersonid(), null);

        lr = LoginService.SINGLETON.login(new LoginRequest("", ""));

        assertTrue(lr.isError());
        assertEquals(lr.getErrorMessage(), "There is a value missing");
        assertEquals(lr.getUsername(), null);
        assertEquals(lr.getAuthToken(), null);
        assertEquals(lr.getPersonid(), null);
    }

    @AfterClass
    public static void cleanup(){
        server.close();
    }
}
