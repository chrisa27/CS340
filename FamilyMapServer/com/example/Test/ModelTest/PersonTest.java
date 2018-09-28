package com.example.Test.ModelTest;

import com.example.Server;
import com.example.ServerModel.Person;

import org.junit.BeforeClass;
import org.junit.Test;


import java.sql.Connection;

import static org.junit.Assert.assertEquals;

/**
 * Created by video on 2/22/2017.
 */

public class PersonTest {
    public static Connection connection;

    @BeforeClass
    public static void initialize(){
        Server server = new Server();
        server.initializeDatabase();
    }

    @Test
    public void userConstructorTest(){
        Person person1 = new Person("Chris", "Arnold", "m");
        Person person2 = new Person("Jane", "Doe", "f");

        assertEquals(person1.getFirstName(), "Chris");
        assertEquals(person1.getLastName(), "Arnold");
        assertEquals(person1.getGender(), "m");
        assertEquals(person1.getMother(), null);
        assertEquals(person1.getFather(), null);
        assertEquals(person1.getSpouse(), null);
        assertEquals(person1.getPersonID(), person1.getDescendentID());
        assertEquals(person2.getFirstName(), "Jane");
        assertEquals(person2.getLastName(), "Doe");
        assertEquals(person2.getGender(), "f");
        assertEquals(person2.getMother(), null);
        assertEquals(person2.getFather(), null);
        assertEquals(person2.getSpouse(), null);
        assertEquals(person2.getPersonID(), person2.getDescendentID());
    }

    @Test
    public void otherConstructorTest(){
        Person person1 = new Person("555", "Chris", "Arnold", "m");
        Person person2 = new Person("666", "Jane", "Doe", "f");

        assertEquals(person1.getFirstName(), "Chris");
        assertEquals(person1.getLastName(), "Arnold");
        assertEquals(person1.getGender(), "m");
        assertEquals(person1.getMother(), null);
        assertEquals(person1.getFather(), null);
        assertEquals(person1.getSpouse(), null);
        assertEquals(person1.getDescendentID(), "555");
        assertEquals(person2.getFirstName(), "Jane");
        assertEquals(person2.getLastName(), "Doe");
        assertEquals(person2.getGender(), "f");
        assertEquals(person2.getMother(), null);
        assertEquals(person2.getFather(), null);
        assertEquals(person2.getSpouse(), null);
        assertEquals(person2.getDescendentID(), "666");
    }

    @Test
    public void addRelativesTest(){
        Person person1 = new Person("555", "Chris", "Arnold", "m");
        person1.setMother("123");
        person1.setFather("444");
        person1.setSpouse("555");

        assertEquals(person1.getMother(), "123");
        assertEquals(person1.getFather(), "444");
        assertEquals(person1.getSpouse(), "555");
    }

    @Test
    public void equalsTest(){
        Person person1 = new Person("Chris", "Arnold", "m");
        Person person2 = new Person("Jane", "Doe", "f");

        assertEquals(person1.equals(person1), true);
        assertEquals(person1.equals(person2), false);
    }
}
