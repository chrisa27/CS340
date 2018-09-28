package com.example.video.familymapclient;

import com.example.video.familymapclient.Model.DatabaseModel.Event;
import com.example.video.familymapclient.Model.DatabaseModel.EventOrderAdapter;
import com.example.video.familymapclient.Model.DatabaseModel.Location;
import com.example.video.familymapclient.Model.DatabaseModel.Person;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by video on 4/18/2017.
 */

public class EventOrderTest {
    private Person person;
    private Location byu = new Location(40.2518, -111.6493, "Provo", "USA");

    @Before
    public void initialzePerson(){
        person = new Person("testPerson", "testPerson", "Chris", "Arnold", "m", "dad", "mom", "spouse");
        person.setLifeEvents(new TreeSet<>(new EventOrderAdapter()));
    }

    @Test
    public void testValidEvents(){
        //tests in normal order
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", 1995));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", 2002));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", 2013));

        Iterator<Event> eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());

        //tests if they are reordered
        person = new Person("testPerson", "testPerson", "Chris", "Arnold", "m", "dad", "mom", "spouse");
        person.setLifeEvents(new TreeSet<>(new EventOrderAdapter()));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", 2002));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", 2013));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", 1995));

        eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());
    }

    @Test
    public void negativeYear(){

        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", -500));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", -20));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", -3));

        Iterator<Event> eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());

        person = new Person("testPerson", "testPerson", "Chris", "Arnold", "m", "dad", "mom", "spouse");
        person.setLifeEvents(new TreeSet<>(new EventOrderAdapter()));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", 0));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", 1));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", -1));

        eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());

        person = new Person("testPerson", "testPerson", "Chris", "Arnold", "m", "dad", "mom", "spouse");
        person.setLifeEvents(new TreeSet<>(new EventOrderAdapter()));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", -1));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", 0));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", 1));

        eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());
    }

    @Test
    public void birthAndDeathTest(){
        //tests if the birth will always be first
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", -20));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", -3));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", 2000));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Death", 2002));

        Iterator<Event> eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());
        assertEquals("Death", eventIterator.next().getEventType());


        //tests if the death will always be last
        person = new Person("testPerson", "testPerson", "Chris", "Arnold", "m", "dad", "mom", "spouse");
        person.setLifeEvents(new TreeSet<>(new EventOrderAdapter()));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", -20));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Death", -50));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", -3));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", -30));

        eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());
        assertEquals("Death", eventIterator.next().getEventType());

        //tests both the birth and death
        person = new Person("testPerson", "testPerson", "Chris", "Arnold", "m", "dad", "mom", "spouse");
        person.setLifeEvents(new TreeSet<>(new EventOrderAdapter()));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Graduation", 550));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Birth", 500));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Baptism", 450));
        person.getLifeEvents().add(new Event("testPerson", "testPerson", byu, "Death", 525));

        eventIterator = person.getLifeEvents().iterator();
        assertEquals("Birth", eventIterator.next().getEventType());
        assertEquals("Baptism", eventIterator.next().getEventType());
        assertEquals("Graduation", eventIterator.next().getEventType());
        assertEquals("Death", eventIterator.next().getEventType());
    }
}
