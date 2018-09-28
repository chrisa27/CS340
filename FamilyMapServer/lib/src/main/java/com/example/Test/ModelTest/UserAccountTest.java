package com.example.Test.ModelTest;

import com.example.ServerModel.UserAccount;

import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by video on 2/24/2017.
 */

public class UserAccountTest {

    @Test
    public void constructorTest(){
        UserAccount user1 = new UserAccount("chrisa27", "pass", "Chris", "Arnold", "chrisa@gmail.com", "m");
        UserAccount user2 = new UserAccount("jacc", "pass", "Jack", "Johnson", "jj@yahoo.com", "f");

        assertEquals(user1.getUsername(), "chrisa27");
        assertEquals(user1.getPassword(), "pass");
        assertEquals(user1.getFirstName(), "Chris");
        assertEquals(user1.getLastName(), "Arnold");
        assertEquals(user1.getEmail(), "chrisa@gmail.com");
        assertEquals(user1.getGender(), "m");
        assertEquals(user1.getPersonid(), null);
        assertEquals(user2.getUsername(), "jacc");
        assertEquals(user2.getPassword(), "pass");
        assertEquals(user2.getFirstName(), "Jack");
        assertEquals(user2.getLastName(), "Johnson");
        assertEquals(user2.getEmail(), "jj@yahoo.com");
        assertEquals(user2.getGender(), "f");
        assertEquals(user2.getPersonid(), null);
    }

    @Test
    public void personidTest(){
        UserAccount user1 = new UserAccount("chrisa27", "pass", "Chris", "Arnold", "chrisa@gmail.com", "m");
        user1.setPersonid("555");

        assertEquals(user1.getPersonid(), "555");
    }

    @Test
    public void equalsTest(){
        UserAccount user1 = new UserAccount("chrisa27", "pass", "Chris", "Arnold", "chrisa@gmail.com", "m");
        UserAccount user2 = new UserAccount("chrisa27", "pass", "Chris", "Arnold", "chrisa@gmail.com", "m");
        UserAccount user3 = new UserAccount("chrisa27", "pss", "ris", "Arno", "chriil.com", "f");
        UserAccount user4 = new UserAccount("chrisa", "pass", "Chris", "Arnold", "chrisa@gmail.com", "m");
        UserAccount user5 = new UserAccount("ch", "pass", "Chris", "Arnold", "chrisa@gmail.com", "m");

        assertEquals(user1.equals(user2), true);
        assertEquals(user1.equals(user3), true);
        assertEquals(user1.equals(user4), false);
        assertEquals(user1.equals(user5), false);
    }
}
