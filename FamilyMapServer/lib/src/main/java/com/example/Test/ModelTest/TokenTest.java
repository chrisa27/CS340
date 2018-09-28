package com.example.Test.ModelTest;

import com.example.Server;
import com.example.ServerModel.AuthToken;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by video on 2/24/2017.
 */

public class TokenTest {
    @BeforeClass
    public static void initialize(){
        Server server = new Server();
        server.initializeDatabase();
    }

    @Test
    public void constructorTest(){
        AuthToken token = new AuthToken("Chris");

        assertEquals(token.getUsername(), "Chris");
    }

    @Test
    public void equalsTest(){
        AuthToken token1 = new AuthToken("Chris");
        AuthToken token2 = new AuthToken("James");

        assertEquals(token1.equals(token1), true);
        assertEquals(token1.equals(token2), false);
    }
}
