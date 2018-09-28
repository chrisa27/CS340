package com.example.Test.ModelTest;

import com.example.Server;
import com.example.ServerModel.Event;
import com.example.ServerModel.Location;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by video on 2/24/2017.
 */

public class EventTest {
    @BeforeClass
    public static void initialize(){
        Server server = new Server();
        server.initializeDatabase();
    }

    @Test
    public void constructorTest(){
        Location location = new Location(55.55, 33.33, "Rockville", "USA");
        Event event = new Event("333", "777", location, "Marriage", 2003);

        assertEquals(event.getDescendantID(), "333");
        assertEquals(event.getPersonID(), "777");
        assertEquals(event.getLatitude(), 55.55, 0);
        assertEquals(event.getLongitude(), 33.33, 0);
        assertEquals(event.getCountry(), "USA");
        assertEquals(event.getCity(), "Rockville");
        assertEquals(event.getEventType(), "Marriage");
        assertEquals(event.getYear(), 2003);
    }

    @Test
    public void equalsTest(){
        Location location = new Location(55.55, 33.33, "Rockville", "USA");
        Event event1 = new Event("333", "777", location, "Marriage", 2003);
        Event event2 = new Event("333", "777", location, "Marriage", 2003);

        assertEquals(event1.equals(event1), true);
        assertEquals(event1.equals(event2), false);
    }
}

//String descendantID, String personID, double latitude, double longitude, String country, String city, String eventType, int year